package ordenadores;

import java.util.Comparator;

import modelo.EmpresaHotel;

public class OrdenarEmpresaHotelPorEmpresa implements Comparator<EmpresaHotel>{

	@Override
	public int compare(EmpresaHotel o1, EmpresaHotel o2) {
		return o1.getEmpresa().getNombreCompania().compareTo(o2.getEmpresa().getNombreCompania());
	}

}
