	package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC12
 * 
	El formato del archivo Json es
	
	{
		 
		"tipoAlojamiento": vivienda,
		"alojamientoId": 3
		"descripcion": nombre
		
	}
	
 * @author s.beltran
 *
 */
public class RFC12b {

	
	@JsonProperty(value="tipoAlojamiento")
	private String tipoAlojamiento;
	
	@JsonProperty(value="descripcion")
	private String descripcion;

	@JsonProperty(value="alojamientoId")
	private int alojamientoId;
	
	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoAlojamiento() {
		return tipoAlojamiento;
	}


	public void setTipoAlojamiento(String tipoAlojamiento) {
		this.tipoAlojamiento = tipoAlojamiento;
	}


	public int getAlojamientoId() {
		return alojamientoId;
	}


	public void setAlojamientoId(int alojamientoId) {
		this.alojamientoId = alojamientoId;
	}


	public RFC12b(@JsonProperty(value = "tipoAlojamiento")String tipoAlojamiento, @JsonProperty(value = "alojamientoId") int alojamientoId,  @JsonProperty(value = "descripcion")String descripcion) {
		this.tipoAlojamiento = tipoAlojamiento;
		this.alojamientoId = alojamientoId;
		this.descripcion = descripcion;
	}
	
}
