package modelo;

import java.util.HashMap;
import java.util.Map;

public enum CategoriaHotel {
	
	CERO_ESTRELLAS(0),
	UNA_ESTRELLA(1),
	DOS_ESTRELLAS(2),
	TRES_ESTRELLAS(3),
	CUATRO_ESTRELLAS(4),
	CINCO_ESTRELLAS(5);
	
	private int estrellas;
	
	private static Map<Integer, CategoriaHotel> map = new HashMap<Integer, CategoriaHotel>();
	
	static {
		for (CategoriaHotel c: CategoriaHotel.values()) {			
			map.put(c.estrellas, c);
		}
	}
	
	private CategoriaHotel(final int estrellas) {
		this.estrellas = estrellas;
	}
	
	public static CategoriaHotel valueOf(int estrellas) {
		return map.get(estrellas);
	}

}
