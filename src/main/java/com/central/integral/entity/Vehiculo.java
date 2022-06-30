package com.central.integral.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "vehiculo")
public class Vehiculo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String placa;
	@NotEmpty
	private String marca;
	@NotEmpty
	private String color;

	private float km;

	private float horas;
	
	@NotEmpty
	private String modelo;

	@ManyToOne
	@JoinColumn(name = "propietario")
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "vehiculo")
	private List<SalidaArticulo> salidaArticulo;
	
	//mappedBy = "vehiculo" es porque al otro lado de la relacion asi se llama el atributo
	@OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) 
	private List<Cotizacion> cotizaciones;

	public Vehiculo() {
		cotizaciones = new ArrayList<Cotizacion>();
	}


	public Vehiculo(Long id, @NotEmpty String placa, @NotEmpty String marca, @NotEmpty String color, float km,
			float horas, @NotEmpty String modelo, Cliente cliente, List<SalidaArticulo> salidaArticulo,
			List<Cotizacion> cotizaciones) {
		super();
		this.id = id;
		this.placa = placa;
		this.marca = marca;
		this.color = color;
		this.km = km;
		this.horas = horas;
		this.modelo = modelo;
		this.cliente = cliente;
		this.salidaArticulo = salidaArticulo;
		this.cotizaciones = cotizaciones;
	}







	public List<SalidaArticulo> getSalidaArticulo() {
		return salidaArticulo;
	}

	public void setSalidaArticulo(List<SalidaArticulo> salidaArticulo) {
		this.salidaArticulo = salidaArticulo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getKm() {
		return km;
	}

	public void setKm(float km) {
		this.km = km;
	}

	public float getHoras() {
		return horas;
	}

	public void setHoras(float horas) {
		this.horas = horas;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	
	
	public List<Cotizacion> getCotizaciones() {
		return cotizaciones;
	}



	public void setCotizaciones(List<Cotizacion> cotizaciones) {
		this.cotizaciones = cotizaciones;
	}



	public void addCotizacion(Cotizacion cotizacion) {
		
		cotizaciones.add(cotizacion);
	
	}

	private static final long serialVersionUID = 1L;

}
