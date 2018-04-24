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
import vos.RFC1;

public class DAORFC4 {

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
	public DAORFC4() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<RFC4> getAlojamientosPorFechaApartamento(String fechaInicial, String fechaFinal) throws SQLException
	{
		String sql = String.format("SELECT tr.id\n" + 
				"		FROM\n" + 
				"		(SELECT %1$s.APARTAMENTO.APARTAMENTOID AS hid, %1$s.APARTAMENTO.OCUPADO AS OCUPADO\n" + 
				"		FROM %1$s.APARTAMENTO\n" + 
				"		) pr,\n" + 
				"		(SELECT pr.id, tr.RESERVAID\n" + 
				"		FROM\n" + 
				"		(SELECT  %1$s.RESERVA.RESERVAID AS RESERVAID\n" + 
				"		FROM    %1$s.RESERVA \n" + 
				"		WHERE   %1$s.RESERVA.FECHAINICIAL >= '%2$s' AND\n" + 
				"				%1$s.RESERVA.FECHAFINAL   <= '%3$s') tr, \n" + 
				"		        (\n" + 
				"		        SELECT ho.COMUNIDADID, rhh.RESERVAID, id\n" + 
				"		        FROM %1$s.RESERVASHISTORICASAPTOS rhh,\n" + 
				"		        (SELECT h.APARTAMENTOID as id, h.COMUNIDADID\n" + 
				"		        FROM %1$s.APARTAMENTO h) ho\n" + 
				"		        WHERE ho.id=rhh.APTOID)\n" + 
				"		         pr\n" + 
				"		        WHERE pr.RESERVAID=tr.RESERVAID) tr\n" + 
				"		WHERE pr.OCUPADO=0 AND tr.id=pr.hid\n" + 
				"		ORDER BY tr.id desc\n" + 
				"", USUARIO,fechaInicial, fechaFinal );

				
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC4> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC4 reserva = convertResultSetToReservaApartamento(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}
	
	public ArrayList<RFC4> getAlojamientosPorFechaVivienda(String fechaInicial, String fechaFinal) throws SQLException
	{
		String sql = String.format("SELECT tr.id\n" + 
				"		FROM\n" + 
				"		(SELECT %1$s.VIVIENDA.VIVIENDAID AS hid, %1$s.VIVIENDA.OCUPADA AS OCUPADO\n" + 
				"		FROM %1$s.VIVIENDA\n" + 
				"		) pr,\n" + 
				"		(SELECT pr.id, tr.RESERVAID\n" + 
				"		FROM\n" + 
				"		(SELECT  %1$s.RESERVA.RESERVAID AS RESERVAID\n" + 
				"		FROM    %1$s.RESERVA \n" + 
				"		WHERE   %1$s.RESERVA.FECHAINICIAL >= '%2$s' AND\n" + 
				"				%1$s.RESERVA.FECHAFINAL   <= '%3$s') tr, \n" + 
				"		        (\n" + 
				"		        SELECT ho.COMUNIDADID, rhh.RESERVAID, id\n" + 
				"		        FROM %1$s.RESERVASHISTORICASVIVIENDAS rhh,\n" + 
				"		        (SELECT h.VIVIENDAID as id, h.COMUNIDADID\n" + 
				"		        FROM %1$s.VIVIENDA h) ho\n" + 
				"		        WHERE ho.id=rhh.VIVIENDAID)\n" + 
				"		         pr\n" + 
				"		        WHERE pr.RESERVAID=tr.RESERVAID) tr\n" + 
				"		WHERE pr.OCUPADO=0 AND tr.id=pr.hid\n" + 
				"		ORDER BY tr.id desc", USUARIO,fechaInicial, fechaFinal );

		
				
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC4> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC4 reserva = convertResultSetToReservaVivienda(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}
	
	public ArrayList<RFC4> getAlojamientosPorFechaHabitacion(String fechaInicial, String fechaFinal) throws SQLException
	{
		String sql = String.format("SELECT tr.id\n" + 
				"		FROM\n" + 
				"		(SELECT %1$s.HABITACION.HABITACIONID AS hid, %1$s.HABITACION.OCUPADA AS OCUPADO\n" + 
				"		FROM %1$s.HABITACION\n" + 
				"		) pr,\n" + 
				"		(SELECT pr.id, tr.RESERVAID\n" + 
				"		FROM\n" + 
				"		(SELECT  %1$s.RESERVA.RESERVAID AS RESERVAID\n" + 
				"		FROM    %1$s.RESERVA \n" + 
				"		WHERE   %1$s.RESERVA.FECHAINICIAL >= '%2$s' AND\n" + 
				"				%1$s.RESERVA.FECHAFINAL   <= '%3$s') tr, \n" + 
				"		        (\n" + 
				"		        SELECT ho.COMUNIDADID, rhh.RESERVAID, id\n" + 
				"		        FROM %1$s.RESERVASHISTORICASHABITACIONES rhh,\n" + 
				"		        (SELECT h.HABITACIONID as id, h.COMUNIDADID\n" + 
				"		        FROM %1$s.HABITACION h) ho\n" + 
				"		        WHERE ho.id=rhh.HABITACIONID)\n" + 
				"		         pr\n" + 
				"		        WHERE pr.RESERVAID=tr.RESERVAID) tr\n" + 
				"		WHERE pr.OCUPADO=0 AND tr.id=pr.hid\n" + 
				"		ORDER BY tr.id desc", USUARIO,fechaInicial, fechaFinal );

		
				
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC4> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC4 reserva = convertResultSetToReservaHabitacion(rs);

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
	public RFC4 convertResultSetToReservaApartamento(ResultSet resultSet) throws SQLException {


		RFC4 rfc4 = null;

		int alojamientoIdDisponible = resultSet.getInt("ID");
		String nombreAlojamiento = "Apartamento";

		rfc4 = new RFC4(nombreAlojamiento, alojamientoIdDisponible);

		return rfc4;
	}
	
	public RFC4 convertResultSetToReservaVivienda(ResultSet resultSet) throws SQLException {


		RFC4 rfc4 = null;

		int alojamientoIdDisponible = resultSet.getInt("ID");
		String nombreAlojamiento = "Vivienda";

		rfc4 = new RFC4(nombreAlojamiento, alojamientoIdDisponible);

		return rfc4;
	}
	
	public RFC4 convertResultSetToReservaHabitacion(ResultSet resultSet) throws SQLException {


		RFC4 rfc4 = null;

		int alojamientoIdDisponible = resultSet.getInt("ID");
		String nombreAlojamiento = "Habitacion";

		rfc4 = new RFC4(nombreAlojamiento, alojamientoIdDisponible);

		return rfc4;
	}
}
