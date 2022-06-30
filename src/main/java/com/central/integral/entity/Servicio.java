package com.central.integral.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "servicio")
public class Servicio implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String descripcion;
	
	@NotNull
	private Double precio;
	
	
	private String codigo = "MO"; //mano de obra

	public Servicio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Servicio(Long id, @NotEmpty String descripcion, @NotNull Double precio, String codigo) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.precio = precio;
		this.codigo = codigo;
	}

	public String precio() {
		
		if(precio == 0.0) {
			return "0.0";
		}else {
		Locale.setDefault(Locale.US);
		DecimalFormat num = new DecimalFormat("#,###");
		return num.format(precio);
		}

	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Double getPrecio() {
		return precio;
	}


	public void setPrecio(Double precio) {
		this.precio = precio;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	private static final long serialVersionUID = 1L;
}
