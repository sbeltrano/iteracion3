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
import vos.RFC4;
import vos.RFC7;
import vos.RFC8;
import vos.RFC9;
import vos.RFC1;

public class DAORFC9 {

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
	public DAORFC9() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<RFC9> rfc9Apartamentos() throws SQLException
	{
		String sql = String.format("SELECT apa.APARTAMENTOID \n" + 
				"		FROM %1$s.APARTAMENTO apa\n" + 
				"		WHERE apa.APARTAMENTOID NOT IN (SELECT APTOID FROM %1$s.RESERVASHISTORICASAPTOS)\n" + 
				"		UNION\n" + 
				"		SELECT rd.APTOID\n" + 
				"		FROM\n" + 
				"		(SELECT ra.APTOID, SUM(re.FECHAFINAL - re.FECHAINICIAL) AS tDias\n" + 
				"		FROM %1$s.RESERVASHISTORICASAPTOS ra, %1$s.RESERVA re\n" + 
				"		WHERE ra.RESERVAID = re.RESERVAID\n" + 
				"		GROUP BY ra.APTOID) rd\n" + 
				"		WHERE rd.tdias < 31", USUARIO);
		
		
		
				
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC9> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC9 reserva = convertResultSetToReservaApartamento(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}
	
	public ArrayList<RFC9> rfc8Habitaciones() throws SQLException
	{
		String sql = String.format("SELECT apa.HABITACIONID \n" + 
				"		FROM %1$s.HABITACION apa\n" + 
				"		WHERE apa.HABITACIONID NOT IN (SELECT HABITACIONID FROM %1$s.RESERVASHISTORICASHABITACIONES)\n" + 
				"		UNION\n" + 
				"		SELECT rd.HABITACIONID\n" + 
				"		FROM\n" + 
				"		(SELECT ra.HABITACIONID, SUM(re.FECHAFINAL - re.FECHAINICIAL) AS tDias\n" + 
				"		FROM %1$s.RESERVASHISTORICASHABITACIONES ra, %1$s.RESERVA re\n" + 
				"		WHERE ra.RESERVAID = re.RESERVAID\n" + 
				"		GROUP BY ra.HABITACIONID) rd\n" + 
				"		WHERE rd.tdias < 31", USUARIO);
		
		

				
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC9> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC9 reserva = convertResultSetToReservaHabitacion(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}
	
	public ArrayList<RFC9> rfc8Viviendas() throws SQLException
	{
		String sql = String.format("SELECT apa.VIVIENDAID \n" + 
				"		FROM %1$s.VIVIENDA apa\n" + 
				"		WHERE apa.VIVIENDAID NOT IN (SELECT VIVIENDAID FROM %1$s.RESERVASHISTORICASVIVIENDAS)\n" + 
				"		UNION\n" + 
				"		SELECT rd.VIVIENDAID\n" + 
				"		FROM\n" + 
				"		(SELECT ra.VIVIENDAID, SUM(re.FECHAFINAL - re.FECHAINICIAL) AS tDias\n" + 
				"		FROM %1$s.RESERVASHISTORICASVIVIENDAS ra, %1$s.RESERVA re\n" + 
				"		WHERE ra.RESERVAID = re.RESERVAID\n" + 
				"		GROUP BY ra.VIVIENDAID) rd\n" + 
				"		WHERE rd.tdias < 31", USUARIO);

		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC9> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC9 reserva = convertResultSetToReservaVivienda(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

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
	public RFC9 convertResultSetToReservaApartamento(ResultSet resultSet) throws SQLException {


		RFC9 rfc4 = null;

		int id = resultSet.getInt("APARTAMENTOID");
		String nombreAlojamiento = "Apartamento";

		rfc4 = new RFC9(nombreAlojamiento,id);

		return rfc4;
	}
	
	public RFC9 convertResultSetToReservaVivienda(ResultSet resultSet) throws SQLException {


		RFC9 rfc4 = null;

		int id = resultSet.getInt("VIVIENDAID");
		String nombreAlojamiento = "Vivienda";

		rfc4 = new RFC9(nombreAlojamiento,id);

		return rfc4;
	}
	
	public RFC9 convertResultSetToReservaHabitacion(ResultSet resultSet) throws SQLException {


		RFC9 rfc4 = null;

		int id = resultSet.getInt("HABITACIONID");
		String nombreAlojamiento = "Habitacion";

		rfc4 = new RFC9(nombreAlojamiento,id);

		return rfc4;
	}

}
