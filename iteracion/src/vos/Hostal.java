package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Clase que representa un hostal
 * @author jp.campos
 
 
  
 {
 	"nombre": "Doña Blanca",
 	"capacidad": 10,
 	"disponibilidad": 5,
 	"registroCamaraComercio": "registro120", 
 	"registroSuperIntendencia": "registro123", 
 	"ubicacion": "Calle 123 # 32-10", 
 	"id" : 2, 
 	"horaApertura": "8:00", 
 	"horaCierre": "18:00",
 	"habitaciones": [
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
 		}, 
 		{
 		"id" : 2, 
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
 		}]
 }
 
 
 
 
 */
public class Hostal extends Hotel{

	
	
	
	@JsonProperty (value="horaApertura")
	private String horaApertura;
	
	@JsonProperty(value = "horaCierre")
	private String horaCierre;
	
	
	public Hostal(@JsonProperty(value="nombre")String nombre, @JsonProperty(value = "habitaciones")List<Habitacion>habitaciones, @JsonProperty(value="id")int id,  @JsonProperty(value = "capacidad")int capacidad,@JsonProperty(value = "disponibilidad") int disponibilidad, @JsonProperty(value = "registroCamaraComercio")String registroCamaraComercio,
			@JsonProperty(value = "registroSuperIntendencia")String registroSuperIntendencia,
			@JsonProperty(value = "ubicacion")String ubicacion,@JsonProperty(value = "horaApertura")String horaApertura,@JsonProperty(value = "horaCierre")String horaCierre ) {
		
		super(nombre, habitaciones, id, capacidad, disponibilidad, registroCamaraComercio, registroSuperIntendencia, ubicacion); 

		this.horaApertura =horaApertura;
		this.horaCierre=horaCierre;
		
		
	}


	public String getHoraApertura() {
		return horaApertura;
	}


	public void setHoraApertura(String horaApertura) {
		this.horaApertura = horaApertura;
	}


	public String getHoraCierre() {
		return horaCierre;
	}


	public void setHoraCierre(String horaCierre) {
		this.horaCierre = horaCierre;
	}
	
	
	
	
	
	

}
