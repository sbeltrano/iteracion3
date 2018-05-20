package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC10
 * 
	El formato del archivo Json es
	
	{
		 
		"nombre": omar,
		"clienteId": 3,
		"carnet":20187236
	}
	
 * @author s.beltran
 *
 */
public class RFC13 {

	
	@JsonProperty(value="nombre")
	private String nombre;
	

	@JsonProperty(value="clienteId")
	private int clienteId;
	
	@JsonProperty(value="carnet")
	private int carnet;

	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public int getClienteId() {
		return clienteId;
	}



	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}



	public int getCarnet() {
		return carnet;
	}



	public void setCarnet(int carnet) {
		this.carnet = carnet;
	}



	public RFC13(@JsonProperty(value = "nombre")String nombre, @JsonProperty(value = "clienteId") int clienteId, @JsonProperty(value = "carnet") int carnet) {
		this.carnet = carnet;
		this.clienteId = clienteId;
		this.nombre = nombre;
	}
	
}
