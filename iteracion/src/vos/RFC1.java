package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC1
 * 
	El formato del archivo Json es
	
	{
		"nombre": "13-03-2018", 
		"ingreso": "13-03-2018"
	}
	
 * @author s.beltran
 *
 */
public class RFC1 {

	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="ingreso")
	private int ingreso; 


	//Constructor
	public RFC1 (@JsonProperty(value = "nombre")String nombre, @JsonProperty(value = "ingreso")int ingreso)
	{
		this.nombre = nombre; 
		this.ingreso = ingreso; 
	}
	
	
	
	
}
