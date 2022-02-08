package ordenadores;

import java.util.Comparator;

import modelo.Habitacion;

public class OrdenarHabitacionPorPreciosAsc implements Comparator<Habitacion>{

	@Override
	public int compare(Habitacion o1, Habitacion o2) {
		return o1.getPrecio().compareTo(o2.getPrecio());
	}
	
}
