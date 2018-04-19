package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;
import vos.Servicios;

public class DAOServicios {

	
	//Nombres columnas
	
	public final static String SERVICIOSID = "SERVICIOSID";
	public final static String AGUA = "AGUA";
	public final static String BAÑERA = "BAÑERA";
	public final static String COCINETA = "COCINETA";
	public final static String PARQUEADERO = "PARQUEADERO";
	public final static String PISCINA = "PISCINA";
	public final static String RECEPCION24H = "RECEPCION24H";	
	public final static String RESTAURANTE = "RESTAURANTE";	
	public final static String SALA = "SALA";		
	public final static String TV = "TV";	
	public final static String WIFI = "WIFI";	
	public final static String YACUZZI = "YACUZZI";
	public final static String HABITACIONID = "HABITACIONID";
	
	
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
		 * Metodo constructor de la clase DAOBebedor <br/>
		*/
		public DAOServicios() {
			recursos = new ArrayList<Object>();
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
		
		
		public void addServicios(Servicios servicios, int habitacionId) throws SQLException, Exception
		{
			String sql = String.format("insert into servicios (agua, bañera, cocineta, parqueadero, piscina, recepcion24h, restaurante, sala, tv, wifi, yacuzzi, habitacionid)" + 
					"values (%2$s,%3$s,%4$s,%5$s,%6$s,%7$s,%8$s,%9$s,%10$s,%11$s,%12$s, %13$s)", 
					USUARIO,
					servicios.isAgua()? 1 : 0,
					servicios.isBañera()? 1 : 0,
					servicios.isCocineta() ? 1 : 0,
					servicios.isParquedero()? 1 : 0,
					servicios.isPiscina()? 1 : 0,
					servicios.isRecepcion24h()? 1 : 0,
					servicios.isRestaurante()? 1 : 0,
					servicios.isSala()? 1 : 0,
					servicios.isTv()? 1 : 0,
					servicios.isWifi()? 1 : 0,
					servicios.isYacuzzi()? 1 : 0,
					habitacionId
					);
			
			
			
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			
			
			
		}
		
		
		
		public Servicios findServicioById(int id)throws SQLException, Exception
		{
			
			Servicios servicio = null;
			
			String sql = String.format("SELECT * FROM SERVICIOS WHERE SERVICIOSID = %2$s", USUARIO, id);
			
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			if(rs.next()) {
				servicio = convertResultSetToServicios(rs);
			}

			return servicio;
			
		}
		
		
		public Servicios getServicioHabitacion(int idHabitacion)throws SQLException, Exception
		{
			
			Servicios servicio = null;
			
			String sql = String.format("SELECT * FROM SERVICIOS WHERE SERVICIOSID = %2$s", USUARIO, idHabitacion);
			
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			if(rs.next()) {
				servicio = convertResultSetToServicios(rs);
			}

			return servicio;
			
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
		 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla CLIENTES) en una instancia de la clase Cliente	 * @param resultSet ResultSet con la informacion de un bebedor que se obtuvo de la base de datos.
		 * @return Cliente cuyos atributos corresponden a los valores asociados a un registro particular de la tabla Clientes.
		 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
		 */
		public Servicios convertResultSetToServicios(ResultSet resultSet) throws SQLException {

		
			Servicios servicios = null;
			

			int id = resultSet.getInt(SERVICIOSID);
			boolean agua = resultSet.getBoolean(AGUA);
			boolean bañera = resultSet.getBoolean(BAÑERA);
			boolean cocineta = resultSet.getBoolean(COCINETA);
			boolean piscina = resultSet.getBoolean(PISCINA); 
			boolean parqueadero = resultSet.getBoolean(PARQUEADERO);
			boolean recepcion = resultSet.getBoolean(RECEPCION24H);
			boolean restaurante = resultSet.getBoolean(RESTAURANTE); 
			boolean sala = resultSet.getBoolean(SALA);
			boolean tv = resultSet.getBoolean(TV);
			boolean wifi = resultSet.getBoolean(WIFI); 
			boolean yacuzzi = resultSet.getBoolean(YACUZZI);
			int habitacionid = resultSet.getInt(HABITACIONID); 
			
				
			
			
			
			servicios = new Servicios(habitacionid, agua, bañera, cocineta, parqueadero, piscina, recepcion, restaurante, sala, tv, wifi, yacuzzi);
			
			
			return servicios;
		}
	
}
