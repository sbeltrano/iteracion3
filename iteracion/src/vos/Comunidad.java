package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa un integrante de la comunidad
 * @author jp.campos
 *
 */
public class Comunidad {


	
	public final static String ESTUDIANTE =  "Estudiante";
	public final static String PROFESOR = "Profesor";
	public final static String FAMILIAR = "Familiar"; 
	public final static String HOTEL =  "Hotel"; 
	
	/**
	 * Id
	 */
	@JsonProperty(value="id")
	private int id; 

	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value="nombre")
	private String nombre; 

	/**
	 * Relacion con la universidad de los Andes.
	 */
	@JsonProperty(value="rol")
	private String rol;
	
	/**
	 * Carnet del uniandino relacioado. Puede ser nulo si se trata de un vecino
	 */
	@JsonProperty(value="carnet")
	private int carnet;

	
	
	
	public Comunidad(@JsonProperty(value="id")int id, @JsonProperty(value="nombre")String nombre, @JsonProperty(value="rol")String rol,@JsonProperty(value="carnet") int carnet) {
		
		this.id = id;
		this.nombre = nombre;
		this.rol = rol;
		this.carnet = carnet;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public int getCarnet() {
		return carnet;
	}

	public void setCarnet(Integer carnet) {
		this.carnet = carnet;
	}
	

	
	
}
