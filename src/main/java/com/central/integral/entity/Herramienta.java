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
@Table(name = "herramienta")
public class Herramienta implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private Integer cantidad;
	
	private String marca;
	
	@NotEmpty
	private String descripcion;
	@NotNull
	private Double valorUnitario;
	
	
	//Muestra el valor unitario en formato dinero
	public String valorUnitario() {
		
		if(valorUnitario == 0.0) {
			return "0.0";
		}else {
		Locale.setDefault(Locale.US);
		DecimalFormat num = new DecimalFormat("#,###");
		return num.format(valorUnitario);
		}

	}

	
	//Calcula el valor base
	public Double valorCalcular() {

		Double valor = valorUnitario * cantidad;
		
		return valor;

	}
	
	
	//Muestra el subtotal en formato dinero
	public String valor() {
		
		if(valorCalcular() == 0.0) {
			return "0.0";
		}else {
		Locale.setDefault(Locale.US);
		DecimalFormat num = new DecimalFormat("#,###");
		return num.format(valorCalcular());
		}

	}

	public Double ivaCalcular() {

		Double iva = valorCalcular()*0.19f;		
		return iva;

	}
	
	public String iva() {
		
		if(ivaCalcular() == 0.0) {
			return "0.0";
		}else {
		Locale.setDefault(Locale.US);
		DecimalFormat num = new DecimalFormat("#,###");
		return num.format(ivaCalcular());
		}

	}

	public Double valorTol() {
		Double valorTotal = valorCalcular()+ivaCalcular();
		return valorTotal;
	}
	
	public String valorTotal() {
		
		if(valorTol() == 0.0) {
			return "0.0";
		}else {
		Locale.setDefault(Locale.US);
		DecimalFormat num = new DecimalFormat("#,###");
		return num.format(valorTol());
		}

	}
	

	public Herramienta() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Herramienta(Long id, @NotNull Integer cantidad, String marca, @NotEmpty String descripcion,
			@NotNull Double valorUnitario) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.marca = marca;
		this.descripcion = descripcion;
		this.valorUnitario = valorUnitario;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}





	private static final long serialVersionUID = 1L;

}
