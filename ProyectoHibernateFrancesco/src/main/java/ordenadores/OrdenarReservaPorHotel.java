package ordenadores;

import java.util.Comparator;

import modelo.Reserva;

public class OrdenarReservaPorHotel implements Comparator<Reserva>{

	@Override
	public int compare(Reserva o1, Reserva o2) {
		return o1.getHotel().compareTo(o2.getHotel());
	}

}
