	package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC12
 * 
	El formato del archivo Json es
	
	{
		 
		"tipoAlojamiento": vivienda,
		"alojamientoId": 3
		"descripcion": nombre
		
	}
	
 * @author s.beltran
 *
 */
public class RFC12b {

	
	@JsonProperty(value="week")
	private String week;
	
	@JsonProperty(value="startWeek")
	private String startWeek;

	@JsonProperty(value="endWeek")
	private String endWeek;
	
	@JsonProperty(value="operadorId")
	private int operadorId;
	
	

	public String getWeek() {
		return week;
	}



	public void setWeek(String week) {
		this.week = week;
	}



	public String getStartWeek() {
		return startWeek;
	}



	public void setStartWeek(String startWeek) {
		this.startWeek = startWeek;
	}



	public String getEndWeek() {
		return endWeek;
	}



	public void setEndWeek(String endWeek) {
		this.endWeek = endWeek;
	}



	public int getOperadorId() {
		return operadorId;
	}



	public void setOperadorId(int operadorId) {
		this.operadorId = operadorId;
	}



	public RFC12b(@JsonProperty(value = "week")String week, @JsonProperty(value = "startWeek")String startWeek, @JsonProperty(value = "endWeek")String endWeek, @JsonProperty(value = "operadorId")int operadorId) {
		this.week = week;
		this.startWeek = startWeek;
		this.endWeek = endWeek;
		this.operadorId = operadorId;
	}
	
}
