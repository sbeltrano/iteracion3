package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC10
 * 
	El formato del archivo Json es
	
	{	 
		"usuarioId": 3,
		"nombre": 3
	}
	
 * @author s.beltran
 *
 */
public class RFC10b {

	
	@JsonProperty(value="fechaInicial")
	private String fechaInicial;
	

	@JsonProperty(value="usuarioId")
	private int usuarioId;
	
	@JsonProperty(value="fechaFinal")
	private String fechaFinal;
	
	@JsonProperty(value="tipo")
	private String tipo;

	

	public RFC10b(@JsonProperty(value = "fechaInicial")String fechaInicial, @JsonProperty(value = "usuarioId") int usuarioId, @JsonProperty(value = "fechaFinal")String fechaFinal, @JsonProperty(value = "tipo") String tipo) {
		this.tipo = tipo;
		this.usuarioId = usuarioId;
		this.fechaFinal = fechaFinal;
		this.fechaInicial = fechaInicial;
	}
	
}
