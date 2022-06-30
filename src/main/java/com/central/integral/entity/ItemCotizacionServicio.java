package com.central.integral.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_cotizacion_servicio")
public class ItemCotizacionServicio implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer cantidad;

	private Integer iva;

	private Integer descuento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "servicio_id")
	private Servicio servicio;

	@ManyToOne
	@JoinColumn(name = "cotizacion_id")
	private Cotizacion cotizacion;

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

	public Integer getIva() {
		return iva;
	}

	public void setIva(Integer iva) {
		this.iva = iva;
	}

	public Integer getDescuento() {
		return descuento;
	}

	public void setDescuento(Integer descuento) {
		this.descuento = descuento;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public Double subTotal() {
		return (cantidad.doubleValue() * servicio.getPrecio());
	}

	public String subTotalMostrar() {

		if (subTotal() == 0.0) {
			return "0.0";
		} else {
			Locale.setDefault(Locale.US);
			DecimalFormat num = new DecimalFormat("#,###");
			return num.format(subTotal());
		}

	}

	public Double iva() {
		return (subTotal() * getIva()) / 100;
	}

	public String ivaMostrar() {

		if (iva() == 0.0) {
			return "0.0";
		} else {
			Locale.setDefault(Locale.US);
			DecimalFormat num = new DecimalFormat("#,###");
			return num.format(iva());
		}

	}

	public Double descuento() {
		return (subTotal() * getDescuento()) / 100;
	}

	public String descuentoMostrar() {

		if (descuento() == 0.0) {
			return "0.0";
		} else {
			Locale.setDefault(Locale.US);
			DecimalFormat num = new DecimalFormat("#,###");
			return num.format(descuento());
		}

	}

	public Double calcularImporte() {
		return (subTotal() - descuento());
	}

	public String calcularImporteMostrar() {

		if (calcularImporte() == 0.0) {
			return "0.0";
		} else {
			Locale.setDefault(Locale.US);
			DecimalFormat num = new DecimalFormat("#,###");
			return num.format(calcularImporte());
		}

	}

	public Double calcularImporteOrden() {
		return (subTotal() - descuento() + iva());
	}

	public String calcularImporteOrdenMostrar() {

		if (calcularImporteOrden() == 0.0) {
			return "0.0";
		} else {
			Locale.setDefault(Locale.US);
			DecimalFormat num = new DecimalFormat("#,###");
			return num.format(calcularImporteOrden());
		}

	}

	private static final long serialVersionUID = 1L;
}
