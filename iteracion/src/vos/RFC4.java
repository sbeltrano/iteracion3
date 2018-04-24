package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC4
 * 
	El formato del archivo Json es
	
	{
		"alojamientoIdDisponible": 1,
		"nombreAlojamiento": papalos
	}
	
 * @author s.beltran
 *
 */
public class RFC4 {

	
	@JsonProperty(value = "alojamientoIdDisponible")
	private int alojamientoIdDisponible; 
	
	@JsonProperty(value = "nombreAlojamiento")
	private String nombreAlojamiento; 
		
	
	


	public int getAlojamientoIdDisponible() {
		return alojamientoIdDisponible;
	}





	public void setAlojamientoIdDisponible(int alojamientoIdDisponible) {
		this.alojamientoIdDisponible = alojamientoIdDisponible;
	}





	public String getNombreAlojamiento() {
		return nombreAlojamiento;
	}





	public void setNombreAlojamiento(String nombreAlojamiento) {
		this.nombreAlojamiento = nombreAlojamiento;
	}





	//Constructor
	public RFC4 (@JsonProperty(value = "nombreAlojamiento")String nombreAlojamiento, @JsonProperty(value = "alojamientoIdDisponible")int alojamientoIdDisponible)
	{
		this.nombreAlojamiento = nombreAlojamiento;
		this.alojamientoIdDisponible = alojamientoIdDisponible;
	}
	
	
	
	
}
