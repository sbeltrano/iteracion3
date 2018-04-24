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
import vos.RFC5y6;
import vos.RFC5y6;
import vos.RFC1;

public class DAORFC5y6 {

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
	public DAORFC5y6() {
		recursos = new ArrayList<Object>();
	}

	public ArrayList<RFC5y6> usoAlohandesParaTipoUsuario(String tipoUsuario) throws SQLException
	{
		
		String sql = String.format("SELECT *\r\n" + 
				"		FROM(\r\n" + 
				"		SELECT *\r\n" + 
				"		FROM %1$s.COMUNIDAD\r\n" + 
				"		WHERE %1$s.COMUNIDAD.TIPO='%2$s'),%1$s.RESERVA\r\n" + 
				"		INNER JOIN %1$s.COMUNIDAD ON %1$s.COMUNIDAD.COMUNIDADID=%1$s.RESERVA.COMUNIDADID\r\n" + 
				"		ORDER BY %1$s.RESERVA.COMUNIDADID ASC", USUARIO,tipoUsuario );

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC4 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC5y6> rfc5y6 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC5y6 reserva = rfc5convert(rs);

			if(reserva!= null)
			rfc5y6.add(reserva);
		}
		return rfc5y6;

	}
	
	public ArrayList<RFC5y6> usoAlohandesParaUsuario(int usuarioId) throws SQLException
	{
		String sql = String.format("SELECT *\r\n" + 
				"FROM(\r\n" + 
				"SELECT *\r\n" + 
				"FROM %1$s.COMUNIDAD\r\n" + 
				"WHERE %1$s.COMUNIDAD.TIPO='%2$s'), RESERVA\r\n" + 
				"INNER JOIN %1$s.COMUNIDAD ON %1$s.COMUNIDAD.COMUNIDADID=%1$s.RESERVA.COMUNIDADID\r\n" + 
				"ORDER BY %1$s.COMUNIDAD.COMUNIDADID ASC", USUARIO,usuarioId );

		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		System.out.println("SentenciaMakiaRFC5y6 = "+sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<RFC5y6> rfc5y6 = new ArrayList<>();

		while (rs.next()) {
			System.out.println("entra al next RFC1");

			RFC5y6 reserva = rfc6convert(rs);

			if(reserva!= null)
			rfc5y6.add(reserva);
		}
		return rfc5y6;

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
	public RFC5y6 rfc5convert(ResultSet resultSet) throws SQLException {


		RFC5y6 rfc5y6 = null;

		int usuarioId = resultSet.getInt("COMUNIDADID");
		String fechaInicial = resultSet.getString("FECHAINICIAL");
		String FechaFinal = resultSet.getString("FECHAINICIAL");
		
		int mesesContratados = 69;
		String tipoUsuario = resultSet.getString("TIPO");
		String caracteristicasAlojamiento = resultSet.getString("TIPOR");;
		int dineroPagado = resultSet.getInt("PRECIO");


		rfc5y6 = new RFC5y6(usuarioId, tipoUsuario, mesesContratados, caracteristicasAlojamiento, dineroPagado);

		return rfc5y6;
	}
	/**
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla RESERVAS) en una instancia de la clase Cliente	 * @param resultSet ResultSet con la informacion de un bebedor que se obtuvo de la base de datos.
	 * @return Reserva cuyos atributos corresponden a los valores asociados a un registro particular de la tabla Reservas.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public RFC5y6 rfc6convert(ResultSet resultSet) throws SQLException {


		RFC5y6 rfc5y6 = null;

		int usuarioId = resultSet.getInt("COMUNIDADID");
		String fechaInicial = resultSet.getString("FECHAINICIAL");
		String FechaFinal = resultSet.getString("FECHAINICIAL");
		
		int mesesContratados = 69;
		String tipoUsuario = resultSet.getString("TIPO");
		String caracteristicasAlojamiento = resultSet.getString("TIPOR");;
		int dineroPagado = resultSet.getInt("PRECIO");


		rfc5y6 = new RFC5y6(usuarioId, tipoUsuario, mesesContratados, caracteristicasAlojamiento, dineroPagado);

		return rfc5y6;
	}
}
