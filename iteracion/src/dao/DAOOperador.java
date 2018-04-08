package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;
import vos.PersonaOperador;

public class DAOOperador {
	
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
		 * Metodo constructor de la clase DAOOperador <br/>
		*/
		public DAOOperador() {
			recursos = new ArrayList<Object>();
		}
		
	
		
		/**
		 * Metodo que agregar la informacion de un nuevo operador en la Base de Datos a partir del parametro ingresado<br/>
		 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
		 * @param PersonaOperador operador que desea agregar a la Base de Datos
		 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
		 * @throws Exception Si se genera un error dentro del metodo.
		 */
		public void addOperador(PersonaOperador operador) throws SQLException, Exception {
			
			String sql = String.format("INSERT INTO %1$s.COMUNIDAD (CARNET, NOMBRE, ROL, COMUNIDADID, TIPO)"
					+ " VALUES (%2$s, '%3$s', '%4$s', %5$s, 'OPERADOR')", 
										USUARIO, 
										operador.getCarnet(), operador.getNombre(), operador.getRol(), operador.getId() );
										
			System.out.println(sql);
			
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
		}
		

		/**
		 * Metodo que obtiene la informacion de todos los operadores en la Base de Datos <br/>
		 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
		 * @return	lista con la informacion de todos los bebedores que se encuentran en la Base de Datos
		 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
		 * @throws Exception Si se genera un error dentro del metodo.
		 */
		public ArrayList<PersonaOperador> getAllOperadores() throws SQLException, Exception {
			ArrayList<PersonaOperador> operadores = new ArrayList<>();

			
			String sql = String.format( "SELECT * FROM %1$s.COMUNIDAD WHERE TIPO = 'OPERADOR'", USUARIO);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			
			System.out.println(sql);
			
					
			while (rs.next()) {
				System.out.println("entra al next");
				
				PersonaOperador operador = convertResultSetToOperador(rs);
				
				if(operador!= null)
				operadores.add(operador);
			}
			return operadores;
		}
		
		
		/**
		 * Metodo que obtiene la informacion del bebedor en la Base de Datos que tiene el identificador dado por parametro<br/>
		 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
		 * @param id el identificador del bebedor
		 * @return la informacion del bebedor que cumple con los criterios de la sentecia SQL
		 * 			Null si no existe el bebedor conlos criterios establecidos
		 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
		 * @throws Exception Si se genera un error dentro del metodo.
		 */
		public PersonaOperador findOperadorById(int id) throws SQLException, Exception 
		{
			PersonaOperador operador = null;

			String sql = String.format("SELECT * FROM %1$s.COMUNIDAD WHERE COMUNIDADID = %2$d", USUARIO, id); 

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			if(rs.next()) {
				operador = convertResultSetToOperador(rs);
			}

			return operador;
		}
		
		/*
		 * Metodos auxiliares
		 */
		
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
		public PersonaOperador convertResultSetToOperador(ResultSet resultSet) throws SQLException {

		
			PersonaOperador operador = null;
			
			String nombre=""; 
			String rol = ""; 
			int carnet = 0;
			int id = 0;
			
			id = resultSet.getInt("COMUNIDADID");
			nombre = resultSet.getString("NOMBRE");
			rol = resultSet.getString("ROL"); 
			carnet = resultSet.getInt("CARNET");
			String tipo = resultSet.getString("TIPO");
			
			if(tipo.equals("OPERADOR"))
			{
			operador = new PersonaOperador(id, nombre, rol, carnet);
			}
			
			
			return operador;
		}

		
		

		
		
		
}
