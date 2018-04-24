package vos;



import org.codehaus.jackson.annotate.JsonProperty;

public class Respuesta {
	
	@JsonProperty(value="listaRespuesta")
	private String listaRespuesta;
	
	private int contador;

	public Respuesta(@JsonProperty(value="listaRespuesta")String listaRespuesta) {
		super();
		this.listaRespuesta = listaRespuesta;
		contador = 0;
	}

	public String getListaRespuesta() {
		return listaRespuesta;
	}

	public void setListaRespuesta(String listaRespuesta) {
		this.listaRespuesta = listaRespuesta;
	}
	
	
	
	public void agregar(String sentencia)
	{
		contador++;
		listaRespuesta += contador + ". " +  sentencia + "\n";
		
	}
	
	@Override
	public String toString() {
		return listaRespuesta; 
	}
	
	

}
