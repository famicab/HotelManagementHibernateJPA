package controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import database.Database;
import modelo.Habitacion;
import modelo.Reserva;
import ordenadores.OrdenarReservaPorFechaDeReserva;
import ordenadores.OrdenarReservaPorHotel;
import ordenadores.OrdenarReservaPorPrecio;
import utils.DateFormatter;
import utils.Log;
import utils.Notificador;
import utils.TableModelUtils;
import view.ModificarReservaDialog;
import view.AdministrarTodasLasReservasDialog;

/***
 * Clase para gestionar todas las reservas. Desde aquí pueden visualizarse y modificarse.
 * @author Francesco De Amicis 
 *
 */
public class ControladorReservasAdministrador {

	private Database database;
	private List<Reserva> reservas;
	private AdministrarTodasLasReservasDialog dialog;
	private ModificarReservaDialog modificarReservaDialog= new ModificarReservaDialog(this.dialog, true);;
	
	public ControladorReservasAdministrador(AdministrarTodasLasReservasDialog dialog, Database database) {
		this.dialog = dialog;
		this.database = database;
		
		/**
		 * Evento que permite la visualización de todas las reservas y filtrarlas por distintos parámetros.
		 */
		this.dialog.verReservas(e -> {
			this.reservas = database.getReservas();
			ordenarReservas(dialog, reservas);
			filtrarReservas(dialog, reservas);
			TableModel model = TableModelUtils.listReservasToTableModel(reservas);		
			dialog.getTable().setModel(model);
		});
		
		/***
		 * Abre un dialog que permite modificar la fecha de reserva
		 */
		this.dialog.updateReserva(e -> {
			if (dialog.getTable().getSelectedRow() != -1) {
				modificarReservaDialog.setVisible(true);
			} else {
				Notificador.notificaError("Seleccione una reserva");
			}
			
		});
		
		/***
		 * Actualiza la fecha de reserva
		 */
		this.modificarReservaDialog.actualizarReserva(e -> {
			String idReserva = dialog.getTable().getValueAt(dialog.getTable().getSelectedRow(), 0).toString();
			int id = Integer.parseInt(idReserva);
			Reserva reserva = database.getReserva(id);
			String anoIda = modificarReservaDialog.getAnoIda();
			String mesIda = modificarReservaDialog.getMesIda();
			String diaIda = modificarReservaDialog.getDiaIda();
			String anoVuelta = modificarReservaDialog.getAnoVuelta();
			String mesVuelta = modificarReservaDialog.getMesVuelta();
			String diaVuelta = modificarReservaDialog.getDiaVuelta();

			if (anoIda.isEmpty() || mesIda.isEmpty() || diaIda.isEmpty() || anoVuelta.isEmpty() || mesVuelta.isEmpty()
					|| diaVuelta.isEmpty()) {
				Notificador.notificaError("Todos los campos son obligatorios");
			} else {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String fechaIda = anoIda + "-" + mesIda + "-" + diaIda;
				String fechaVuelta = anoVuelta + "-" + mesVuelta + "-" + diaVuelta;
				try {
					Date ida = format.parse(fechaIda);
					Date vuelta = format.parse(fechaVuelta);
					Date now = new Date();
					
					if (ida.compareTo(now) < 0) { //si la fecha de reserva es anterior a hoy lanzo error
						Notificador.notificaError("¡No puedes viajar al pasado!");
					} else if (vuelta.compareTo(ida) < 0){
						Notificador.notificaError("La fecha de vuelta debe ser posterior a la fecha de ida");
					} else {
						//Modificar la reserva
						Double precio = calcularPrecio(ida, vuelta, reserva.getHabitacion());
						reserva.setPrecio(precio);
						reserva.setFechaEntrada(ida);
						reserva.setFechaSalida(vuelta);
						
						int input = JOptionPane.showConfirmDialog(null, "Vas a realizar una reserva en: "
								+ "\n- Hotel: "+reserva.getHotel().getNombre()+" ("+reserva.getHotel().getCiudad()+")\n"
										+ "- Habitación: "+reserva.getHabitacion().getCategoriaHabitacion()+"\n"
												+ "Desde: "+ida+" - Hasta: "+vuelta+"\n"
														+ "Por un total de: "+Math.round(precio * 100.0)/100.0+"€\n"
																+ "¿Estás seguro/a?");
						
						if (input == JOptionPane.OK_OPTION) {
							if (!database.habitacionOcupada(reserva) || reserva.equals(database.selectReservaOcupada(reserva))) { //se comprueba si la fecha es posible ese dia
								database.updateReserva(reserva);
								Notificador.notificaExito("Reserva actualizada con exito");
								Log.i("Admin actualizó reserva con id: "+reserva.getIdReserva());
							} else {
								//
								Reserva fallo = database.selectReservaOcupada(reserva);
								String fechaIdaFallo = DateFormatter.getDate(fallo.getFechaEntrada());
								String fechaVueltaFallo = DateFormatter.getDate(fallo.getFechaSalida());
								Notificador.notificaError("Esta habitación está ocupada desde el: "+fechaIdaFallo+" hasta el: "+fechaVueltaFallo+". Prueba una fecha distinta");
							}
							
							
						}
						
					}
				} catch (ParseException e1) {
					Notificador.notificaError("Introduzca campos válidos");
				}
			}
		});
	}

	/***
	 * Método que filtra reservas por el id
	 * @param dialog Dialog sobre el que se verá la lista.
	 * @param reservas Lista sobre la que se va a filtrar.
	 */
	private void filtrarReservas(AdministrarTodasLasReservasDialog dialog, List<Reserva> reservas) {
		if (!dialog.getTextoFiltrado().equals("")) {
			ListIterator<Reserva> itr = reservas.listIterator();
			try {
				int id = Integer.parseInt(dialog.getTextoFiltrado());
				while (itr.hasNext()) {
					if(itr.next().getIdReserva() != id) {
						itr.remove();
					}
				}
			} catch (NumberFormatException e) {
				Notificador.notificaError("Introduzca un numero");
			}
			
		}
		
	}

	/***
	 * Método para ordenar una Lista de Reservas por distintos parámetros.
	 * @param dialog Vista sobre la que se va a visualizar la tabla.
	 * @param reservas Lista de Reservas que se va a ordenar.
	 */
	private void ordenarReservas(AdministrarTodasLasReservasDialog dialog, List<Reserva> reservas) {
		if (!dialog.getOrden().equals("")) {
			switch(dialog.getOrden()) {
				case "Fecha de reserva":
					Collections.sort(reservas, new OrdenarReservaPorFechaDeReserva());
					break;
				case "Precio":
					Collections.sort(reservas, new OrdenarReservaPorPrecio());
					break;
				case "Hotel":
					Collections.sort(reservas, new OrdenarReservaPorHotel());
					break;
			}
		}
		
	}
	
	private Double calcularPrecio(Date ida, Date vuelta, Habitacion habitacion) {
		int dias = (int)(vuelta.getTime() - ida.getTime()) / (1000 * 60 * 60 * 24);
		Double precio = habitacion.getPrecio()*dias;
		return precio;
	}
}
