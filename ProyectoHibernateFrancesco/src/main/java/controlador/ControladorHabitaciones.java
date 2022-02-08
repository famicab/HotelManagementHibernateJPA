package controlador;

import java.awt.Point;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import database.Database;
import modelo.*;
import ordenadores.OrdenarHabitacionPorCategoria;
import ordenadores.OrdenarHabitacionPorPreciosAsc;
import ordenadores.OrdenarHabitacionPorPreciosDesc;
import utils.Log;
import utils.Notificador;
import utils.TableModelUtils;
import view.CatalogoDestinosDialog;
import view.HacerReservasDialog;
import view.ReservaHabitacionesDialog;

/***
 * Esta clase controla la vista sobre la cual podemos ver las habitaciones que
 * tiene un hotel. Podemos consultar las habitaciones, ordenarlas según ciertos
 * criterios y agruparlas.
 * 
 * @author Francesco De Amicis
 *
 */
public class ControladorHabitaciones {

	private Usuario usuario;
	private Hotel hotel;
	private Database database;
	private ReservaHabitacionesDialog habitacionesDialog;

	public ControladorHabitaciones(Usuario usuario, Hotel hotel, Database database,
			ReservaHabitacionesDialog habitacionesDialog) {
		this.usuario = usuario;
		this.hotel = hotel;
		this.database = database;
		this.habitacionesDialog = habitacionesDialog;

		/**
		 * Evento que selecciona las habitaciones de la base de datos relacionadas con el hotel.
		 * Rellena el JTable con la lista recuperada.
		 */
		this.habitacionesDialog.verHabitaciones(e -> {
			Log.i("Usuario: "+usuario.getId()+" consultó habitaciones");
			List<Habitacion> list = database.getHabitaciones(hotel);
			ordenarHabitaciones(habitacionesDialog, list);
			TableModel tablaHabitaciones = TableModelUtils.listHabitacionToTableModel(list);
			habitacionesDialog.getTable().setModel(tablaHabitaciones);
			habitacionesDialog.getBotonAgrupar().setEnabled(true);
		});

		/***
		 * Evento que hace un groupBy sobre la lista de habitaciones dependiendo de la categoría.
		 */
		this.habitacionesDialog.agruparHabitaciones(e -> {
			List list = database.getHabitacionesAgrupadas(hotel);
			TableModel agrupar = TableModelUtils.listHabitacionAgrupadaToTableModel(list);
			habitacionesDialog.getTable().setModel(agrupar);
		});

		/***
		 * Evento en el doble click de una fila que nos abre una pantalla para reservarla.
		 */
		this.habitacionesDialog.irAReservaHabitacion(e -> {
			JTable table = (JTable) e.getSource();
			if (table.getColumnCount() < 4) {
				Notificador.notificaError("Para seleccionar una habitación debe cambiar de vista.");
			} else {
				Point point = e.getPoint();
				int row = table.rowAtPoint(point);
				if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
					String id = table.getValueAt(table.getSelectedRow(), 0).toString();
					Habitacion habitacion = database.getHabitacion(Integer.parseInt(id));
					HacerReservasDialog reservasDialog = new HacerReservasDialog(habitacionesDialog, true);
					ControladorHacerReservas controladorHacerReservas = new ControladorHacerReservas(usuario, hotel,
							habitacion, database, reservasDialog);
					// habitacionesDialog.dispatchEvent(new WindowEvent(habitacionesDialog,
					// WindowEvent.WINDOW_CLOSING));
					reservasDialog.setVisible(true);
				}

			}
		});

	}

	/***
	 * Metodo que ordena una lista de habitaciones
	 * 
	 * @param habitacionesDialog Vista sobre la cual se visualiza el ordenamiento.
	 * @param list               Lista de habitaciones a ordenar.
	 */
	private void ordenarHabitaciones(ReservaHabitacionesDialog habitacionesDialog, List<Habitacion> list) {
		if (habitacionesDialog.getFiltro() != "") {

			switch (habitacionesDialog.getFiltro()) {
				case "Tipo":
					Collections.sort(list, new OrdenarHabitacionPorCategoria());
					break;
				case "Precio: ascendente":
					Collections.sort(list, new OrdenarHabitacionPorPreciosAsc());
					break;
				case "Precio: descendente":
					Collections.sort(list, new OrdenarHabitacionPorPreciosDesc());
					break;
			}

		}

	}
}
