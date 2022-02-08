package ordenadores;

import java.util.Comparator;

import modelo.Habitacion;

public class OrdenarHabitacionPorPreciosDesc implements Comparator<Habitacion>{


		@Override
		public int compare(Habitacion o1, Habitacion o2) {
			return o2.getPrecio().compareTo(o1.getPrecio());
		}
		
	
}
