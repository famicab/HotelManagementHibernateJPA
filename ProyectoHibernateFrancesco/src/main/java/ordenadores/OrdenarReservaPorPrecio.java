package ordenadores;

import java.util.Comparator;

import modelo.Reserva;

public class OrdenarReservaPorPrecio implements Comparator<Reserva>{

	@Override
	public int compare(Reserva o1, Reserva o2) {
		return o1.getPrecio().compareTo(o2.getPrecio());
	}

}
