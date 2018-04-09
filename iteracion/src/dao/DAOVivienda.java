package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Apartamento;
import vos.Habitacion;
import vos.Hotel;
import vos.PersonaOperador;
import vos.Vivienda;

public class DAOVivienda {

	
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
		public DAOVivienda() {
			recursos = new ArrayList<Object>();
		}
		
		//----------------------------------------------------------------------------------------------------------------------------------
		// METODOS DE COMUNICACION CON LA BASE DE DATOS
		//----------------------------------------------------------------------------------------------------------------------------------
		/**
		 * Metodo que agregar la informacion de un nuevo bebedor en la Base de Datos a partir del parametro ingresado<br/>
		 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
		 * @param cliente cliente que desea agregar a la Base de Datos
		 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
		 * @throws Exception Si se genera un error dentro del metodo.
		 */
		public void addVivienda(int operadorId, Vivienda viv) throws SQLException, Exception {

			String sql = String.format("INSERT INTO %1$s.VIVIENDA (MENAJE, NUMEROHABITACIONES, OCUPADA, UBICACION, VIVIENDAID, COMUNIDADID) VALUES (%2$s,'%3$s',%4$s,'%5$s', %6$s, %7$s )", 
										USUARIO, 
										viv.isMenaje() ? 1 : 0, 
										viv.getNumeroHabitaciones(),
										viv.isOcupado() ? 1:0, 
										viv.getUbicacion(),
										viv.getId(),
										operadorId);
										
			System.out.println(sql);
			
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			
			sql = String.format("INSERT INTO %1$s.COMUNIDAD (VIVIENDAID) VALUES (%2$s) ", 
								USUARIO, 
								viv.getId());
			
			
			System.out.println(sql);
			prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			
		}
		
		
		public void deleteVivienda(Vivienda viv) throws SQLException
		{	
			String sql = String.format("DElETE FROM %1$s.VIVIENDA WHERE VIVIENDAID = %2$s ", 
						USUARIO, viv.getId());
			
			System.out.println(sql);
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			
			sql = String.format("DELETE FROM %1$s.VIVIENDA WHERE VIVIENDAID = %2$s ", 
					USUARIO, viv.getId());
			
			System.out.println(sql);
			prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();		
		}
		
		
		public ArrayList<Vivienda> getViviendas() throws SQLException, Exception {
			ArrayList<Vivienda> viviendas = new ArrayList<Vivienda>();

			
			String sql = String.format( "SELECT * FROM %1$s.VIVIENDA", USUARIO);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			
			System.out.println("depues sql");
			
					
			while (rs.next()) {
				System.out.println("entra al next");
				
				Vivienda viv = convertResultSetToVivienda(rs);
				
				if(viv!= null)
				viviendas.add(viv);
			}
			return viviendas;
		}
		
		public Vivienda findViviendaById(int id) throws SQLException, Exception {
			Vivienda vivienda = null;

			
			String sql = String.format( "SELECT * FROM %1$s.VIVIENDA WHERE VIVIENDAID = %2$", USUARIO, id);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			
			System.out.println("depues sql");
			
					
			if (rs.next()) {
				
			vivienda = convertResultSetToVivienda(rs);
				
			}
			
			return vivienda;
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
		public Vivienda convertResultSetToVivienda(ResultSet resultSet) throws SQLException {

		
			Vivienda viv = null; 
			
			boolean menaje = resultSet.getBoolean("MENAJE");
			boolean ocupada = resultSet.getBoolean("OCUPADA");
			String ubicacion = resultSet.getString("UBICACION"); 
			int numeroHabitaciones = resultSet.getInt("NUMEROHABITACIONES"); 
			int viviendaId = resultSet.getInt("VIVIENDAID"); 
			int personaOperadorId = resultSet.getInt("COMUNIDADID");
			

			 viv = new Vivienda(menaje, numeroHabitaciones, ocupada, ubicacion, viviendaId, null);
	
			
			return viv; 
		}
}
