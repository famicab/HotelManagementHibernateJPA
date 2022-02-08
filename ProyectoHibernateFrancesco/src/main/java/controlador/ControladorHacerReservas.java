package controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;

import javax.swing.JOptionPane;

import database.Database;
import modelo.Habitacion;
import modelo.Hotel;
import modelo.Pago;
import modelo.Reserva;
import modelo.Usuario;
import utils.DateFormatter;
import utils.Log;
import utils.Notificador;
import view.HacerReservasDialog;

/***
 * Controlador que inserta Reservas asociadas a un Usuario
 * 
 * @author Francesco De Amicis
 *
 */
public class ControladorHacerReservas {

	private Usuario usuario;
	private Hotel hotel;
	private Habitacion habitacion;
	private Database database;
	private HacerReservasDialog reservasDialog;

	public ControladorHacerReservas(Usuario usuario, Hotel hotel, Habitacion habitacion, Database database,
			HacerReservasDialog reservasDialog) {
		this.usuario = usuario;
		this.hotel = hotel;
		this.habitacion = habitacion;
		this.database = database;
		this.reservasDialog = reservasDialog;

		/***
		 * Al abrirse al dialog se muestran algunos datos de la reserva
		 */
		this.reservasDialog.setTextoInfo(
				"Una habitación " + habitacion.getCategoriaHabitacion() + ". " + habitacion.getPrecio() + "€/noche.");

		/***
		 * Recuperamos los datos de fechas de reserva, creamos una reserva y se inserta
		 * en la bd.
		 */
		this.reservasDialog.hacerReserva(e -> {
			String anoIda = reservasDialog.getAnoIda();
			String mesIda = reservasDialog.getMesIda();
			String diaIda = reservasDialog.getDiaIda();
			String anoVuelta = reservasDialog.getAnoVuelta();
			String mesVuelta = reservasDialog.getMesVuelta();
			String diaVuelta = reservasDialog.getDiaVuelta();
			String tipoDePago = reservasDialog.getTipoDePago();

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

					if (ida.compareTo(now) < 0) { // si la fecha de reserva es anterior a hoy lanzo error
						Notificador.notificaError("¡No puedes viajar al pasado!");
					} else if (vuelta.compareTo(ida) < 0) {
						Notificador.notificaError("La fecha de vuelta debe ser posterior a la fecha de ida");
					} else {

						// Probamos a hacer la reserva
						Double precio = calcularPrecio(ida, vuelta, habitacion);
						Reserva reserva = new Reserva(usuario, habitacion, hotel, now, ida, vuelta, precio);
						int input = JOptionPane.showConfirmDialog(null,
								"Vas a realizar una reserva en: " + "\n- Hotel: " + hotel.getNombre() + " ("
										+ hotel.getCiudad() + ")\n" + "- Habitación: "
										+ habitacion.getCategoriaHabitacion() + "\n" + "Desde: " + ida + " - Hasta: "
										+ vuelta + "\n" + "Por un total de: " + Math.round(precio * 100.0) / 100.0
										+ "€\n" + "¿Estás seguro/a?");

						if (input == JOptionPane.OK_OPTION) {
							//Si la habitacion no está ocupada en esas fechas, podemos realizar la reserva
							if (!database.habitacionOcupada(reserva)) {
								Pago pago = new Pago(null, precio, tipoDePago, reserva);
								if (tipoDePago.equals("Tarjeta bancaria")) {
									pago.setFechaPago(now);
								} else {
									pago.setFechaPago(ida);
								}	
								reserva.setPago(pago);
								database.insertReserva(reserva);
								usuario.addReserva(reserva);
								hotel.addReserva(reserva);
								habitacion.addReserva(reserva);
								Log.i("Usuario: "+usuario.getId()+" reservó habitación: "+habitacion.getIdHabitacion());
								Notificador
										.notificaExito("Reserva realizada. Puedes consultar los detalles en tu cuenta");
							} else {
								// Se recuperan los datos de la reserva que ha impedido la reserva.
								Reserva fallo = database.selectReservaOcupada(reserva);
								String fechaIdaFallo = DateFormatter.getDate(fallo.getFechaEntrada());
								String fechaVueltaFallo = DateFormatter.getDate(fallo.getFechaSalida());
								Notificador.notificaError("Esta habitación está ocupada desde el: " + fechaIdaFallo
										+ " hasta el: " + fechaVueltaFallo + ". Prueba una fecha distinta");
							}

						}

					}
				} catch (ParseException e1) {
					Notificador.notificaError("Introduzca campos válidos");
				}
			}

		});
	}

	private Double calcularPrecio(Date ida, Date vuelta, Habitacion habitacion) {
		int dias = (int) (vuelta.getTime() - ida.getTime()) / (1000 * 60 * 60 * 24);
		Double precio = habitacion.getPrecio() * dias;
		return precio;
	}

}
