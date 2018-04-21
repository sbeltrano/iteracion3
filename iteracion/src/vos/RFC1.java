package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC1
 * 
	El formato del archivo Json es
	
	{
		"fechaInicial": "13-03-2018", 
		"fechaFinal": "13-03-2018",
		"precio": 150, 
		"operadorId": 1,
		"reservaId": 3
	}
	
 * @author s.beltran
 *
 */
public class RFC1 {

	
	@JsonProperty(value="fechaInicial")
	private String fechaInicial;
	

	@JsonProperty(value="fechaFinal")
	private String fechaFinal;
	
	@JsonProperty(value="precio")
	private int precio; 
	
	@JsonProperty(value="operadorId")
	private int operadorId;
	
	
	@JsonProperty(value = "reservaId")
	private int reservaId; 
		
	
	public String getFechaInicial() {
		return fechaInicial;
	}


	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}


	public String getFechaFinal() {
		return fechaFinal;
	}


	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}


	public int getPrecio() {
		return precio;
	}


	public void setPrecio(int precio) {
		this.precio = precio;
	}


	public int getOperadorId() {
		return operadorId;
	}


	public void setOperadorId(int operadorId) {
		this.operadorId = operadorId;
	}


	public int getReservaId() {
		return reservaId;
	}


	public void setReservaId(int reservaId) {
		this.reservaId = reservaId;
	}


	//Constructor
	public RFC1 (@JsonProperty(value = "fechaInicial")String fechaInicial, @JsonProperty(value = "fechaFinal")String fechaFinal, @JsonProperty(value = "precio")int precio, @JsonProperty(value = "operadorId")int operadorId, @JsonProperty(value = "reservaId")int reservaId)
	{
		this.fechaInicial = fechaInicial; 
		this.fechaFinal = fechaFinal; 
		this.precio = precio; 
		this.operadorId = operadorId;
		this.reservaId = reservaId;
	}
	
	
	
	
}
