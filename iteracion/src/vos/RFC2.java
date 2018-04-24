package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC2
 * 
	El formato del archivo Json es
	
	{ 
		"alojamientoId": 1,
		"nombreAlojamiento": 3
	}
	
 * @author s.beltran
 *
 */
public class RFC2 {
	
	@JsonProperty(value="alojamientoId")
	private int alojamientoId;
	
	
	@JsonProperty(value = "vecesRentado")
	private int vecesRentado; 
		
	
	

	public int getVecesRentado() {
		return vecesRentado;
	}




	public void setVecesRentado(int vecesRentado) {
		this.vecesRentado = vecesRentado;
	}




	public int getAlojamientoId() {
		return alojamientoId;
	}




	public void setAlojamientoId(int alojamientoId) {
		this.alojamientoId = alojamientoId;
	}

	//Constructor
	public RFC2 (@JsonProperty(value = "alojamientoId")int alojamientoId, @JsonProperty(value = "vecesRentado")int vecesRentado)
	{
		this.alojamientoId = alojamientoId; 
		this.vecesRentado = vecesRentado; 
	}
	
	
	
	
}
