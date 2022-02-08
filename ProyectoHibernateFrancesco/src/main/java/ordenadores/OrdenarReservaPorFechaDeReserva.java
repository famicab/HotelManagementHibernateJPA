package ordenadores;

import java.util.Comparator;

import modelo.Reserva;

public class OrdenarReservaPorFechaDeReserva implements Comparator<Reserva>{

	@Override
	public int compare(Reserva o1, Reserva o2) {
		return o1.getFechaReserva().compareTo(o2.getFechaReserva());
	}

}
