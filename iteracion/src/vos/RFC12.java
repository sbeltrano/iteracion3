	package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC12
 * 
	El formato del archivo Json es
	
	{
		 
		"operadorId": 55,
		"nombre": jorge,
		"carnet":201632541
		
	}
	
 * @author s.beltran
 *
 */
public class RFC12 {

	
	@JsonProperty(value="carnet")
	private int carnet;
	
	@JsonProperty(value="nombre")
	private String nombre;

	@JsonProperty(value="operadorId")
	private int operadorId;

	public int getCarnet() {
		return carnet;
	}



	public void setCarnet(int carnet) {
		this.carnet = carnet;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public int getOperadorId() {
		return operadorId;
	}



	public void setOperadorId(int operadorId) {
		this.operadorId = operadorId;
	}



	public RFC12(@JsonProperty(value = "tipoAlojamiento")int carnet, @JsonProperty(value = "alojamientoId") int operadorId,  @JsonProperty(value = "descripcion")String nombre) {
		this.carnet = carnet;
		this.operadorId = operadorId;
		this.nombre = nombre;
	}
	
}
