package com.central.integral.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String razonSocial;
	@NotEmpty
	private String nit;
	@NotEmpty
	private String telefono;

	@OneToMany(mappedBy = "cliente")
	private List<Vehiculo> vehiculo;

	public Cliente() {
		super();
	}


	public Cliente(Long id, @NotEmpty String razonSocial, @NotEmpty String nit, @NotEmpty String telefono,
			List<Vehiculo> vehiculo) {
		super();
		this.id = id;
		this.razonSocial = razonSocial;
		this.nit = nit;
		this.telefono = telefono;
		this.vehiculo = vehiculo;
	}


	public List<Vehiculo> getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(List<Vehiculo> vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	private static final long serialVersionUID = 1L;

}
