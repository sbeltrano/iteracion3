package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC5y6
 * 
	El formato del archivo Json es
	
	{
		 
		"usuarioId": 1,
		"tipoUsuario": OPERADOR,
		"mesesContratados": 3, 
		"caracteristicasAlojamiento": "", 
		"dineroPagado": "5222",
	}
	
 * @author s.beltran
 *
 */
public class RFC5y6 {

	
	@JsonProperty(value="usuarioId")
	private int usuarioId;
	
	@JsonProperty(value="tipoUsuario")
	private String tipoUsuario;

	@JsonProperty(value="mesesContratados")
	private int mesesContratados;
	
	@JsonProperty(value="caracteristicasAlojamiento")
	private String caracteristicasAlojamiento; 
	
	@JsonProperty(value="dineroPagado")
	private int dineroPagado;

	
	


	public String getTipoUsuario() {
		return tipoUsuario;
	}





	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}





	public int getUsuarioId() {
		return usuarioId;
	}





	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}





	public int getMesesContratados() {
		return mesesContratados;
	}





	public void setMesesContratados(int mesesContratados) {
		this.mesesContratados = mesesContratados;
	}





	public String getCaracteristicasAlojamiento() {
		return caracteristicasAlojamiento;
	}





	public void setCaracteristicasAlojamiento(String caracteristicasAlojamiento) {
		this.caracteristicasAlojamiento = caracteristicasAlojamiento;
	}





	public int getDineroPagado() {
		return dineroPagado;
	}





	public void setDineroPagado(int dineroPagado) {
		this.dineroPagado = dineroPagado;
	}





	public RFC5y6(@JsonProperty(value = "usuarioId")int usuarioId, @JsonProperty(value = "tipoUsuario")String tipoUsuario, @JsonProperty(value = "mesesContratados") int mesesContratados,@JsonProperty(value = "caracteristicasAlojamiento") String caracteristicasAlojamiento,@JsonProperty(value = "dineroPagado") int dineroPagado) {
		this.usuarioId = usuarioId;
		this.tipoUsuario = tipoUsuario;
		this.mesesContratados = mesesContratados;
		this.caracteristicasAlojamiento = caracteristicasAlojamiento;
		this.dineroPagado = dineroPagado;
	}
	
	
	
}
