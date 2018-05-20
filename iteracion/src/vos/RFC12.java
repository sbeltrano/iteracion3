	package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el RFC12
 * 
	El formato del archivo Json es
	
	{
		 
		"operadorId": 55,
		"nombre": jorge,
		"carnet":201632541
		
	}
	
 * @author s.beltran
 *
 */
public class RFC12 {

	
	@JsonProperty(value="week")
	private String week;
	
	@JsonProperty(value="startWeek")
	private String startWeek;

	@JsonProperty(value="endWeek")
	private String endWeek;
	
	@JsonProperty(value="aptoId")
	private int aptoId;
	
	@JsonProperty(value="viviendaId")
	private int viviendaId;
	
	@JsonProperty(value="habitacionId")
	private int habitacionId;
	
	

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



	public int getAptoId() {
		return aptoId;
	}



	public void setAptoId(int aptoId) {
		this.aptoId = aptoId;
	}



	public int getViviendaId() {
		return viviendaId;
	}



	public void setViviendaId(int viviendaId) {
		this.viviendaId = viviendaId;
	}



	public int getHabitacionId() {
		return habitacionId;
	}



	public void setHabitacionId(int habitacionId) {
		this.habitacionId = habitacionId;
	}



	public RFC12(@JsonProperty(value = "week")String week, @JsonProperty(value = "startWeek")String startWeek, @JsonProperty(value = "endWeek")String endWeek, @JsonProperty(value = "habitacionId")int habitacionId, @JsonProperty(value = "viviendaId")int viviendaId, @JsonProperty(value = "aptoId")int aptoId) {
		this.week = week;
		this.startWeek = startWeek;
		this.endWeek = endWeek;
		this.habitacionId = habitacionId;
		this.viviendaId = viviendaId;
		this.aptoId = aptoId;
	}
	
}
