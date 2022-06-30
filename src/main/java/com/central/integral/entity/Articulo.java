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
@Table(name = "articulo")
public class Articulo implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String descripcion;

	private String categoria;

	private int entrada = 0;

	private int salida = 0;

	private String unidadMedida;

	private int stock;

	@NotNull
	private Double precioCompra;

	@NotNull
	private Double utilidad;

	private String codigo = "RE"; //REPUESTOS

	public int aumentarStock(int cantidad) {

		stock = stock + cantidad;

		return stock;
	}

	public int aumentarEntrada(int cantidad) {

		entrada = entrada + cantidad;

		return entrada;
	}

	public int aumentarSalida(int cantidad) {

		salida = salida + cantidad;

		return salida;
	}

	public int disminuirStock(int cantidad) {

		stock = stock - cantidad;

		return stock;
	}

	public Double margenUtilidad() {
		Double margenUtilidad = (precioCompra * utilidad) / 100;

		return margenUtilidad;
	}

	public String margenUtilidadListar() {
		
		if(margenUtilidad() == 0.0) {
			return "0.0";
		}else {
		Locale.setDefault(Locale.US);
		DecimalFormat num = new DecimalFormat("#,###");
		return num.format(margenUtilidad());
		}

	}

	public String precioCompra() {
		if(precioCompra == 0.0) {
			return "0.0";
		}else {
		Locale.setDefault(Locale.US);
		DecimalFormat num = new DecimalFormat("#,###");
		return num.format(precioCompra);
		}

	}

	public Double precioVenta() {
		
		Double precioVenta = margenUtilidad() + precioCompra;
		return precioVenta;
	}

	public String precioVentaListar() {
		
		if(precioVenta() == 0.0) {
			return "0.0";
		}else {
		Locale.setDefault(Locale.US);
		DecimalFormat num = new DecimalFormat("#,###");
		return num.format(precioVenta());
		}

	}

	public double importeInventario() {
		double importeInventario = precioVenta() * stock;
		return importeInventario;
	}

	public String importeInventarioListar() {
		
		if(importeInventario() == 0.0) {
			return "0.0";
		}else {
		Locale.setDefault(Locale.US);
		DecimalFormat num = new DecimalFormat("#,###");
		return num.format(importeInventario());
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getEntrada() {
		return entrada;
	}

	public void setEntrada(int entrada) {
		this.entrada = entrada;
	}

	public int getSalida() {
		return salida;
	}

	public void setSalida(int salida) {
		this.salida = salida;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}


	public Double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Double precioCompra) {
		this.precioCompra = precioCompra;
	}


	public Double getUtilidad() {
		return utilidad;
	}

	public void setUtilidad(Double utilidad) {
		this.utilidad = utilidad;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}



	private static final long serialVersionUID = 1L;

}
