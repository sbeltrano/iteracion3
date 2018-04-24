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
import vos.RFC7;
import vos.RFC7;
import vos.RFC1;

public class DAORFC7 {

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
	public DAORFC7() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<RFC7> fechasMayorDemandaApartamento(String fechaInicial, String fechaFinal) throws SQLException
	{
		
		String sql = String.format("SELECT pop.APTOID, pop.diasPopular, ap.UBICACION\r\n" + 
				"		FROM\r\n" + 
				"		(SELECT rha.APTOID, sum(dd.dias) AS diasPopular\r\n" + 
				"		FROM\r\n" + 
				"		(SELECT di.RESERVAID, di.dias\r\n" + 
				"		FROM\r\n" + 
				"		(SELECT r.RESERVAID, TO_DATE('%3$s') - TO_DATE('%2$s') AS dias\r\n" + 
				"		from %1$s.RESERVA r\r\n" + 
				"		WHERE  '%2$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \r\n" + 
				"		'%3$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\r\n" + 
				"		UNION\r\n" + 
				"		SELECT r.RESERVAID, r.FECHAFINAL - TO_DATE('%2$s') AS dias\r\n" + 
				"		from %1$s.RESERVA r\r\n" + 
				"		WHERE  '%2$s' NOT BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \r\n" + 
				"		'%3$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\r\n" + 
				"		UNION\r\n" + 
				"		SELECT  r.RESERVAID, TO_DATE('%3$s') - r.FECHAINICIAL AS dias\r\n" + 
				"		from %1$s.RESERVA r\r\n" + 
				"		WHERE  '%2$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \r\n" + 
				"		'%3$s' NOT BETWEEN r.FECHAINICIAL AND r.FECHAFINAL) di) dd, %1$s.RESERVASHISTORICASAPTOS rha\r\n" + 
				"		WHERE dd.RESERVAID=rha.RESERVAID \r\n" + 
				"		GROUP BY rha.APTOID) pop, APARTAMENTO ap\r\n" + 
				"		WHERE ap.APARTAMENTOID=pop.APTOID\r\n" + 
				"		ORDER BY pop.diasPopular desc", USUARIO, fechaInicial, fechaFinal );
		
		

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC7> rfc7 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC7 reserva = rfc5convertApartamento(rs);

			if(reserva!= null)
			rfc7.add(reserva);
		}
		return rfc7;

	}
	
	public ArrayList<RFC7> fechasMayorDemandaHabitacion(String fechaInicial, String fechaFinal) throws SQLException
	{
		
		String sql = String.format("SELECT pop.HABITACIONID, pop.diasPopular, ap.DESCRIPCION\n" + 
				"		FROM\n" + 
				"		(SELECT rha.HABITACIONID, sum(dd.dias) AS diasPopular\n" + 
				"		FROM\n" + 
				"		(SELECT di.RESERVAID, di.dias\n" + 
				"		FROM\n" + 
				"		(SELECT r.RESERVAID, TO_DATE('%3$s') - TO_DATE('%2$s') AS dias\n" + 
				"		from %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT r.RESERVAID, r.FECHAFINAL - TO_DATE('%2$s') AS dias\n" + 
				"		from %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' NOT BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT  r.RESERVAID, TO_DATE('%3$s') - r.FECHAINICIAL AS dias\n" + 
				"		from %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' NOT BETWEEN r.FECHAINICIAL AND r.FECHAFINAL) di) dd, %1$s.RESERVASHISTORICASHABITACIONES rha\n" + 
				"		WHERE dd.RESERVAID=rha.RESERVAID \n" + 
				"		GROUP BY rha.HABITACIONID) pop, %1$s.HABITACION ap\n" + 
				"		WHERE ap.HABITACIONID=pop.HABITACIONID\n" + 
				"		ORDER BY pop.diasPopular desc", USUARIO, fechaInicial, fechaFinal );
		
		

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC7> rfc7 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC7 reserva = rfc5convertHabitacion(rs);

			if(reserva!= null)
			rfc7.add(reserva);
		}
		return rfc7;

	}
	
	public ArrayList<RFC7> fechasMayorDemandaVivienda(String fechaInicial, String fechaFinal) throws SQLException
	{
		
		String sql = String.format("SELECT pop.VIVIENDAID, pop.diasPopular, ap.UBICACION\n" + 
				"		FROM\n" + 
				"		(SELECT rha.VIVIENDAID, sum(dd.dias) AS diasPopular\n" + 
				"		FROM\n" + 
				"		(SELECT di.RESERVAID, di.dias\n" + 
				"		FROM\n" + 
				"		(SELECT r.RESERVAID, TO_DATE('%3$s') - TO_DATE('%2$s') AS dias\n" + 
				"		from  %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT r.RESERVAID, r.FECHAFINAL - TO_DATE('%2$s') AS dias\n" + 
				"		from  %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' NOT BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT  r.RESERVAID, TO_DATE('%3$s') - r.FECHAINICIAL AS dias\n" + 
				"		from  %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' NOT BETWEEN r.FECHAINICIAL AND r.FECHAFINAL) di) dd,  %1$s.RESERVASHISTORICASVIVIENDAS rha\n" + 
				"		WHERE dd.RESERVAID=rha.RESERVAID \n" + 
				"		GROUP BY rha.VIVIENDAID) pop, VIVIENDA ap\n" + 
				"		WHERE ap.VIVIENDAID=pop.VIVIENDAID\n" + 
				"		ORDER BY pop.diasPopular desc", USUARIO, fechaInicial, fechaFinal );
		
		

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC7> rfc7 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC7 reserva = rfc5convertVivienda(rs);

			if(reserva!= null)
			rfc7.add(reserva);
		}
		return rfc7;

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
	public RFC7 rfc5convertVivienda(ResultSet resultSet) throws SQLException {


		RFC7 rfc7 = null;

		int alojamientoId = resultSet.getInt("VIVIENDAID");
		int diasPopular = resultSet.getInt("DIASPOPULAR");
		String ubicacion = resultSet.getString("UBICACION");
		
		rfc7 = new RFC7(alojamientoId, diasPopular, ubicacion);

		return rfc7;
	}
	
	public RFC7 rfc5convertHabitacion(ResultSet resultSet) throws SQLException {


		RFC7 rfc7 = null;

		int alojamientoId = resultSet.getInt("HABITACIONID");
		int diasPopular = resultSet.getInt("DIASPOPULAR");
		String ubicacion = resultSet.getString("DESCRIPCION");
		
		rfc7 = new RFC7(alojamientoId, diasPopular, ubicacion);

		return rfc7;
	}
	
	public RFC7 rfc5convertApartamento(ResultSet resultSet) throws SQLException {


		RFC7 rfc7 = null;

		int alojamientoId = resultSet.getInt("APARTAMENTOID");
		int diasPopular = resultSet.getInt("DIASPOPULAR");
		String ubicacion = resultSet.getString("UBICACION");
		
		rfc7 = new RFC7(alojamientoId, diasPopular, ubicacion);

		return rfc7;
	}

}
