package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa un contrato de vivienda
 * @author jp.campos
 *
 */
public class ContratoVivienda extends Reserva {
	
	
	
	@JsonProperty(value="menaje")
	private boolean menaje;		
	
	
	@JsonProperty(value="numeroHabitaciones")
	private int numeroHabitaciones;

	@JsonProperty(value = "vivienda")
	private Vivienda vivienda; 
	
	@JsonProperty(value="seguro")
	private Seguro seguro;
	

	public ContratoVivienda(@JsonProperty(value = "seguro")Seguro seguro, @JsonProperty(value="id")int id,@JsonProperty(value = "fechaInicial")String fechaInicial, @JsonProperty(value = "fechaFinal")String fechaFinal,@JsonProperty(value = "precio")int precio,@JsonProperty(value="menaje")boolean menaje,@JsonProperty(value="numeroHabitaciones") int numeroHabitaciones) {
		super(id, fechaInicial, fechaFinal, precio);
		this.menaje = menaje;
		this.numeroHabitaciones = numeroHabitaciones;
		this.seguro = seguro;
		
	}


	public boolean isMenaje() {
		return menaje;
	}


	public void setMenaje(boolean menaje) {
		this.menaje = menaje;
	}


	public int getNumeroHabitaciones() {
		return numeroHabitaciones;
	}


	public void setNumeroHabitaciones(int numeroHabitaciones) {
		this.numeroHabitaciones = numeroHabitaciones;
	}


	public Vivienda getVivienda() {
		return vivienda;
	}


	public void setVivienda(Vivienda vivienda) {
		this.vivienda = vivienda;
	}


	public Seguro getSeguro() {
		return seguro;
	}


	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}		
	
	
}
