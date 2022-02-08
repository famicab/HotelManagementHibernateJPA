package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="habitacion")
public class Habitacion implements Serializable{
	
	@Id
	@Column(name="id_habitacion", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idHabitacion;
	
	@ManyToOne
	@JoinColumn(name="id_hotel", foreignKey = @ForeignKey(name="fk_habitacion"))
	private Hotel hotel;
	
	@Column(name = "tipo")
	@Enumerated(EnumType.ORDINAL)
	private CategoriaHabitacion categoriaHabitacion;
	
	@Column(name="precio")
	private Double precio;
	
	@OneToMany(mappedBy="habitacion", cascade = CascadeType.ALL, orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Reserva> reservas = new ArrayList<>();

	public Habitacion(int idHabitacion, Hotel hotel, CategoriaHabitacion categoriaHabitacion, Double precio) {
		this.idHabitacion = idHabitacion;
		this.hotel = hotel;
		this.categoriaHabitacion = categoriaHabitacion;
		this.precio = precio;
	}

	public Habitacion() {

	}

	public int getIdHabitacion() {
		return idHabitacion;
	}

	public void setIdHabitacion(int idHabitacion) {
		this.idHabitacion = idHabitacion;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public CategoriaHabitacion getCategoriaHabitacion() {
		return categoriaHabitacion;
	}

	public void setCategoriaHabitacion(CategoriaHabitacion categoriaHabitacion) {
		this.categoriaHabitacion = categoriaHabitacion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	@Override
	public boolean equals(Object obj) {
		Habitacion h = (Habitacion)obj;
		return this.idHabitacion == h.idHabitacion;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	public List<Reserva> getReservas(){
		return reservas;
	}
	
	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public void addReserva(Reserva reserva) {
		reservas.add(reserva);
		reserva.setHabitacion(this);
	}
	
	public void removeReserva(Reserva reserva) {
		reservas.remove(reserva);
		reserva.setHabitacion(null);
	}

}

