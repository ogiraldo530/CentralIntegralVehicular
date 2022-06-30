package com.central.integral.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "orden_de_trabajo")
public class OrdenDeTrabajo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaSalida;

	@OneToOne
	@JoinColumn(name = "cotizacion_id", updatable = false, nullable = false)
	private Cotizacion cotizacion;

	public OrdenDeTrabajo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrdenDeTrabajo(Long id, Date fechaSalida, Cotizacion cotizacion) {
		super();
		this.id = id;
		this.fechaSalida = fechaSalida;
		this.cotizacion = cotizacion;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = sumarRestarDiasFecha(fechaSalida);
	}

	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}
	
	public Date sumarRestarDiasFecha(Date fecha ) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(fecha); // Configuramos la fecha que se recibe

		calendar.add(Calendar.DAY_OF_YEAR, 1); // numero de días a añadir, o restar en caso de días<0

		return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos

	}

	private static final long serialVersionUID = 1L;

}
