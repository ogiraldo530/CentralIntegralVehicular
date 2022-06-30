package com.central.integral.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "entradaArticulo")
public class EntradaArticulo implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String fecha = obtenerFechaYHoraActual();
	
	@NotEmpty
	private String responsable;
	
	@NotNull
	private Integer cantidad;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "articulo")
	private Articulo articulo;
	
	public EntradaArticulo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String obtenerFechaYHoraActual() {
		String formato = "dd-MM-yyyy \n HH:mm:ss"; 
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
		LocalDateTime ahora = LocalDateTime.now();
		return formateador.format(ahora);
	}


	public EntradaArticulo(Long id, String fecha, @NotEmpty String responsable, @NotNull Integer cantidad,
			Articulo articulo) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.responsable = responsable;
		this.cantidad = cantidad;
		this.articulo = articulo;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}



	private static final long serialVersionUID = 1L;
	
}
