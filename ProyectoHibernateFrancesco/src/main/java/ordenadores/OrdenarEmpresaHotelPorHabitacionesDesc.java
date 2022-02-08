package ordenadores;

import java.util.Comparator;

import modelo.EmpresaHotel;
import modelo.Habitacion;

public class OrdenarEmpresaHotelPorHabitacionesDesc implements Comparator<EmpresaHotel>{

	@Override
	public int compare(EmpresaHotel o1, EmpresaHotel o2) {
		if (o1.getHotel().getHabitaciones().size() > o2.getHotel().getHabitaciones().size()) {
			return -1;
		} else if(o1.getHotel().getHabitaciones().size() < o2.getHotel().getHabitaciones().size()) {
			return 1;
		} else {
			return 0;
		}
	}
	


}
