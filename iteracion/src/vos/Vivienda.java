package vos;

import org.codehaus.jackson.annotate.JsonProperty;



/**
 * 
 * El formato de este archivo es
  
 
  {
  	"menaje": true, 
  	"id": 1, 
  	"operador": null, 
  	"numeroHabitaciones": 4, 
  	"ocupado": false, 
  	"ubicacion": "kilometro 5 a chia"
  
  
  }
 
 	@author K555L
 *
 */

public class Vivienda {

	
	@JsonProperty(value="menaje")
	private boolean menaje; 
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="operador")
	private PersonaOperador operador;
	
	@JsonProperty(value="numeroHabitaciones")
	private int numeroHabitaciones; 
	
	@JsonProperty(value="ocupado")
	private boolean ocupado; 
	
	@JsonProperty(value="ubicacion")
	private String ubicacion;

	public Vivienda( @JsonProperty(value="menaje")boolean menaje,@JsonProperty(value="numeroHabitaciones") int numeroHabitaciones, @JsonProperty(value="ocupado")boolean ocupado, @JsonProperty(value="ubicacion")String ubicacion, @JsonProperty(value="id")int id,  @JsonProperty(value="operador")PersonaOperador operador) {

		this.id = id;
		this.operador = operador;
		this.menaje = menaje;
		this.numeroHabitaciones = numeroHabitaciones;
		this.ocupado = ocupado;
		this.ubicacion = ubicacion;
	}

	public boolean isMenaje() {
		return menaje;
	}

	public void setMenaje(boolean menaje) {
		this.menaje = menaje;
	}

	public int getNumeroHabitaciones() {
		return numeroHabitaciones;
	}
	
	public int getId() {
		return id;
	}

	public PersonaOperador getOperadorId() {
		return operador;
	}
	
	public void setNumeroHabitaciones(int numeroHabitaciones) {
		this.numeroHabitaciones = numeroHabitaciones;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	} 
	

	
	
	
	
	
	
}
