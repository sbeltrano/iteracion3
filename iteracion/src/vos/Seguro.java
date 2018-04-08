package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Seguro {
	
	
	@JsonProperty(value="costo")
	private int costo;
	
	@JsonProperty(value="vigencia")
	private boolean vigencia;

	@JsonProperty(value="id")
	private int id;
	
	public Seguro(@JsonProperty(value="id")int id,@JsonProperty(value="costo")int costo, @JsonProperty(value="vigencia")boolean vigencia) {
		
		this.costo = costo;
		this.vigencia = vigencia;
	}

	
	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public boolean isVigencia() {
		return vigencia;
	}

	public void setVigencia(boolean vigencia) {
		this.vigencia = vigencia;
	}
	
	
	

}
