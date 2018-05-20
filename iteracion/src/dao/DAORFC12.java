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
import vos.RFC12;
import vos.RFC12b;
import vos.RFC2;
import vos.RFC4;
import vos.RFC7;
import vos.RFC8;
import vos.RFC9;
import vos.RFC1;

public class DAORFC12 {

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
	public DAORFC12() {
		recursos = new ArrayList<Object>();
	}
	
	public ArrayList<RFC12> rfc12Max(int year) throws SQLException
	{
		String sql = String.format("SELECT ROWNUM AS WEEK, WK.START_WEEK, WK.END_WEEK, \n" + 
				"		( \n" + 
				"\n" + 
				"		SELECT MAX(APTOS.RESERVASAPTOSEM)\n" + 
				"		FROM\n" + 
				"		(SELECT RHA.APTOID, COUNT(RHA.APTOID) AS RESERVASAPTOSEM\n" + 
				"		FROM %1$s.RESERVASHISTORICASAPTOS RHA, %1$s.RESERVA\n" + 
				"		WHERE RESERVA.RESERVAID=RHA.RESERVAID AND\n" + 
				"		      (WK.START_WEEK BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 1 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 2 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 3 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 4 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 5 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 6 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL)\n" + 
				"		      GROUP BY RHA.APTOID) APTOS\n" + 
				"		) AS APTOID,\n" + 
				"		( \n" + 
				"\n" + 
				"		SELECT MAX(VVDAS.RESERVASVVDASEM)\n" + 
				"		FROM\n" + 
				"		(SELECT RHA.VIVIENDAID, COUNT(RHA.VIVIENDAID) AS RESERVASVVDASEM\n" + 
				"		FROM %1$s.RESERVASHISTORICASVIVIENDAS RHA, %1$s.RESERVA\n" + 
				"		WHERE RESERVA.RESERVAID=RHA.RESERVAID AND\n" + 
				"		      (WK.START_WEEK BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 1 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 2 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 3 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 4 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 5 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 6 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL)\n" + 
				"		      GROUP BY RHA.VIVIENDAID) VVDAS\n" + 
				"		) AS VIVIENDAID,\n" + 
				"		( \n" + 
				"\n" + 
				"		SELECT MAX(HABS.RESERVASHABSEM)\n" + 
				"		FROM\n" + 
				"		(SELECT RHA.HABITACIONID, COUNT(RHA.HABITACIONID) AS RESERVASHABSEM\n" + 
				"		FROM %1$s.RESERVASHISTORICASHABITACIONES RHA, %1$s.RESERVA\n" + 
				"		WHERE RESERVA.RESERVAID=RHA.RESERVAID AND\n" + 
				"		      (WK.START_WEEK BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 1 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 2 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 3 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 4 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 5 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 6 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL)\n" + 
				"		      GROUP BY RHA.HABITACIONID) HABS\n" + 
				"		) AS HABID\n" + 
				"\n" + 
				"\n" + 
				"		FROM\n" + 
				"		(SELECT distinct SEM.START_WEEK, SEM.END_WEEK\n" + 
				"		FROM\n" + 
				"		(SELECT \n" + 
				"		  MONTHS.MONTH_START, \n" + 
				"		  WN.WEEK_NUMBER, \n" + 
				"		  MONTHS.START_SECOND_WEEK + (WN.WEEK_NUMBER-2)*7 START_WEEK, \n" + 
				"		  MONTHS.START_SECOND_WEEK + (WN.WEEK_NUMBER-1)*7 - 1 END_WEEK \n" + 
				"		FROM \n" + 
				"		  (SELECT \n" + 
				"		    ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),(LEVEL-1)) MONTH_START, \n" + 
				"		    ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),LEVEL)-1 MONTH_END, \n" + 
				"		    NEXT_DAY(ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),(LEVEL-1))-1,'SABADO') END_FIRST_WEEK,\n" + 
				"		    NEXT_DAY(ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),(LEVEL-1)),'DOMINGO') START_SECOND_WEEK\n" + 
				"		   FROM \n" + 
				"		    DUAL \n" + 
				"		  CONNECT BY \n" + 
				"		    LEVEL<=12) MONTHS, \n" + 
				"		  (SELECT \n" + 
				"		    LEVEL WEEK_NUMBER \n" + 
				"		  FROM \n" + 
				"		    DUAL \n" + 
				"		  CONNECT BY \n" + 
				"		    LEVEL<=6) WN \n" + 
				"		WHERE \n" + 
				"		  (MONTHS.START_SECOND_WEEK + (WN.WEEK_NUMBER-2)*7)<=MONTHS.MONTH_END) SEM\n" + 
				"		  order by SEM.START_WEEK) WK", USUARIO, year);

		
		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC12> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC12 reserva = convertResultSetToReservaVivienda(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}
	
	public ArrayList<RFC12> rfc12Min(int year) throws SQLException
	{
		String sql = String.format("SELECT ROWNUM AS WEEK, WK.START_WEEK, WK.END_WEEK, \n" + 
				"		( \n" + 
				"\n" + 
				"		SELECT MIN(APTOS.RESERVASAPTOSEM)\n" + 
				"		FROM\n" + 
				"		(SELECT RHA.APTOID, COUNT(RHA.APTOID) AS RESERVASAPTOSEM\n" + 
				"		FROM %1$s.RESERVASHISTORICASAPTOS RHA, %1$s.RESERVA\n" + 
				"		WHERE RESERVA.RESERVAID=RHA.RESERVAID AND\n" + 
				"		      (WK.START_WEEK BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 1 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 2 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 3 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 4 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 5 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 6 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL)\n" + 
				"		      GROUP BY RHA.APTOID) APTOS\n" + 
				"		) AS APTOID,\n" + 
				"		( \n" + 
				"\n" + 
				"		SELECT MIN(VVDAS.RESERVASVVDASEM)\n" + 
				"		FROM\n" + 
				"		(SELECT RHA.VIVIENDAID, COUNT(RHA.VIVIENDAID) AS RESERVASVVDASEM\n" + 
				"		FROM %1$s.RESERVASHISTORICASVIVIENDAS RHA, %1$s.RESERVA\n" + 
				"		WHERE RESERVA.RESERVAID=RHA.RESERVAID AND\n" + 
				"		      (WK.START_WEEK BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 1 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 2 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 3 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 4 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 5 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 6 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL)\n" + 
				"		      GROUP BY RHA.VIVIENDAID) VVDAS\n" + 
				"		) AS VIVIENDAID,\n" + 
				"		( \n" + 
				"\n" + 
				"		SELECT MIN(HABS.RESERVASHABSEM)\n" + 
				"		FROM\n" + 
				"		(SELECT RHA.HABITACIONID, COUNT(RHA.HABITACIONID) AS RESERVASHABSEM\n" + 
				"		FROM %1$s.RESERVASHISTORICASHABITACIONES RHA, %1$s.RESERVA\n" + 
				"		WHERE RESERVA.RESERVAID=RHA.RESERVAID AND\n" + 
				"		      (WK.START_WEEK BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 1 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 2 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 3 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 4 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 5 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 6 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL)\n" + 
				"		      GROUP BY RHA.HABITACIONID) HABS\n" + 
				"		) AS HABID\n" + 
				"\n" + 
				"\n" + 
				"		FROM\n" + 
				"		(SELECT distinct SEM.START_WEEK, SEM.END_WEEK\n" + 
				"		FROM\n" + 
				"		(SELECT \n" + 
				"		  MONTHS.MONTH_START, \n" + 
				"		  WN.WEEK_NUMBER, \n" + 
				"		  MONTHS.START_SECOND_WEEK + (WN.WEEK_NUMBER-2)*7 START_WEEK, \n" + 
				"		  MONTHS.START_SECOND_WEEK + (WN.WEEK_NUMBER-1)*7 - 1 END_WEEK \n" + 
				"		FROM \n" + 
				"		  (SELECT \n" + 
				"		    ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),(LEVEL-1)) MONTH_START, \n" + 
				"		    ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),LEVEL)-1 MONTH_END, \n" + 
				"		    NEXT_DAY(ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),(LEVEL-1))-1,'SABADO') END_FIRST_WEEK,\n" + 
				"		    NEXT_DAY(ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),(LEVEL-1)),'DOMINGO') START_SECOND_WEEK\n" + 
				"		   FROM \n" + 
				"		    DUAL \n" + 
				"		  CONNECT BY \n" + 
				"		    LEVEL<=12) MONTHS, \n" + 
				"		  (SELECT \n" + 
				"		    LEVEL WEEK_NUMBER \n" + 
				"		  FROM \n" + 
				"		    DUAL \n" + 
				"		  CONNECT BY \n" + 
				"		    LEVEL<=6) WN \n" + 
				"		WHERE \n" + 
				"		  (MONTHS.START_SECOND_WEEK + (WN.WEEK_NUMBER-2)*7)<=MONTHS.MONTH_END) SEM\n" + 
				"		  order by SEM.START_WEEK) WK", USUARIO, year);

		
		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC12> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC12 reserva = convertResultSetToReservaVivienda(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}

	public ArrayList<RFC12b> rfc12MinOperador(int year) throws SQLException
	{
		String sql = String.format("SELECT ROWNUM AS WEEK, WK.START_WEEK, WK.END_WEEK, \n" + 
				"		( \n" + 
				"\n" + 
				"		SELECT APT.COMUNIDADID\n" + 
				"		FROM\n" + 
				"		(SELECT MIN(APTOS.RESERVASAPTOSEM) AS APTOMAXI\n" + 
				"		FROM\n" + 
				"		(SELECT RHA.APTOID, COUNT(RHA.APTOID) AS RESERVASAPTOSEM\n" + 
				"		FROM %1$s.RESERVASHISTORICASAPTOS RHA, %1$s.RESERVA\n" + 
				"		WHERE RESERVA.RESERVAID=RHA.RESERVAID AND\n" + 
				"		      (WK.START_WEEK BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 1 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 2 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 3 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 4 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 5 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 6 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL)\n" + 
				"		      GROUP BY RHA.APTOID) APTOS) APTOMAX, APARTAMENTO APT\n" + 
				"		      WHERE APT.APARTAMENTOID=APTOMAX.APTOMAXI     \n" + 
				"		) AS COMUNIDADID\n" + 
				"		FROM\n" + 
				"		(SELECT distinct SEM.START_WEEK, SEM.END_WEEK\n" + 
				"		FROM\n" + 
				"		(SELECT \n" + 
				"		  MONTHS.MONTH_START, \n" + 
				"		  WN.WEEK_NUMBER, \n" + 
				"		  MONTHS.START_SECOND_WEEK + (WN.WEEK_NUMBER-2)*7 START_WEEK, \n" + 
				"		  MONTHS.START_SECOND_WEEK + (WN.WEEK_NUMBER-1)*7 - 1 END_WEEK \n" + 
				"		FROM \n" + 
				"		  (SELECT \n" + 
				"		    ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),(LEVEL-1)) MONTH_START, \n" + 
				"		    ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),LEVEL)-1 MONTH_END, \n" + 
				"		    NEXT_DAY(ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),(LEVEL-1))-1,'SABADO') END_FIRST_WEEK,\n" + 
				"		    NEXT_DAY(ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),(LEVEL-1)),'DOMINGO') START_SECOND_WEEK\n" + 
				"		   FROM \n" + 
				"		    DUAL \n" + 
				"		  CONNECT BY \n" + 
				"		    LEVEL<=12) MONTHS, \n" + 
				"		  (SELECT \n" + 
				"		    LEVEL WEEK_NUMBER \n" + 
				"		  FROM \n" + 
				"		    DUAL \n" + 
				"		  CONNECT BY \n" + 
				"		    LEVEL<=6) WN \n" + 
				"		WHERE \n" + 
				"		  (MONTHS.START_SECOND_WEEK + (WN.WEEK_NUMBER-2)*7)<=MONTHS.MONTH_END) SEM\n" + 
				"		  order by SEM.START_WEEK) WK", USUARIO, year);
		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC12b> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC12b reserva = RFC12bResultSet(rs);

			if(reserva!= null)
			rfc4.add(reserva);
		}
		return rfc4;

	}
	
	public ArrayList<RFC12b> rfc12MaxOperador(int year) throws SQLException
	{
		String sql = String.format("SELECT ROWNUM AS WEEK, WK.START_WEEK, WK.END_WEEK, \n" + 
				"		( \n" + 
				"\n" + 
				"		SELECT APT.COMUNIDADID\n" + 
				"		FROM\n" + 
				"		(SELECT MAX(APTOS.RESERVASAPTOSEM) AS APTOMAXI\n" + 
				"		FROM\n" + 
				"		(SELECT RHA.APTOID, COUNT(RHA.APTOID) AS RESERVASAPTOSEM\n" + 
				"		FROM %1$s.RESERVASHISTORICASAPTOS RHA, %1$s.RESERVA\n" + 
				"		WHERE RESERVA.RESERVAID=RHA.RESERVAID AND\n" + 
				"		      (WK.START_WEEK BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 1 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 2 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 3 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 4 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 5 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL) AND\n" + 
				"		      (WK.START_WEEK + 6 BETWEEN RESERVA.FECHAINICIAL AND RESERVA.FECHAFINAL)\n" + 
				"		      GROUP BY RHA.APTOID) APTOS) APTOMAX, APARTAMENTO APT\n" + 
				"		      WHERE APT.APARTAMENTOID=APTOMAX.APTOMAXI     \n" + 
				"		) AS COMUNIDADID\n" + 
				"		FROM\n" + 
				"		(SELECT distinct SEM.START_WEEK, SEM.END_WEEK\n" + 
				"		FROM\n" + 
				"		(SELECT \n" + 
				"		  MONTHS.MONTH_START, \n" + 
				"		  WN.WEEK_NUMBER, \n" + 
				"		  MONTHS.START_SECOND_WEEK + (WN.WEEK_NUMBER-2)*7 START_WEEK, \n" + 
				"		  MONTHS.START_SECOND_WEEK + (WN.WEEK_NUMBER-1)*7 - 1 END_WEEK \n" + 
				"		FROM \n" + 
				"		  (SELECT \n" + 
				"		    ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),(LEVEL-1)) MONTH_START, \n" + 
				"		    ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),LEVEL)-1 MONTH_END, \n" + 
				"		    NEXT_DAY(ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),(LEVEL-1))-1,'SABADO') END_FIRST_WEEK,\n" + 
				"		    NEXT_DAY(ADD_MONTHS(TO_DATE('01-01-%2$d','MM-DD-YYYY'),(LEVEL-1)),'DOMINGO') START_SECOND_WEEK\n" + 
				"		   FROM \n" + 
				"		    DUAL \n" + 
				"		  CONNECT BY \n" + 
				"		    LEVEL<=12) MONTHS, \n" + 
				"		  (SELECT \n" + 
				"		    LEVEL WEEK_NUMBER \n" + 
				"		  FROM \n" + 
				"		    DUAL \n" + 
				"		  CONNECT BY \n" + 
				"		    LEVEL<=6) WN \n" + 
				"		WHERE \n" + 
				"		  (MONTHS.START_SECOND_WEEK + (WN.WEEK_NUMBER-2)*7)<=MONTHS.MONTH_END) SEM\n" + 
				"		  order by SEM.START_WEEK) WK", USUARIO, year);
		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC12b> rfc4 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC12b reserva = RFC12bResultSet(rs);

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
	public RFC12 convertResultSetToReservaVivienda(ResultSet resultSet) throws SQLException {


		RFC12 rfc4 = null;

		int aptoid = resultSet.getInt("APTOID");
		int viviendaid = resultSet.getInt("VIVIENDAID");
		int habid = resultSet.getInt("HABID");
		String week = resultSet.getString("WEEK");
		String startWeek = resultSet.getString("START_WEEK");
		String enWeek = resultSet.getString("END_WEEK");
		
		rfc4 = new RFC12(week,startWeek, enWeek, habid, viviendaid, aptoid);

		return rfc4;
	}
	
	public RFC12b RFC12bResultSet(ResultSet resultSet) throws SQLException {


		RFC12b rfc4 = null;

		int operadorId = resultSet.getInt("COMUNIDADID");
		String week = resultSet.getString("WEEK");
		String startWeek = resultSet.getString("START_WEEK");
		String enWeek = resultSet.getString("END_WEEK");
		
		rfc4 = new RFC12b(week,startWeek, enWeek, operadorId);

		return rfc4;
	}
}
