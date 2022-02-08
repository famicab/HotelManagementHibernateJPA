package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "hotel")
public class Hotel implements Serializable, Comparable<Hotel>{

	@Id
	@Column(name="id_hotel", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name="id_ciudad", foreignKey = @ForeignKey(name="fk_hotel"))
	private Ciudad ciudad;

	@Column(name="nombre")
	private String nombre;

	@Column(name = "estrellas")
	@Enumerated(EnumType.ORDINAL)
	private CategoriaHotel categoria;
	
	@OneToMany(mappedBy="hotel", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Habitacion> habitaciones = new ArrayList<>();
	
	@OneToMany(mappedBy="hotel", cascade = CascadeType.ALL, orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Reserva> reservas = new ArrayList<>();
	
	@OneToMany(mappedBy ="hotel", cascade = CascadeType.ALL, orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<EmpresaHotel> duenos = new ArrayList<>();

	public Hotel(String nombre, CategoriaHotel categoria) {
		this.nombre = nombre;
		this.categoria = categoria;
	}

	public Hotel() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CategoriaHotel getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaHotel categoria) {
		this.categoria = categoria;
	}

	@Override
	public boolean equals(Object obj) {
		Hotel h = (Hotel) obj;
		return this.id == h.id;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		return this.nombre + " - "+ this.ciudad.getNombre();
	}
	
	public List<Habitacion> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(List<Habitacion> hoteles) {
		this.habitaciones = hoteles;
	}
	
	public void addHabitacion(Habitacion habitacion) {
		habitaciones.add(habitacion);
		habitacion.setHotel(this);
	}
	
	public void removeHabitacion(Habitacion habitacion) {
		habitaciones.remove(habitacion);
		habitacion.setHotel(null);
	}
	
	public List<Reserva> getReservas(){
		return reservas;
	}
	
	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public void addReserva(Reserva reserva) {
		reservas.add(reserva);
		reserva.setHotel(this);
	}
	
	public void removeReserva(Reserva reserva) {
		reservas.remove(reserva);
		reserva.setHotel(null);
	}

	@Override
	public int compareTo(Hotel o) {
			return this.getNombre().compareTo(o.getNombre());	
	}
	
	public List<EmpresaHotel> getDuenos(){
		return this.duenos;
	}
	

}
