package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Apartamento;
import vos.Cliente;
import vos.Hostal;
import vos.Hotel;
import vos.PersonaOperador;

public class DAOHotel {

	//----------------------------------------------------------------------------------------------------------------------------------
		// CONSTANTES
		//----------------------------------------------------------------------------------------------------------------------------------
		
		/**
		 * Constante para indicar el usuario Oracle del estudiante
		 */
		//TODO Requerimiento 1H: Modifique la constante, reemplazando al ususario PARRANDEROS por su ususario de Oracle
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
		 * Metodo constructor de la clase DAOHotel <br/>
		*/
		public DAOHotel() {
			recursos = new ArrayList<Object>();
		}
		
		//----------------------------------------------------------------------------------------------------------------------------------
		// METODOS DE COMUNICACION CON LA BASE DE DATOS
		//----------------------------------------------------------------------------------------------------------------------------------
	
	

		public void addHotel(Hotel hotel) throws SQLException, Exception {

			
			if(hotel instanceof Hostal)
			{	
				Hostal hostal = (Hostal) hotel; 
				
			String sql = String.format("INSERT INTO %1$s.HOTELERIA (CAPACIDAD, DISPONIBILIDAD, REGISTROCAMARACOMERCIO, REGISTROSUPERINTENDENCIA, UBICACION, HOTELERIAID, NOMBRE, HORAAPERTURA, HORACIERRE ) "
					+ "VALUES (%2$s,%3$s,'%4$s','%5$s', '%6$s', %7$s , '%8$s', '%9$s', '%10$s', 'HOSTAL'  )", 
										USUARIO, 
										hostal.getCapacidad(), 
										hostal.getDisponibilidad(), 
										hostal.getRegistroCamaraComercio(), 
										hostal.getRegistroSuperIntendencia(), 
										hostal.getUbicacion(), 
										hostal.getId(),
										hostal.getNombre(), 
										hostal.getHoraApertura(),
										hostal.getHoraCierre());
										
			System.out.println(sql);
			System.out.println("1");
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			System.out.println("2");
			recursos.add(prepStmt);
			System.out.println("3");
			prepStmt.executeQuery();
			System.out.println("4");
			
			}else 
			{
				String sql = String.format("INSERT INTO %1$s.HOTELERIA (CAPACIDAD, DISPONIBILIDAD, REGISTROCAMARACOMERCIO, REGISTROSUPERINTENDENCIA, UBICACION, HOTELERIAID, NOMBRE, TIPOH) "
						+ "VALUES (%2$s,%3$s,'%4$s','%5$s', '%6$s', %7$s , '%8$s', 'HOTEL')", 
											USUARIO, 
											hotel.getCapacidad(), 
											hotel.getDisponibilidad(), 
											hotel.getRegistroCamaraComercio(), 
											hotel.getRegistroSuperIntendencia(), 
											hotel.getUbicacion(), 
											hotel.getId(), 
											hotel.getNombre());
											
				System.out.println(sql);
		
				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				prepStmt.executeQuery();
					
			}
			
		}
		
		
		/**
		 * Metodo que obtiene la informacion de todos los hoteles en la Base de Datos <br/>
		 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
		 * @return	lista con la informacion de todos los bebedores que se encuentran en la Base de Datos
		 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
		 * @throws Exception Si se genera un error dentro del metodo.
		 */
		public ArrayList<Hotel> getHoteles() throws SQLException, Exception {
			ArrayList<Hotel> hoteles = new ArrayList<Hotel>();

			
			String sql = String.format( "SELECT * FROM %1$s.HOTELERIA WHERE TIPOH = 'HOTEL'", USUARIO);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			
			System.out.println("depues sql");
			
					
			while (rs.next()) {
				System.out.println("entra al next");
				
				Hotel hotel = convertResultSetToHotel(rs);
				
				if(hotel!= null)
				hoteles.add(hotel);
			}
			return hoteles;
		}


		/**
		 * Metodo que obtiene la informacion del hotel en la Base de Datos que tiene el identificador dado por parametro<br/>
		 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
		 * @param id el identificador del hotel
		 * @return la informacion del bebedor que cumple con los criterios de la sentecia SQL
		 * 			Null si no existe el bebedor conlos criterios establecidos
		 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
		 * @throws Exception Si se genera un error dentro del metodo.
		 */
		public Hotel findHotelById(int id) throws SQLException, Exception 
		{
			Hotel hotel = null;

			String sql = String.format("SELECT * FROM %1$s.HOTELERIA WHERE HOTELERIAID = %2$d", USUARIO, id); 

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			if(rs.next()) {
				hotel = convertResultSetToHotel(rs);
			}

			return hotel;
		}
		
		
		/**
		 * Metodo que obtiene la informacion de todos los hoteles en la Base de Datos <br/>
		 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
		 * @return	lista con la informacion de todos los bebedores que se encuentran en la Base de Datos
		 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
		 * @throws Exception Si se genera un error dentro del metodo.
		 */
		public ArrayList<Hostal> getHostales() throws SQLException, Exception {
			ArrayList<Hostal> hostales = new ArrayList<>();

			
			String sql = String.format( "SELECT * FROM %1$s.HOTELERIA WHERE TIPOH = 'HOSTAL'", USUARIO);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			
			System.out.println("depues sql");
			
					
			while (rs.next()) {
				System.out.println("entra al next");
				
				Hostal hotel = (Hostal) convertResultSetToHotel(rs);
				
				if(hotel!= null)
				hostales.add(hotel);
			}
			return hostales;
		}
		
		
		
		
		public void deleteHotel(int hoteleriaId) throws SQLException
		{
			
		
				
			String sql = String.format("DELETE FROM %1$s.HOTELERIA WHERE HOSTALID = %2$s ", 
						USUARIO, hoteleriaId);
			
			System.out.println(sql);
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
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
		 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla CLIENTES) en una instancia de la clase Cliente	 * @param resultSet ResultSet con la informacion de un bebedor que se obtuvo de la base de datos.
		 * @return Cliente cuyos atributos corresponden a los valores asociados a un registro particular de la tabla Clientes.
		 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
		 */
		public Hotel convertResultSetToHotel(ResultSet resultSet) throws SQLException {

		
			Hotel hotel = null;
			Hostal hostal = null;
			

			String nombre = resultSet.getString("NOMBRE"); 
			int capacidad = resultSet.getInt("CAPACIDAD"); 
			int disponibilidad = resultSet.getInt("DISPONIBILIDAD"); 
			String registroCamaraComercio = resultSet.getString("REGISTROCAMARACOMERCIO");
			String registroSuperIntendencia = resultSet.getString("REGISTROSUPERINTENDENCIA"); 
			String ubicacion = resultSet.getString("UBICACION"); 
			int id = resultSet.getInt("HOTELERIAID");
			
			
			if(resultSet.getString("TIPOH").equals("HOSTAL")) {
				
				String horaApertura = resultSet.getString("HORAAPERTURA"); 
				String horaCierre =  resultSet.getString("HORACIERRE"); 
				
				hostal = new Hostal(nombre, null, id, capacidad, disponibilidad, registroCamaraComercio, registroSuperIntendencia, ubicacion, horaApertura, horaCierre);
				
			}
			
			hotel = new Hotel(nombre, null, id, capacidad, disponibilidad, registroCamaraComercio, registroSuperIntendencia, ubicacion);
			
			return hotel;
		}

		
		
		
		
	
}
