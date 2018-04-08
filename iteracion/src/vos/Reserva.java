package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa una reserva
 * 
	El formato del archivo Json es
	
	{
		"fechaInicial": "13-03-2018", 
		"fechaFinal": "13-03-2018",
		"precio": 150, 
		"id": 1,
		"cliente" : null
	}
	
 * @author jp.campos
 *
 */
public class Reserva {

	
	@JsonProperty(value="fechaInicial")
	private String fechaInicial;
	

	@JsonProperty(value="fechaFinal")
	private String fechaFinal;
	
	@JsonProperty(value="precio")
	private int precio; 
	
	@JsonProperty(value="id")
	private int id;
	
	
	@JsonProperty(value = "habitacion")
	private Habitacion habitacion; 
	
	@JsonProperty(value = "cliente")
	private Cliente cliente; 
		
	
	
	//Constructor
	public Reserva (@JsonProperty(value="id")int id, @JsonProperty(value = "fechaInicial")String fechaInicial, @JsonProperty(value = "fechaFinal")String fechaFinal, @JsonProperty(value = "precio")int precio)
	{
		this.id = id; 
		this.fechaInicial = fechaInicial; 
		this.fechaFinal = fechaFinal; 
		this.precio = precio; 
	}
	
	
	
	
	public Habitacion getHabitacion() {
		return habitacion;
	}




	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}




	public Cliente getCliente() {
		return cliente;
	}




	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}




	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}

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


	
	
	
	
	
}
