package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa la reserva historica de apartamentos
 * 
	El formato del archivo Json es
	
	{

		"aptoId": 1,
		"reservaId": 3
	}
	
 * @author s.beltran
 *
 */
public class ReservaHistoricaAptos {

	
	@JsonProperty(value="aptoId")
	private Apartamento aptoId;
	

	@JsonProperty(value="reservaId")
	private Reserva reservaId;



	public Apartamento getAptoId() {
		return aptoId;
	}



	public void setAptoId(Apartamento aptoId) {
		this.aptoId = aptoId;
	}



	public Reserva getReservaId() {
		return reservaId;
	}



	public void setReservaId(Reserva reservaId) {
		this.reservaId = reservaId;
	}



	//Constructor
	public ReservaHistoricaAptos ( )
	{
		
	}
	

	
}
