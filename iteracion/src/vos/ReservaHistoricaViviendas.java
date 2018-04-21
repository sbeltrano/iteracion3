package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa la reserva historica de apartamentos
 * 
	El formato del archivo Json es
	
	{

		"viviendaId": 1,
		"reservaId": 3
	}
	
 * @author s.beltran
 *
 */
public class ReservaHistoricaViviendas {

	
	@JsonProperty(value="viviendaId")
	private Apartamento viviendaId;
	

	@JsonProperty(value="reservaId")
	private Reserva reservaId;



	public Apartamento getAptoId() {
		return viviendaId;
	}



	public void setAptoId(Apartamento aptoId) {
		this.viviendaId = aptoId;
	}



	public Reserva getReservaId() {
		return reservaId;
	}



	public void setReservaId(Reserva reservaId) {
		this.reservaId = reservaId;
	}



	//Constructor
	public ReservaHistoricaViviendas ( )
	{
		
	}
	

	
}
