package controlador;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import database.Database;
import modelo.Habitacion;
import modelo.Hotel;
import modelo.Reserva;
import modelo.Usuario;
import utils.Log;
import utils.Notificador;
import utils.TableModelUtils;
import view.VerMisReservasDialog;

/***
 * Controlador para consultar las reservas del Usuario que hace Log in.
 * 
 * @author Francesco De Amicis
 *
 */
public class ControladorGestionarMisReservas {

	private VerMisReservasDialog misReservasDialog;
	private Database database;
	private Usuario usuario;

	public ControladorGestionarMisReservas(VerMisReservasDialog misReservasDialog, Database database, Usuario usuario) {
		this.misReservasDialog = misReservasDialog;
		this.database = database;
		this.usuario = usuario;

		/***
		 * Evento que borra una reserva de la Bd.
		 */
		this.misReservasDialog.borrarReserva(e -> {
			if (misReservasDialog.getTable().getSelectedRow() != -1) {
				int input = JOptionPane.showConfirmDialog(null,
						"Vas a cancelar un billete. Esta acción no se puede deshacer. " + "¿Estás seguro/a?");
				if (input == JOptionPane.OK_OPTION) {

					
					Notificador.notificaExito("Billete cancelado. Actualiza la tabla para ver los cambios");
					String idReserva = this.misReservasDialog.getTable()
							.getValueAt(misReservasDialog.getTable().getSelectedRow(), 0).toString();
					Reserva r = new Reserva(Integer.parseInt(idReserva));
					if (this.usuario.getReservas().contains(r)) {
						r = this.usuario.getReservas().get(usuario.getReservas().indexOf(r));
					}
					Log.i("Usuario: " + usuario.getId() + " borró reserva: " + r.getIdReserva());
					//Elimino las referencias a reserva
					Hotel h = r.getHotel();
					Habitacion habitacion = r.getHabitacion();
					usuario.removeReserva(r);
					h.removeReserva(r);
					habitacion.removeReserva(r);

					//Lo elimino de la base de datos
					this.database.deleteReserva(r);
				}

			} else {
				Notificador.notificaError("Debe seleccionar un billete.");
			}

		});

		/***
		 * Cargar las reservas del usuario.
		 */
		this.misReservasDialog.verReservas(e -> {
			List<Reserva> reservas = usuario.getReservas();
			TableModel tablaReservas = TableModelUtils.listReservasToTableModel(reservas);
			this.misReservasDialog.getTable().setModel(tablaReservas);
			Log.i("Usuario: " + usuario.getId() + " consultó reservas.");
		});

		/***
		 * Se imprimen los datos de una reserva en un .txt
		 */
		this.misReservasDialog.imprimirBillete(e -> {
			if (misReservasDialog.getTable().getSelectedRow() != -1) {
				String idReserva = this.misReservasDialog.getTable()
						.getValueAt(misReservasDialog.getTable().getSelectedRow(), 0).toString();
				Reserva r = new Reserva(Integer.parseInt(idReserva));
				if (usuario.getReservas().contains(r)) {
					r = usuario.getReservas().get(usuario.getReservas().indexOf(r));
				}
				printFactura(usuario, r);
				Log.i("Usuario: " + usuario.getId() + " imprimió: " + r.getIdReserva());
			} else {
				Notificador.notificaError("Seleccione un billete");
			}

		});
	}

	private void printFactura(Usuario usuario, Reserva r) {
		if (misReservasDialog.showFileChooser() == JFileChooser.APPROVE_OPTION) {
			String factura = r.toString();
			try {
				PrintStream ps = new PrintStream(misReservasDialog.getJFileChooser().getSelectedFile() + ".txt");
				ps.print(factura);
				ps.close();
				Notificador.notificaExito("Factura descargada correctamente");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}

	}

}
