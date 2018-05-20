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
import vos.RFC13;
import vos.RFC2;
import vos.RFC4;
import vos.RFC7;
import vos.RFC8;
import vos.RFC9;
import vos.RFC1;

public class DAORFC13 {

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
	public DAORFC13() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<RFC13> rfc13CostosApto() throws SQLException
	{
		String sql = String.format("SELECT *\n" + 
				"		FROM\n" + 
				"		(SELECT RES.COMUNIDADID\n" + 
				"		FROM\n" + 
				"		(SELECT RHA.RESERVAID\n" + 
				"		FROM\n" + 
				"		(SELECT APARTAMENTO.APARTAMENTOID\n" + 
				"		FROM %1$s.APARTAMENTO\n" + 
				"		WHERE APARTAMENTO.PRECIONOCHE > 40000) KOK, %1$s.RESERVASHISTORICASAPTOS RHA\n" + 
				"		WHERE RHA.APTOID=KOK.APARTAMENTOID) KIK, %1$s.RESERVA RES\n" + 
				"		WHERE RES.RESERVAID=KIK.RESERVAID) ron, %1$s.COMUNIDAD co\n" + 
				"		WHERE co.COMUNIDADID=ron.COMUNIDADID", USUARIO);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC13> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC13 reserva = convertResultSetToReservaApartamento(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}
	
	public ArrayList<RFC13> rfc13CostosVivienda() throws SQLException
	{
		String sql = String.format("SELECT *\n" + 
				"		FROM\n" + 
				"		(SELECT RES.COMUNIDADID\n" + 
				"		FROM\n" + 
				"		(SELECT RHA.RESERVAID\n" + 
				"		FROM\n" + 
				"		(SELECT VIVIENDA.VIVIENDAID\n" + 
				"		FROM %1$s.VIVIENDA\n" + 
				"		WHERE VIVIENDA.PRECIONOCHE > 40000) KOK, %1$s.RESERVASHISTORICASVIVIENDAS RHA\n" + 
				"		WHERE RHA.VIVIENDAID=KOK.VIVIENDAID) KIK, %1$s.RESERVA RES\n" + 
				"		WHERE RES.RESERVAID=KIK.RESERVAID) ron, %1$s.COMUNIDAD co\n" + 
				"		WHERE co.COMUNIDADID=ron.COMUNIDADID", USUARIO);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC13> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC13 reserva = convertResultSetToReservaApartamento(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}
	
	public ArrayList<RFC13> rfc13CostosHabitacion() throws SQLException
	{
		String sql = String.format("SELECT *\n" + 
				"		FROM\n" + 
				"		(SELECT RES.COMUNIDADID\n" + 
				"		FROM\n" + 
				"		(SELECT RHA.RESERVAID\n" + 
				"		FROM\n" + 
				"		(SELECT HABITACION.HABITACIONID\n" + 
				"		FROM %1$s.HABITACION\n" + 
				"		WHERE HABITACION.PRECIONOCHE > 40000) KOK, %1$s.RESERVASHISTORICASHABITACIONES RHA\n" + 
				"		WHERE RHA.HABITACIONID=KOK.HABITACIONID) KIK, %1$s.RESERVA RES\n" + 
				"		WHERE RES.RESERVAID=KIK.RESERVAID) ron, %1$s.COMUNIDAD co\n" + 
				"		WHERE co.COMUNIDADID=ron.COMUNIDADID", USUARIO);
				
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC13> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC13 reserva = convertResultSetToReservaApartamento(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}
	
	public ArrayList<RFC13> rfc13Mes() throws SQLException
	{
		String sql = String.format("SELECT DAT.COMUNIDADID, CID.CARNET, CID.NOMBRE\n" + 
				"		FROM\n" + 
				"		(SELECT RES.COMUNIDADID\n" + 
				"		FROM %1$s.RESERVA RES\n" + 
				"		WHERE RES.FECHAINICIAL BETWEEN TO_DATE('01-07-2018') AND TO_DATE('01-08-2018')) DAT, %1$s.COMUNIDAD CID\n" + 
				"		WHERE CID.COMUNIDADID=DAT.COMUNIDADID", USUARIO);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC13> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC13 reserva = convertResultSetToReservaApartamento(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}
	
	public ArrayList<RFC13> rfc13Suites() throws SQLException
	{
		String sql = String.format("SELECT REP.COMUNIDADID, COM.CARNET, COM.NOMBRE\n" + 
				"		FROM\n" + 
				"		(SELECT RES.COMUNIDADID\n" + 
				"		FROM\n" + 
				"		(SELECT RHA.RESERVAID\n" + 
				"		FROM\n" + 
				"		(SELECT %1$s.HABITACION.HABITACIONID\n" + 
				"		FROM %1$s.HABITACION\n" + 
				"		WHERE %1$s.HABITACION.DESCRIPCION = 'SUITE') KOK, %1$s.RESERVASHISTORICASHABITACIONES RHA\n" + 
				"		WHERE RHA.HABITACIONID=KOK.HABITACIONID) KIK, %1$s.RESERVA RES\n" + 
				"		WHERE RES.RESERVAID=KIK.RESERVAID) REP, %1$s.COMUNIDAD COM\n" + 
				"		WHERE COM.COMUNIDADID=REP.COMUNIDADID", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC13> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC13 reserva = convertResultSetToReservaApartamento(rs);

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
	public RFC13 convertResultSetToReservaApartamento(ResultSet resultSet) throws SQLException {


		RFC13 rfc4 = null;

		int clienteId = resultSet.getInt("COMUNIDADID");
		int carnet = resultSet.getInt("CARNET");
		String nombre = "NOMBRE";

		rfc4 = new RFC13(nombre, clienteId, carnet);

		return rfc4;
	}

}
