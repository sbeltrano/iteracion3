package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC3
 * 
	El formato del archivo Json es
	
	{
		 
		"tipoAlojamiento": vivienda,
		"AlojamientoId":58
		"Estado": 1
	}
	
 * @author s.beltran
 *
 */
public class RFC3 {

	
	@JsonProperty(value="tipoAlojamiento")
	private String tipoAlojamiento;
	

	@JsonProperty(value="alojamientoId")
	private int alojamientoId;
	
	@JsonProperty(value="estado")
	private int estado;

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
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public RFC3(@JsonProperty(value = "tipoAlojamiento")String tipoAlojamiento, @JsonProperty(value = "alojamientoId")int alojamientoId, @JsonProperty(value = "estado")int estado) {
		this.tipoAlojamiento = tipoAlojamiento;
		this.alojamientoId = alojamientoId;
		this.estado = estado;
	}

}
