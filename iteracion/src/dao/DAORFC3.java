package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Cliente;
import vos.ContratoApto;
import vos.ContratoHabitacion;
import vos.ContratoVivienda;
import vos.Habitacion;
import vos.PersonaOperador;
import vos.RFC1;
import vos.RFC2;
import vos.RFC3;
import vos.RFC1;

public class DAORFC3 {

	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante para indicar el usuario Oracle del estudiante
	 */
	public final static String USUARIO = "ISIS2304A791810";


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase DAORFC1 <br/>
	 */
	public DAORFC3() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<RFC3> getIndiceOcupamientoApto() throws SQLException
	{

		String sql = String.format("SELECT %1$s.APARTAMENTO.APARTAMENTOID AS aid, %1$s.APARTAMENTO.OCUPADO AS OCUPADO\n" + 
				"		FROM %1$s.APARTAMENTO", USUARIO);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println("Sentencia = "+rs);
		ArrayList<RFC3> rfc3 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC3 reserva = convertResultSetToReservaApto(rs);

			if(reserva!= null)
			rfc3.add(reserva);
		}
		return rfc3;

	}
	
	public ArrayList<RFC3> getIndiceOcupamientoHabitacin() throws SQLException
	{

		String sql = String.format("SELECT %1$s.HABITACION.HABITACIONID AS hid, %1$s.HABITACION.OCUPADA AS OCUPADO\n" + 
				"		FROM %1$s.HABITACION", USUARIO);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println("Sentencia = "+rs);
		ArrayList<RFC3> rfc3 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC3 reserva = convertResultSetToReservaHabitacion(rs);

			if(reserva!= null)
			rfc3.add(reserva);
		}
		return rfc3;

	}
	
	public ArrayList<RFC3> getIndiceOcupamientoVivienda() throws SQLException
	{

		String sql = String.format("SELECT  %1$s.VIVIENDA.VIVIENDAID AS vid,  %1$s.VIVIENDA.OCUPADA AS OCUPADO\n" + 
				"		FROM  %1$s.VIVIENDA", USUARIO);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println("Sentencia = "+rs);
		ArrayList<RFC3> rfc3 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC3 reserva = convertResultSetToReservaVivienda(rs);

			if(reserva!= null)
			rfc3.add(reserva);
		}
		return rfc3;

	}
	
	

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS AUXILIARES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo encargado de inicializar la conexion del DAO a la Base de Datos a partir del parametro <br/>
	 * <b>Postcondicion: </b> el atributo conn es inicializado <br/>
	 * @param connection la conexion generada en el TransactionManager para la comunicacion con la Base de Datos
	 */
	public void setConn(Connection connection){
		this.conn = connection;
	}


	/**
	 * Metodo que cierra todos los recursos que se encuentran en el arreglo de recursos<br/>
	 * <b>Postcondicion: </b> Todos los recurso del arreglo de recursos han sido cerrados.
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}


	/**
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla RESERVAS) en una instancia de la clase Cliente	 * @param resultSet ResultSet con la informacion de un bebedor que se obtuvo de la base de datos.
	 * @return Reserva cuyos atributos corresponden a los valores asociados a un registro particular de la tabla Reservas.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public RFC3 convertResultSetToReservaApto(ResultSet resultSet) throws SQLException {


		RFC3 rfc3 = null;

		String tipoAlojamiento = "Apartamento";
		int aljamientoId = resultSet.getInt("aid");
		int estado = resultSet.getInt("OCUPADO");
		rfc3 = new RFC3(tipoAlojamiento, aljamientoId, estado);

		return rfc3;
	}
	
	public RFC3 convertResultSetToReservaHabitacion(ResultSet resultSet) throws SQLException {


		RFC3 rfc3 = null;

		String tipoAlojamiento = "Habitacion";
		int aljamientoId = resultSet.getInt("hid");
		int estado = resultSet.getInt("OCUPADO");
		rfc3 = new RFC3(tipoAlojamiento, aljamientoId, estado);

		return rfc3;
	}
	
	public RFC3 convertResultSetToReservaVivienda(ResultSet resultSet) throws SQLException {


		RFC3 rfc3 = null;

		String tipoAlojamiento = "Vivienda";
		int aljamientoId = resultSet.getInt("vid");
		int estado = resultSet.getInt("OCUPADO");
		rfc3 = new RFC3(tipoAlojamiento, aljamientoId, estado);

		return rfc3;
	}
}
