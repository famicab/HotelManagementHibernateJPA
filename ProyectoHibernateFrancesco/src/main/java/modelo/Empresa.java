package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="empresa")
public class Empresa implements Serializable{
	
	@Id
	@Column(name="id_empresa", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idEmpresa;
	
	@Column(name="nombre_compania")
	private String nombreCompania;
	
	@OneToMany(mappedBy ="empresa", cascade = CascadeType.ALL, orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<EmpresaHotel> hoteles = new ArrayList<>();

	public Empresa(String nombreCompania) {
		this.nombreCompania = nombreCompania;
	}

	public Empresa() {
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNombreCompania() {
		return nombreCompania;
	}

	public void setNombreCompania(String nombreCompania) {
		this.nombreCompania = nombreCompania;
	}
	
	public List<EmpresaHotel> getEmpresasHoteles(){
		return this.hoteles;
	}

	@Override
	public String toString() {
		return this.nombreCompania;
	}

}
