package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa un operador
 * @author jp.campos
 
 El formato JSON de esta clase es
 
  	{
        "id": 101,
        "nombre": "Hernando",
        "rol": "Empleado",
        "carnet": 201631726
   
	 }
 
 
 
 */

public class PersonaOperador extends Comunidad{

	
	
		@JsonProperty(value = "habitaciones")
		private List<Habitacion> habitaciones; 
		
		@JsonProperty(value = "apartamento")
		private Apartamento apartamento;
		
		@JsonProperty(value = "vivienda")
		private Vivienda vivienda; 
		
		@JsonProperty(value  = "contratoApto")
		private ContratoApto contratoApto;
	
		@JsonProperty(value = "contratoHabitacion")
		private ContratoHabitacion contratoHabitacion;
		
		@JsonProperty(value = "hotel")
		private Hotel hotel; 
	
		
		
		public PersonaOperador(@JsonProperty(value="id")int id, @JsonProperty(value="nombre")String nombre,
				@JsonProperty(value="rol")String rol,@JsonProperty(value="carnet") int carnet) 
		{
		
		super(id, nombre, rol, carnet);
		setId(id);
		setNombre(nombre);
		setRol(rol);
		setCarnet(carnet);
		hotel = null; 
		contratoApto = null; 
		contratoHabitacion = null; 
		vivienda = null; 
		apartamento = null; 
		habitaciones = new ArrayList<>(); 
		}

		
		
		
		
		public Hotel getHotel() {
			return hotel;
		}


		public void setHotel(Hotel hotel) {
			this.hotel = hotel;
		}





		public List<Habitacion> getHabitaciones() {
			return habitaciones;
		}

		public void setHabitaciones(List<Habitacion> habitaciones) {
			this.habitaciones = habitaciones;
		}

		public Apartamento getApartamento() {
			return apartamento;
		}

		public void setApartamento(Apartamento apartamento) {
			this.apartamento = apartamento;
		}

		public Vivienda getVivienda() {
			return vivienda;
		}

		public void setVivienda(Vivienda vivienda) {
			this.vivienda = vivienda;
		}

		public ContratoApto getContratoApto() {
			return contratoApto;
		}

		public void setContratoApto(ContratoApto contratoApto) {
			this.contratoApto = contratoApto;
		}

		public ContratoHabitacion getContratoHabitacion() {
			return contratoHabitacion;
		}

		public void setContratoHabitacion(ContratoHabitacion contratoHabitacion) {
			this.contratoHabitacion = contratoHabitacion;
		}
	
	
		
	
		
	
}
