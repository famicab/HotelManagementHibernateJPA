package modelo;

import javax.persistence.*;

@Entity
@IdClass(EmpresaHotelId.class)
@Table(name="empresa_hotel")
public class EmpresaHotel {

	@Id
	@ManyToOne
	@JoinColumn(name="id_hotel", insertable = false, updatable = false)
	private Hotel hotel;
	
	@Id
	@ManyToOne
	@JoinColumn(name="id_empresa", insertable = false, updatable = false)
	private Empresa empresa;

	public EmpresaHotel(Hotel hotel, Empresa empresa) {
		this.hotel = hotel;
		this.empresa = empresa;
	}

	public EmpresaHotel() {
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return "Empresa: "+this.empresa+". Hotel: "+this.hotel;
	}
	
	
}
