package controlador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.swing.table.TableModel;

import database.Database;
import modelo.Empresa;
import modelo.EmpresaHotel;
import ordenadores.OrdenarEmpresaHotelPorEmpresa;
import ordenadores.OrdenarEmpresaHotelPorHabitacionesAsc;
import ordenadores.OrdenarEmpresaHotelPorHabitacionesDesc;
import utils.Log;
import utils.TableModelUtils;
import view.AdministracionHotelesDialog;

/***
 * Controlador de la vista del administrador de hoteles
 * 
 * @author cesco
 *
 */
public class ControladorAdministracionHoteles {

	private AdministracionHotelesDialog hotelesDialog;
	private Database database;

	public ControladorAdministracionHoteles(AdministracionHotelesDialog hotelesDialog, Database database) {
		this.hotelesDialog = hotelesDialog;
		this.database = database;

		/***
		 * Join hoteles con empresas Creo una lista con todas las listas y las muestro
		 * en la tabla
		 */
		hotelesDialog.actualizarTabla(e -> {
			Log.i("Administrador consultó tabla Hoteles Empresas");
			List<EmpresaHotel> join = new ArrayList<>();

			List<Empresa> list = database.getEmpresas();
			ListIterator<Empresa> itr = list.listIterator();
			while (itr.hasNext()) {
				List<EmpresaHotel> empresaHotel = itr.next().getEmpresasHoteles();
				ListIterator<EmpresaHotel> itrEH = empresaHotel.listIterator();
				while (itrEH.hasNext()) {
					EmpresaHotel eh = itrEH.next();
					join.add(eh);
				}
			}
			ordenarEmpresaHotel(hotelesDialog, join);
			TableModel tableModel = TableModelUtils.listJoinHotelEmpresas(join);

			hotelesDialog.getTabla().setModel(tableModel);

		});

		/***
		 * Este evento lanza la query que agrupará el numero de habitaciones por
		 * empresa. Esta query no es posible hacerla en HQL.
		 */
		hotelesDialog.agruparHabitacionesPorHotel(e -> {

			String query = "select sum(h.numhab) as 'Total habitaciones', empresa.nombre_compania "
					+ "from (select count(*) as numhab, habitacion.id_hotel as id "
					+ "from habitacion join hotel on habitacion.id_hotel = hotel.id_hotel group by habitacion.id_hotel) h"
					+ " inner join empresa_hotel on empresa_hotel.id_hotel = h.id "
					+ "inner join empresa on empresa_hotel.id_empresa = empresa.id_empresa group by empresa_hotel.id_empresa";
			List result = database.nativeQuery(query);

			TableModel tableModel = TableModelUtils.listHabitacionesAgrupadasPorCompania(result);

			hotelesDialog.getTabla().setModel(tableModel);
		});

		hotelesDialog.verHotelesMasRentables(e -> {

			String query = "select sum(r.precio) as suma, h.nombre from Reserva r inner join r.hotel h group by r.hotel order by suma desc";
			List result = database.query(query);
			TableModel tableModel = TableModelUtils.listHotelesMasRentables(result);
			hotelesDialog.getTabla().setModel(tableModel);
		});

		hotelesDialog.verHabitacionesMasBaratas(e -> {

			String query = "select min(hab.precio) as m, hot.nombre from Habitacion hab inner join hab.hotel hot group by hot.nombre order by m ";
			List result = database.query(query);

			TableModel tableModel = TableModelUtils.listHabitacionMas(result);
			hotelesDialog.getTabla().setModel(tableModel);
		});

		hotelesDialog.verHabitacionesMasCaras(e -> {
			String query = "select max(hab.precio) as m, hot.nombre from Habitacion hab inner join hab.hotel hot group by hot.nombre order by m desc";
			List result = database.query(query);
			TableModel tableModel = TableModelUtils.listHabitacionMas(result);
			hotelesDialog.getTabla().setModel(tableModel);
		});

		hotelesDialog.verHabitacionesNoReservadasNunca(e -> {
			String query = "select hab.idHabitacion, hab.categoriaHabitacion, hab.precio, hot.nombre from Habitacion hab "
					+ "inner join hab.hotel hot "
					+ "left outer join hab.reservas r where r.habitacion is null order by hab.idHabitacion";

			List result = database.query(query);
			
			TableModel tableModel = TableModelUtils.listHabitacionesSinReserva(result);
			hotelesDialog.getTabla().setModel(tableModel);
		});

	}

	private void ordenarEmpresaHotel(AdministracionHotelesDialog dialog, List<EmpresaHotel> list) {
		if (!dialog.getOrden().equals("")) {
			switch (dialog.getOrden()) {
			case "Compañía":
				Collections.sort(list, new OrdenarEmpresaHotelPorEmpresa());
				break;
			case "Cantidad habitaciones asc.":
				Collections.sort(list, new OrdenarEmpresaHotelPorHabitacionesAsc());
				break;
			case "Cantidad habitaciones desc.":
				Collections.sort(list, new OrdenarEmpresaHotelPorHabitacionesDesc());
				break;
			}

		}
	}
}
