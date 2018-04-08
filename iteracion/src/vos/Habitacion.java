package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa una habitación
 * @author jp.campos
 
 
 
 	El formato JSON es 
 	
 	
 	{
 		"id" : 1, 
 		"descripcion" : "Suite",
 		"ocupada" : true,
 		"compartida" : false,
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
 		
 		
 		}
 	}
 
 
 */
public class Habitacion {

	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="compartida")
	private boolean compartida;
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	@JsonProperty(value="ocupada")
	private boolean ocupada;
	
	@JsonProperty(value = "servicios")
	private Servicios servicios; 

	public Habitacion(@JsonProperty (value = "servicios")Servicios servicios, @JsonProperty(value="id")int id,@JsonProperty(value="compartida")boolean compartida, @JsonProperty(value="descripcion")String descripcion, @JsonProperty(value="ocupada") boolean ocupada) {
		this.id = id; 
		this.compartida = compartida;
		this.descripcion = descripcion;
		this.ocupada = ocupada;
		this.servicios = servicios; 
		
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


	public boolean isCompartida() {
		return compartida;
	}

	public void setCompartida(boolean compartida) {
		this.compartida = compartida;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean getOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}
	
	
	
}
