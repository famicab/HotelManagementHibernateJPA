package modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="reserva")
public class Reserva implements Serializable{
	
	@Id
	@Column(name="id_reserva", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idReserva;
	
	@ManyToOne
	@JoinColumn(name="id_cliente", foreignKey = @ForeignKey(name="fk_reserv_clien"))
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="id_habitacion", foreignKey = @ForeignKey(name="fk_reserv_hab"))
	private Habitacion habitacion;
	
	@ManyToOne
	@JoinColumn(name="id_hotel", foreignKey = @ForeignKey(name="fk_reserv_hot"))
	private Hotel hotel;
	
	@Column(name="fecha_reserva")
	private Date fechaReserva;
	
	@Column(name="fecha_entrada")
	private Date fechaEntrada;
	
	@Column(name="fecha_salida")
	private Date fechaSalida;
	
	@Column(name="precio")
	private Double precio;
	
	@OneToOne(mappedBy="reserva", cascade = CascadeType.ALL)
	private Pago pago;

	public Reserva(int idReserva, Usuario usuario, Habitacion habitacion, Hotel hotel, Date fechaReserva,
			Date fechaEntrada, Date fechaSalida, Double precio) {
		this.idReserva = idReserva;
		this.usuario = usuario;
		this.habitacion = habitacion;
		this.hotel = hotel;
		this.fechaReserva = fechaReserva;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.precio = precio;
	}
	
	public Reserva(int idReserva) {
		this.idReserva = idReserva;
	}
	
	public Reserva(Usuario usuario, Habitacion habitacion, Hotel hotel, Date fechaReserva, Date fechaEntrada, Date fechaSalida, Double precio) {
		this.usuario = usuario;
		this.habitacion = habitacion;
		this.hotel = hotel;
		this.fechaReserva = fechaReserva;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.precio = precio;
	}

	public Reserva() {
		super();
	}

	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Date getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(Date fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Reserva r = (Reserva)obj;
		return this.idReserva == r.idReserva;
	}

	@Override
	public String toString() {
		String factura = String.format("%-21s %d%n"
				+ "%-21s (%s)%n"
				+ "%-21s%n"
				+ "%-21S %d %s%n"
				+ "%-21S %-10s %n"
				+ "%-21S %-10s %n"
				+ "%-21S %-10s %n"
				+ "%-21S %.2f%s",
				"ID FACTURA",this.idReserva,
				this.hotel.getNombre(), this.hotel.getCiudad(),			
				this.usuario.getEmail(),
				"habitacion ",this.habitacion.getIdHabitacion(),this.habitacion.getCategoriaHabitacion(), 
				"fecha de reserva ",this.fechaReserva,
				"fecha de entrada ",this.fechaEntrada,
				"fecha de salida ",this.fechaSalida,
				"precio total",this.precio, "€");
		return factura;
	}

	

}
