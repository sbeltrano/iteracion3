package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa la reserva historica de habitaciones
 * 
	El formato del archivo Json es
	
	{

		"habitacionId": 1,
		"reservaId": 3
	}
	
 * @author s.beltran
 *
 */
public class ReservaHistoricaHabitaciones {

	
	@JsonProperty(value="habitacionId")
	private Apartamento habitacionId;
	

	@JsonProperty(value="reservaId")
	private Reserva reservaId;



	public Apartamento getAptoId() {
		return habitacionId;
	}



	public void setAptoId(Apartamento aptoId) {
		this.habitacionId = aptoId;
	}



	public Reserva getReservaId() {
		return reservaId;
	}



	public void setReservaId(Reserva reservaId) {
		this.reservaId = reservaId;
	}



	//Constructor
	public ReservaHistoricaHabitaciones ( )
	{
		
	}
	

	
}
