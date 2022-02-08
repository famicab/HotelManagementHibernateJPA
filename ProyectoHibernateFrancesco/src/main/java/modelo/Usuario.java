package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{
	
	@Id
	@Column(name="id_usuario", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idUsuario;
	
	@Column(name="fecha_registro")
	private Date fechaRegistro;
	
	@Column(name="email", unique=true)
	private String email;
	
	@Column(name="contrasena")
	private String contrasena;
	
	@OneToMany(mappedBy="usuario", cascade = CascadeType.ALL, orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Reserva> reservas = new ArrayList<>();
	
	public Usuario(Date fechaRegistro, String email, String contrasena) {
		this.fechaRegistro = fechaRegistro;
		this.email = email;
		this.contrasena = contrasena;
	}
	
	public Usuario(int id, Date fechaRegistro, String email, String contrasena) {
		this.idUsuario = id;
		this.fechaRegistro = fechaRegistro;
		this.email = email;
		this.contrasena = contrasena;
	}

	public Usuario() {
		
	}

	public int getId() {
		return idUsuario;
	}

	public void setId(int id) {
		this.idUsuario = id;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Override
	public boolean equals(Object arg0) {
		Usuario u = (Usuario)arg0;
		return this.getEmail().contentEquals(u.getEmail()) && this.getContrasena().contentEquals(u.getContrasena());
	}

	@Override
	public String toString() {
		return this.email;
	}
	
	public List<Reserva> getReservas(){
		return reservas;
	}
	
	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public void addReserva(Reserva reserva) {
		reservas.add(reserva);
		reserva.setUsuario(this);
	}
	
	public void removeReserva(Reserva reserva) {
		reservas.remove(reserva);
		reserva.setUsuario(null);
	}

}
