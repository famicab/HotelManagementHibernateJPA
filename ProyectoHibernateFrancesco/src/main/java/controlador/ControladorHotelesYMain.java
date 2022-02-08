package controlador;

import java.awt.Point;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import database.Database;
import modelo.Ciudad;
import modelo.Hotel;
import modelo.Usuario;
import utils.Notificador;
import utils.TableModelUtils;
import view.CatalogoDestinosDialog;
import view.MainDialog;
import view.ReservaHabitacionesDialog;
import view.VerMisReservasDialog;
import view.AdministracionHotelesDialog;
import view.AdministrarTodasLasReservasDialog;

/***
 * 
 * @author Francesco De Amicis
 * Esta clase controla las vistas main y destinosDialog.
 *
 */
public class ControladorHotelesYMain {

	private MainDialog mainFrame;
	private CatalogoDestinosDialog destinosDialog;
	private Usuario usuario;
	private Database database;

	public ControladorHotelesYMain(MainDialog mainFrame, CatalogoDestinosDialog destinosDialog, Usuario usuario,
			Database database) {
		this.mainFrame = mainFrame;
		this.destinosDialog = destinosDialog;
		this.usuario = usuario;
		this.database = database;

		/***
		 * Este evento abre el Dialog de destinos a los que ir.
		 */
		this.mainFrame.irACatalogo(e -> {
			destinosDialog.setVisible(true);
		});
		
		/***
		 * Evento que abre un Dialog en el que podemos consultar las reservas asociadas al usuario.
		 */
		this.mainFrame.verMisReservas(e -> {
			VerMisReservasDialog misReservasDialog = new VerMisReservasDialog((MainDialog)null, true);
			ControladorGestionarMisReservas controladorMisReservas = new ControladorGestionarMisReservas(misReservasDialog, database, usuario);
			misReservasDialog.setVisible(true);
		});

		/***
		 * Este evento actualiza la tabla según los parámetros introducidos.
		 */
		this.destinosDialog.actualizarTabla(e -> {
			
			String query = "SELECT H.nombre, H.categoria, C.nombre, C.pais FROM Hotel H INNER JOIN H.ciudad C ";			
			query = addFiltro(destinosDialog, query);	
			query = ordenarTabla(destinosDialog, query);

			List hotelesCiudades = database.query(query);
			TableModel tablaHotelCiudad = TableModelUtils.joinHotelesCiudades(hotelesCiudades);

			destinosDialog.getTabla().setModel(tablaHotelCiudad);
			if (destinosDialog.getTabla().getRowCount() < 1) {
				Notificador.notificaError("Su busqueda no devolvio resultados");
			}
			
			
		});
		
		/***
		 * Este evento permite ir a un nuevo Dialog para consultar las habitaciones del hotel seleccionado.
		 */
		destinosDialog.irAConsultaHabitaciones(e -> {
			JTable table =(JTable) e.getSource();
	        Point point = e.getPoint();
	        int row = table.rowAtPoint(point);
	        if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
	        	String nombre = table.getValueAt(table.getSelectedRow(), 0).toString();
	        	Hotel h = database.selectHotel(nombre);
        	
	            ReservaHabitacionesDialog habitacionesDialog = new ReservaHabitacionesDialog(destinosDialog, true);
	            ControladorHabitaciones controladorHabitaciones = new ControladorHabitaciones(this.usuario, h, database, habitacionesDialog);

	            habitacionesDialog.setVisible(true);
	        }
		});
		
		/***
		 * Si entra el admin puede consultar todas las reservas realizadas y modificarlas,
		 * así como ver información adicional sobre los hoteles.
		 * Un usuario normal solo puede eliminarlas.
		 */
		if(this.usuario.getEmail().equals("admin") && this.usuario.getContrasena().equals("admin")) {
			mainFrame.getButtonMisReservas().setVisible(false);
			mainFrame.getButtonMisReservas().setEnabled(false);
			mainFrame.getButtonMisReservas().setVisible(false);
			mainFrame.getButtonMisReservas().setEnabled(false);
			mainFrame.getButtonGestionarReservas().setVisible(true);
			mainFrame.getButtonGestionarReservas().setEnabled(true);
			mainFrame.gestionarReservas(e -> {
				AdministrarTodasLasReservasDialog dialog = new AdministrarTodasLasReservasDialog((MainDialog)null, true);
				ControladorReservasAdministrador controladorReservas = new ControladorReservasAdministrador(dialog, database);
				dialog.setVisible(true);
			});
			
			mainFrame.getButtonVerHoteles().setVisible(true);
			mainFrame.getButtonVerHoteles().setEnabled(true);
			mainFrame.consultarHoteles(e -> {
				AdministracionHotelesDialog hotelesDialog = new AdministracionHotelesDialog((MainDialog)null, true);
				ControladorAdministracionHoteles administracionHoteles = new ControladorAdministracionHoteles(hotelesDialog, database);
				hotelesDialog.setVisible(true);
			});
		}
	}

	/***
	 * Método que ordena la tabla mediante una query.
	 * @param destinosDialog Dialog en el que se mostrará la tabla ordenada.
	 * @param query Query que se va a realizar.
	 * @return La query modificada.
	 */
	private String ordenarTabla(CatalogoDestinosDialog destinosDialog, String query) {
		if (destinosDialog.getOrden() != "") {
			switch (destinosDialog.getOrden()) {
			case "Nombre":
				query += "ORDER BY H.nombre";
				break;
			case "Ciudad":
				query += "ORDER BY C.nombre";
				break;
			case "Pais":
				query += "ORDER BY C.pais";
				break;
			case "Estrellas: ascendente":
				query += "ORDER BY H.categoria ASC";
				break;
			case "Estrellas: descendente":
				query += "ORDER BY H.categoria DESC";
				break;
			}
		}
		return query;
	}

	/***
	 * Método que añade un filtro WHERE a nuestra query.
	 * @param destinosDialog Dialog sobre el que se va a mostrar el resultado de la query.
	 * @param query Query que se va a modificar.
	 * @return La query modificada.
	 */
	private String addFiltro(CatalogoDestinosDialog destinosDialog, String query) {
		if (destinosDialog.getFiltro() != "") {
			if (!destinosDialog.getFiltroText().isEmpty()) {
				switch (destinosDialog.getFiltro()) {
				case "Nombre":
					query += "WHERE H." + destinosDialog.getFiltro().toLowerCase() + " LIKE \'%"
							+ destinosDialog.getFiltroText() + "%\'";
					break;
				case "Ciudad":
					query += "WHERE C.nombre LIKE \'%" + destinosDialog.getFiltroText() + "%\'";
					break;
				case "Pais":
					query += "WHERE C.pais LIKE \'%" + destinosDialog.getFiltroText() + "%\'";
					break;
				case "Estrellas":
					int estrellas = 0;
					try {
						estrellas = Integer.parseInt(destinosDialog.getFiltroText());
					} catch (Exception ex) {
						Notificador.notificaError("Introduzca un número entre 1 y 5");
					}
					query += "WHERE H.categoria = " + estrellas;
					break;
				}
			} else {
				Notificador.notificaError(
						"Si quiere filtrar por " + destinosDialog.getFiltro() + " introduzca un valor válido");
			}
		}
		return query;

	}

}
