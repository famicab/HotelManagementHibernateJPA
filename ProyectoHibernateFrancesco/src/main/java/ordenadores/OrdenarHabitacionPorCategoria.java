package ordenadores;

import java.util.Comparator;

import modelo.Habitacion;

public class OrdenarHabitacionPorCategoria implements Comparator<Habitacion>{

	@Override
	public int compare(Habitacion o1, Habitacion o2) {
		return o1.getCategoriaHabitacion().compareTo(o2.getCategoriaHabitacion());
	}

}
