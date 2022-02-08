package modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="pago")
public class Pago implements Serializable{

	@Id
	@Column(name="id_pago")
	private int id;
	
	@Column(name="fecha_pago")
	private Date fechaPago;
	
	@Column(name="cantidad")
	private Double cantidad;
	
	@Column(name="tipo_pago")
	private String tipoPago;
	
	@OneToOne
	@JoinColumn(name="id_reserva", updatable = false, nullable = false, foreignKey = @ForeignKey(name="id_reserva"))
	private Reserva reserva;

	public Pago(Date fechaPago, Double cantidad, String tipoPago, Reserva reserva) {
		this.fechaPago = fechaPago;
		this.cantidad = cantidad;
		this.tipoPago = tipoPago;
		this.reserva = reserva;
	}

	public Pago() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	
	
	
}
