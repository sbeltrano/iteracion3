package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC8
 * 
	El formato del archivo Json es
	
	{
		 
		"tipoAlojamiento": vivienda,
		"alojamientoId": 3
	}
	
 * @author s.beltran
 *
 */
public class RFC9 {

	
	@JsonProperty(value="tipoAlojamiento")
	private String tipoAlojamiento;
	

	@JsonProperty(value="alojamientoId")
	private int alojamientoId;
	
	
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


	public RFC9(@JsonProperty(value = "tipoAlojamiento")String tipoAlojamiento, @JsonProperty(value = "alojamientoId") int alojamientoId) {
		this.tipoAlojamiento = tipoAlojamiento;
		this.alojamientoId = alojamientoId;
	}
	
}
