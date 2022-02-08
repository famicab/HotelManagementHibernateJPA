package utils;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import modelo.CategoriaHabitacion;
import modelo.CategoriaHotel;
import modelo.EmpresaHotel;
import modelo.Habitacion;
import modelo.Hotel;
import modelo.Reserva;
/***
 * 
 * @author Francesco De Amicis
 * Clase que permite convertir diferentes 
 * tipos de lista a TableModel para rellenar JTables.
 */
public class TableModelUtils {


	/***
	 * Convierte una Lista de Hoteles en un TableModel
	 * @param list Lista de hoteles.
	 * @return Un TableModel que empleará una JTable.
	 */
	public static TableModel listHotelToTableModel(List<Hotel> list) {
		String[] col = { "Id", "IdCiudad", "Nombre", "Estrellas" };
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		ListIterator<Hotel> iteradorHotel = list.listIterator();
		while (iteradorHotel.hasNext()) {
			Hotel h = iteradorHotel.next();
			int id = h.getId();
			int idC = h.getCiudad().getId();
			String nombre = h.getNombre();
			CategoriaHotel cat = h.getCategoria();
			Object[] data = { id, idC, nombre, cat };
			tableModel.addRow(data);
		}
		return tableModel;
	}

	/***
	 * Metodo que convierte a TableModel una query que ha
	 * hecho un Join de la clase Hotel y la clase Ciudad.
	 * @param list Lista a convertir.
	 * @return Un TableModel que empleará una JTable.
	 */
	public static TableModel joinHotelesCiudades(List list) {
		String[] col = { "Nombre", "Estrellas", "Nombre", "Pais" };
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);

		Iterator iteradorHotelCiudad = list.iterator();

		while (iteradorHotelCiudad.hasNext()) {
			Object[] rows = (Object[]) iteradorHotelCiudad.next();
			tableModel.addRow(rows);
		}
		return tableModel;
	}
	
	/***
	 * Convierte una Lista de Habitaciones en TableModel.
	 * @param list Lista que va a transformar.
	 * @return Un TableModel que empleará una JTable.
	 */
	public static TableModel listHabitacionToTableModel(List<Habitacion> list) {
		String[] col = { "Id", "Hotel", "Categoria", "Precio/Dia €" };
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		
		ListIterator<Habitacion> iteradorHabitacion = list.listIterator();
		while (iteradorHabitacion.hasNext()) {
			Habitacion h = iteradorHabitacion.next();
			int id = h.getIdHabitacion();
			String nombreHotel = h.getHotel().getNombre();
			CategoriaHabitacion categoria = h.getCategoriaHabitacion();
			Double precio = h.getPrecio();
			Object[] data = { id, nombreHotel, categoria, precio };
			tableModel.addRow(data);
		}
		return tableModel;
	}
	
	/***
	 * Convierte una Lista de Reservas en TableModel.
	 * @param list Lista de Reservas.
	 * @return Un TableModel que empleará una JTable.
	 */
	public static TableModel listReservasToTableModel(List<Reserva> list) {
		String[] col = {"Id", "Hotel", "Habitacion", "Entrada", "Salida", "Fecha de reserva", "Precio total", "Tipo de pago"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);		
		ListIterator<Reserva> itr = list.listIterator();
		
		while (itr.hasNext()) {
			Reserva r = itr.next();
			int id = r.getIdReserva();
			String hotel = r.getHotel().getNombre();
			CategoriaHabitacion c = r.getHabitacion().getCategoriaHabitacion();
			String entrada = DateFormatter.getDate(r.getFechaEntrada());
			String salida = DateFormatter.getDate(r.getFechaSalida());
			String reserva = DateFormatter.getDate(r.getFechaReserva());
			Double precio = r.getPrecio();
			String tipoDePago = r.getPago().getTipoPago();
			Object[] data = {id, hotel, c, entrada, salida, reserva, precio, tipoDePago};
			tableModel.addRow(data);
		}		
		return tableModel;
	}
	
	/***
	 * Método que convierte una lista de habitaciones agrupadas por categoria en una JTable
	 * @param list Lista a convertir
	 * @return un TableModel que se usará para llenar un JTable.
	 */
	public static TableModel listHabitacionAgrupadaToTableModel(List list) {
		String[] col = {"Tipo habitacion", "Precio/noche", "Cantidad" };		
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);	
		Iterator itr = list.iterator();	
		while(itr.hasNext()) {
			Object[] data = (Object[])itr.next();
			tableModel.addRow(data);
		}
			
		return tableModel;
	}
	
	/***
	 * Convierte una lista de la subquery en la que se agrupan habitaciones por el nombre de la compañia
	 * @param list Lista que recibe
	 * @return Un table model que rellenará una JTable.
	 */
	public static TableModel listHabitacionesAgrupadasPorCompania(List list) {
		String[] col = {"Total habitaciones", "Nombre companía"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);	
		Iterator itr = list.iterator();	
		while(itr.hasNext()) {
			Object[] data = (Object[])itr.next();
			tableModel.addRow(data);
		}		
		return tableModel;
	}
	
	/***
	 * Método que convierte una lista hoteles y su dinero recaudado en JTable.
	 * @param list Lista a convertir
	 * @return un TableModel que se usará para llenar un JTable.
	 */
	public static TableModel listHotelesMasRentables(List list) {
		String[] col = {"Total recaudado", "Hotel"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);	
		Iterator itr = list.iterator();	
		while(itr.hasNext()) {
			Object[] data = (Object[])itr.next();
			tableModel.addRow(data);
		}		
		return tableModel;
	}
	
	public static TableModel listHabitacionMas(List list) {
		String[] col = {"Precio", "Hotel"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);	
		Iterator itr = list.iterator();	
		while(itr.hasNext()) {
			Object[] data = (Object[])itr.next();
			tableModel.addRow(data);
		}		
		return tableModel;
	}
	
	public static TableModel listHabitacionesSinReserva(List list) {
		String[] col = {"Id Habitacion", "Categoria habitacion","Precio/noche", "Hotel"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);	
		Iterator itr = list.iterator();	
		while(itr.hasNext()) {
			Object[] data = (Object[])itr.next();
			tableModel.addRow(data);
		}		
		return tableModel;
	}
	
	public static TableModel listJoinHotelEmpresas(List<EmpresaHotel> list) {
		String[] col = {"N. Companía", "Hotel", "Estrellas", "Habitaciones"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		ListIterator<EmpresaHotel> itr = list.listIterator();
		while(itr.hasNext()) {
			EmpresaHotel eh = itr.next();
			String compania = eh.getEmpresa().getNombreCompania();
			String hotel = eh.getHotel().getNombre();
			CategoriaHotel cat = eh.getHotel().getCategoria();
			int habitaciones = eh.getHotel().getHabitaciones().size();
			
			Object[] data = {compania, hotel, cat, habitaciones};
			tableModel.addRow(data);
		}
		return tableModel;
	}

	

	

	

}
