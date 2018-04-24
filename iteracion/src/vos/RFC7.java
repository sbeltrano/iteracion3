package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC7
 * 
	El formato del archivo Json es
	
	{
		 
		"alojamientoId": 3, 
		"diasPopular": 8, 
		"ubicacion": "5222",
	}
	
 * @author s.beltran
 *
 */
public class RFC7 {

	

	@JsonProperty(value="alojamientoId")
	private int alojamientoId;
	
	@JsonProperty(value="diasPopular")
	private int diasPopular;

	@JsonProperty(value="ubicacion")
	private String ubicacion;

	public int getAlojamientoId() {
		return alojamientoId;
	}





	public int getDiasPopular() {
		return diasPopular;
	}





	public void setDiasPopular(int diasPopular) {
		this.diasPopular = diasPopular;
	}





	public String getUbicacion() {
		return ubicacion;
	}





	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}





	public void setAlojamientoId(int alojamientoId) {
		this.alojamientoId = alojamientoId;
	}





	public RFC7(@JsonProperty(value = "alojamientoId")int alojamientoId, @JsonProperty(value = "diasPopular")int diasPopular, @JsonProperty(value = "ubicacion")String ubicacion) {
		this.alojamientoId = alojamientoId;
		this.diasPopular = diasPopular;
		this.ubicacion = ubicacion;
	}

	
	
	
}
