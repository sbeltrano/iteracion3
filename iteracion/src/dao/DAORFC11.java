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
import vos.RFC10;
import vos.RFC10b;
import vos.RFC11;
import vos.RFC2;
import vos.RFC4;
import vos.RFC7;
import vos.RFC8;
import vos.RFC9;
import vos.RFC1;

public class DAORFC11 {

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
	public DAORFC11() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<RFC11> rfc11Nombre(String fechaInicial, String fechaFinal) throws SQLException
	{
		String sql = String.format("SELECT DISTINCT RES.COMUNIDADID, CO.NOMBRE, CO.ROL, CO.CARNET\n" + 
				"		FROM\n" + 
				"		(SELECT r.COMUNIDADID\n" + 
				"		from %1$s.RESERVA  r\n" + 
				"		WHERE  '%2$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT r.COMUNIDADID\n" + 
				"		from %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' NOT BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT  r.COMUNIDADID\n" + 
				"		from %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' NOT BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT r.COMUNIDADID\n" + 
				"		from %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' > r.FECHAINICIAL AND \n" + 
				"		'%3$s' < R.FECHAFINAL) RES, %1$s.COMUNIDAD CO\n" + 
				"		WHERE RES.COMUNIDADID IN CO.COMUNIDADID\n" + 
				"		ORDER BY CO.NOMBRE", USUARIO, fechaInicial, fechaFinal );
		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC11> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC11 reserva = convertResultSetRFC10Nombre(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}
	
	public ArrayList<RFC11> rfc11Carnet(String fechaInicial, String fechaFinal) throws SQLException
	{
		String sql = String.format("SELECT DISTINCT RES.COMUNIDADID, CO.NOMBRE, CO.ROL, CO.CARNET\n" + 
				"		FROM\n" + 
				"		(SELECT r.COMUNIDADID\n" + 
				"		from %1$s.RESERVA  r\n" + 
				"		WHERE  '%2$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT r.COMUNIDADID\n" + 
				"		from %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' NOT BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT  r.COMUNIDADID\n" + 
				"		from %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' NOT BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT r.COMUNIDADID\n" + 
				"		from %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' > r.FECHAINICIAL AND \n" + 
				"		'%3$s' < R.FECHAFINAL) RES, %1$s.COMUNIDAD CO\n" + 
				"		WHERE RES.COMUNIDADID IN CO.COMUNIDADID\n" + 
				"		ORDER BY CO.CARNET", USUARIO, fechaInicial, fechaFinal );
		
		
		
				
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC11> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC11 reserva = convertResultSetRFC10Nombre(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}
	
	public ArrayList<RFC11> rfc11Rol(String fechaInicial, String fechaFinal) throws SQLException
	{
		String sql = String.format("SELECT DISTINCT RES.COMUNIDADID, CO.NOMBRE, CO.ROL, CO.CARNET\n" + 
				"		FROM\n" + 
				"		(SELECT r.COMUNIDADID\n" + 
				"		from %1$s.RESERVA  r\n" + 
				"		WHERE  '%2$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT r.COMUNIDADID\n" + 
				"		from %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' NOT BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT  r.COMUNIDADID\n" + 
				"		from %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' BETWEEN r.FECHAINICIAL AND r.FECHAFINAL AND \n" + 
				"		'%3$s' NOT BETWEEN r.FECHAINICIAL AND r.FECHAFINAL\n" + 
				"		UNION\n" + 
				"		SELECT r.COMUNIDADID\n" + 
				"		from %1$s.RESERVA r\n" + 
				"		WHERE  '%2$s' > r.FECHAINICIAL AND \n" + 
				"		'%3$s' < R.FECHAFINAL) RES, %1$s.COMUNIDAD CO\n" + 
				"		WHERE RES.COMUNIDADID IN CO.COMUNIDADID\n" + 
				"		ORDER BY CO.ROL", USUARIO, fechaInicial, fechaFinal );
		
		
		
				
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC11> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC11 reserva = convertResultSetRFC10Nombre(rs);

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
	public RFC11 convertResultSetRFC10Nombre(ResultSet resultSet) throws SQLException {


		RFC11 rfc4 = null;

		int id = resultSet.getInt("COMUNIDADID");
		String nombre = resultSet.getString("NOMBRE");
		int carnet = resultSet.getInt("CARNET");
		String rol = resultSet.getString("ROL");
		rfc4 = new RFC11(nombre,id,rol,carnet);

		return rfc4;
	}

}
