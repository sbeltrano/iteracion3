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
import vos.RFC1;

public class DAORFC2 {

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
	public DAORFC2() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<RFC2> get20MasPopularesApto() throws SQLException
	{
		String sql = String.format("SELECT ap.APTOID, ap.total, at.OCUPADO\n" + 
				"		FROM\n" + 
				"		(select %1$s.RESERVASHISTORICASAPTOS.APTOID,count(%1$s.RESERVASHISTORICASAPTOS.APTOID) as total\n" + 
				"		from %1$s.RESERVASHISTORICASAPTOS \n" + 
				"		group by %1$s.RESERVASHISTORICASAPTOS.APTOID) ap, %1$s.APARTAMENTO at\n" + 
				"		WHERE at.APARTAMENTOID=ap.APTOID", USUARIO);
		
		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println("Sentencia = "+rs);
		ArrayList<RFC2> rfc2 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC2 reserva = convertResultSetToReserva(rs);

			if(reserva!= null)
			rfc2.add(reserva);
		}
		return rfc2;

	}
	
	public ArrayList<RFC2> get20MasPopularesHabitacion() throws SQLException
	{
		String sql = String.format("SELECT ap.HABITACIONID, ap.total, at.OCUPADA\n" + 
				"		FROM\n" + 
				"		(select %1$s.RESERVASHISTORICASHABITACIONES.HABITACIONID,count(%1$s.RESERVASHISTORICASHABITACIONES.HABITACIONID) as total\n" + 
				"		from %1$s.RESERVASHISTORICASHABITACIONES \n" + 
				"		group by %1$s.RESERVASHISTORICASHABITACIONES.HABITACIONID) ap, %1$s.HABITACION at\n" + 
				"		WHERE at.HABITACIONID=ap.HABITACIONID", USUARIO);
		
		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println("Sentencia = "+rs);
		ArrayList<RFC2> rfc2 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC2 reserva = convertResultSetToReservaHabitacion(rs);

			if(reserva!= null)
			rfc2.add(reserva);
		}
		return rfc2;

	}
	
	public ArrayList<RFC2> get20MasPopularesAptoVivienda() throws SQLException
	{
		String sql = String.format("SELECT ap.VIVIENDAID, ap.total, at.OCUPADA\n" + 
				"		FROM\n" + 
				"		(select %1$s.RESERVASHISTORICASVIVIENDAS.VIVIENDAID,count(%1$s.RESERVASHISTORICASVIVIENDAS.VIVIENDAID) as total\n" + 
				"		from %1$s.RESERVASHISTORICASVIVIENDAS \n" + 
				"		group by %1$s.RESERVASHISTORICASVIVIENDAS.VIVIENDAID) ap, %1$s.VIVIENDA at\n" + 
				"		WHERE at.VIVIENDAID=ap.VIVIENDAID", USUARIO);
		
		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println("Sentencia = "+rs);
		ArrayList<RFC2> rfc2 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC2 reserva = convertResultSetToReservaVivienda(rs);

			if(reserva!= null)
			rfc2.add(reserva);
		}
		return rfc2;

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
	public RFC2 convertResultSetToReserva(ResultSet resultSet) throws SQLException {


		RFC2 rfc2 = null;

		int alojamientoId = resultSet.getInt("APTOID");
		int vecesRentado = resultSet.getInt("total");

		rfc2 = new RFC2(alojamientoId, vecesRentado);

		return rfc2;
	}
	
	public RFC2 convertResultSetToReservaHabitacion(ResultSet resultSet) throws SQLException {


		RFC2 rfc2 = null;

		int alojamientoId = resultSet.getInt("HABITACIONID");
		int vecesRentado = resultSet.getInt("total");

		rfc2 = new RFC2(alojamientoId, vecesRentado);

		return rfc2;
	}
	
	public RFC2 convertResultSetToReservaVivienda(ResultSet resultSet) throws SQLException {


		RFC2 rfc2 = null;

		int alojamientoId = resultSet.getInt("VIVIENDAID");
		int vecesRentado = resultSet.getInt("total");

		rfc2 = new RFC2(alojamientoId, vecesRentado);

		return rfc2;
	}
}
