package modelo;

import java.util.HashMap;
import java.util.Map;

public enum CategoriaHabitacion{
	
	INDIVIDUAL(0),
	DOBLE(1),
	SUITE_PREMIUM(2);
	
	private int categoria;
	
	private static Map<Integer, CategoriaHabitacion> map = new HashMap<Integer, CategoriaHabitacion>();
	
	static {
		for (CategoriaHabitacion c:CategoriaHabitacion.values()) {			
			map.put(c.categoria, c);
		}
	}
	
	private CategoriaHabitacion(final int categoria) {
		this.categoria = categoria;
	}
	
	public static CategoriaHabitacion valueOf(int categoria) {
		return map.get(categoria);
	}

}
