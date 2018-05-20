package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC10
 * 
	El formato del archivo Json es
	
	{	 
		"usuarioId": 3,
		"nombre": 3
	}
	
 * @author s.beltran
 *
 */
public class RFC11 {

	
	@JsonProperty(value="nombre")
	private String nombre;
	

	@JsonProperty(value="usuarioId")
	private int usuarioId;
	
	@JsonProperty(value="rol")
	private String rol;
	
	@JsonProperty(value="carnet")
	private int carnet;

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

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

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public RFC11(@JsonProperty(value = "nombre")String nombre, @JsonProperty(value = "usuarioId") int usuarioId, @JsonProperty(value = "rol")String rol, @JsonProperty(value = "carnet") int carnet) {
		this.nombre = nombre;
		this.usuarioId = usuarioId;
		this.rol = rol;
		this.carnet = carnet;
	}
}