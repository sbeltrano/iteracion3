package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa un apartamento
 
  

 
 
 
 */
public class Apartamento {

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="amoblado")
	private boolean amoblado;
	
	@JsonProperty(value="ocupado")
	private boolean ocupado;
	
	@JsonProperty(value="ubicacion")
	private String ubicacion;

	public Apartamento(@JsonProperty(value ="id")int id, @JsonProperty(value="amoblado")boolean amoblado, @JsonProperty(value="ocupado")boolean ocupado, @JsonProperty(value="ubicacion")String ubicacion) {

		this.id = id;
		this.amoblado = amoblado;
		this.ocupado = ocupado;
		this.ubicacion = ubicacion;
	}

	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public boolean isAmoblado() {
		return amoblado;
	}


	public void setAmoblado(boolean amoblado) {
		this.amoblado = amoblado;
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
