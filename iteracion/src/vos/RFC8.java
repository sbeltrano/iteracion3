package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC8
 * 
	El formato del archivo Json es
	
	{
		 
		"tipoAlojamiento": vivienda,
		"clienteIdFrecuente": 3
	}
	
 * @author s.beltran
 *
 */
public class RFC8 {

	
	@JsonProperty(value="tipoAlojamiento")
	private String tipoAlojamiento;
	

	@JsonProperty(value="clienteIdFrecuente")
	private int clienteIdFrecuente;
	

	public String getTipoAlojamiento() {
		return tipoAlojamiento;
	}
	public void setTipoAlojamiento(String tipoAlojamiento) {
		this.tipoAlojamiento = tipoAlojamiento;
	}

	public int getClienteIdFrecuente() {
		return clienteIdFrecuente;
	}

	public void setClienteIdFrecuente(int clienteIdFrecuente) {
		this.clienteIdFrecuente = clienteIdFrecuente;
	}

	public RFC8(@JsonProperty(value = "tipoAlojamiento")String tipoAlojamiento, @JsonProperty(value = "clienteIdFrecuente") int clienteIdFrecuente) {
		super();
		this.tipoAlojamiento = tipoAlojamiento;
		this.clienteIdFrecuente = clienteIdFrecuente;
	}
	
}
