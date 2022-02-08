package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="ciudad")
public class Ciudad implements Serializable{

	@Id
	@Column(name="id_ciudad", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="pais")
	private String pais;
	
	@OneToMany(mappedBy="ciudad", cascade = CascadeType.ALL)
	private List<Hotel> hoteles = new ArrayList<>();

	public Ciudad(String nombre, String pais) {
		this.nombre = nombre;
		this.pais = pais;
	}

	public Ciudad() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public List<Hotel> getHoteles() {
		return hoteles;
	}

	public void setHoteles(List<Hotel> hoteles) {
		this.hoteles = hoteles;
	}
	
	public void addHotel(Hotel hotel) {
		hoteles.add(hotel);
		hotel.setCiudad(this);
	}
	
	public void removeHotel(Hotel hotel) {
		hoteles.remove(hotel);
		hotel.setCiudad(null);
	}

	@Override
	public boolean equals(Object obj) {
		Ciudad c = (Ciudad)obj;
		return this.nombre.equals(c.nombre) && this.pais.equals(c.pais);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nombre;
	}
	
	
}
