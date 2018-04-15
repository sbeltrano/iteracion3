package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReservaColectiva {
	

/**
 * Clase que representa una reserva
 * 
	El formato del archivo Json es
	
	{
	
	"reserva":{
		"fechaInicial": "13-03-2018", 
		"fechaFinal": "13-03-2018",
		"precio": 150, 
		"id": 1,
		"cliente" : null
	}, 
	 "servicios":{
 		"id": 1, 
 		"agua": true, 
 		"bañera": true, 
 		"cocineta": false, 
 		"parquedero": true, 
 		"piscina": true, 
 		"recepcion24h": false, 
 		"restaurante" : true, 
 		"sala": false, 
 		"tv": true, 
 		"wifi": true, 
 		"yacuzzi": false
 	},
 	"descripcion": "Habitación Sencilla", 
 	"id": 1,
 	"numeroReservas":10
 	}
	
 * @author jp.campos
 *
 */
	
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	
	@JsonProperty(value="numeroReservas")
	private int numeroReservas;
	
	
	@JsonProperty(value="servicios")
	private Servicios servicios ;
	
	
	@JsonProperty(value="id")
	private int id;

	@JsonProperty(value="reserva")
	private Reserva reserva;
	
	
	public ReservaColectiva(@JsonProperty(value="reserva") Reserva reserva,@JsonProperty(value="descripcion")String descripcion, @JsonProperty(value="numeroReservas")int numeroReservas, @JsonProperty(value="servicios")Servicios servicios,@JsonProperty(value="id") int id) {
		super();
		this.reserva = reserva;
		this.descripcion = descripcion;
		this.numeroReservas = numeroReservas;
		this.servicios = servicios;
		this.id = id;
	}


	public Reserva getReserva() {
		return reserva;
	}


	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public int getNumeroReservas() {
		return numeroReservas;
	}


	public void setNumeroReservas(int numeroReservas) {
		this.numeroReservas = numeroReservas;
	}


	public Servicios getServicios() {
		return servicios;
	}


	public void setServicios(Servicios servicios) {
		this.servicios = servicios;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
	

}
