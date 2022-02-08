package modelo;

import java.io.Serializable;

public class EmpresaHotelId implements Serializable{

	private int empresa;
	private int hotel;
	
	public EmpresaHotelId() {
	}

	public int getEmpresa() {
		return empresa;
	}

	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}

	public int getHotel() {
		return hotel;
	}

	public void setHotel(int hotel) {
		this.hotel = hotel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + empresa;
		result = prime * result + hotel;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpresaHotelId other = (EmpresaHotelId) obj;
		if (empresa != other.empresa)
			return false;
		if (hotel != other.hotel)
			return false;
		return true;
	}


	
	
}
