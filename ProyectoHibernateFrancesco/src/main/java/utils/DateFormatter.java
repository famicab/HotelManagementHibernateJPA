package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * Clase que convierte un String a fecha.
 * @author Francesco De Amicis
 *
 */
public class DateFormatter {

	public static String getDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");	
		String fecha = dateFormat.format(date);
		return fecha;
	}
	

}
