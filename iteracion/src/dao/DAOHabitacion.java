package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Apartamento;
import vos.Cliente;
import vos.Habitacion;
import vos.PersonaOperador;

public class DAOHabitacion {

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
		 * Metodo constructor de la clase DAOHabitacion <br/>
		*/
		public DAOHabitacion() {
			recursos = new ArrayList<Object>();
		}
		
		//----------------------------------------------------------------------------------------------------------------------------------
		// METODOS DE COMUNICACION CON LA BASE DE DATOS
		//----------------------------------------------------------------------------------------------------------------------------------
	

		public void addHabitacionHotel(Habitacion habitacion, int idHotel) throws SQLException, Exception {

			String sql = String.format("INSERT INTO %1$s.HABITACION (COMPARTIDA, DESCRIPCION, OCUPADA, HABITACIONID, HOTELERIAID) VALUES (%2$s,'%3$s',%4$s,%5$s, %6$s )", 
										USUARIO, 
										habitacion.isCompartida() ? 1 : 0, 
										habitacion.getDescripcion(), 
										habitacion.getOcupada()? 1 : 0, 
										habitacion.getId(), 
										idHotel);
										
			
				
			
			System.out.println(sql);
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			
		}
		
		public void addHabitacionPersona(Habitacion habitacion, int idPersona) throws SQLException, Exception {

			String sql = String.format("INSERT INTO %1$s.HABITACION (COMPARTIDA, DESCRIPCION, OCUPADA, HABITACIONID, COMUNIDADID) VALUES (%2$s,'%3$s',%4$s,%5$s, %6$s )", 
										USUARIO, 
										habitacion.isCompartida() ? 1 : 0, 
										habitacion.getDescripcion(), 
										habitacion.getOcupada()? 1 : 0, 
										habitacion.getId(), 
										idPersona);
			
			System.out.println(sql);
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			
		}
		
		public void deleteHabitacion(Habitacion habitacion) throws SQLException
		{
			
			String sql = String.format("DELETE FROM  %1$s.HABITACION  WHERE HABITACIONID = %2$s ", 
						USUARIO, habitacion.getId());
			
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
		public Habitacion convertResultSetToHabitacion(ResultSet resultSet) throws SQLException {

		
			Habitacion habitacion = null;
			
			
			
			
			int id = resultSet.getInt("HABITACIONID");
			String descripcion = resultSet.getString("DESCRIPCION"); 
			boolean compartida = resultSet.getBoolean("COMPARTIDA"); 
			boolean ocupada = resultSet.getBoolean("OCUPADA");
			
			
			
			habitacion = new Habitacion(null, id, compartida, descripcion, ocupada);
			
			
			
			return habitacion;
		}
		
}
