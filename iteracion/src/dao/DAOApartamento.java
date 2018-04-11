package dao;

import java.security.cert.CertPathBuilderResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Apartamento;
import vos.Cliente;
import vos.Apartamento;
import vos.PersonaOperador;
import vos.Vivienda;

public class DAOApartamento {

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
		public DAOApartamento() {
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
		public void addApartamento(int idOperador, Apartamento apto) throws SQLException, Exception {

			String sql = String.format("INSERT INTO %1$s.APARTAMENTO (AMOBLADO, OCUPADO, UBICACION, APARTAMENTOID, COMUNIDADID) VALUES (%2$s,%3$s,'%4$s',%5$s, %6$s )", 
										USUARIO, 
										apto.isAmoblado() ? 1 : 0, 
										apto.isOcupado() ? 1:0, 
										apto.getUbicacion(), 
										apto.getId(), 
										idOperador);
										
		
			
		}
		public ArrayList<Apartamento> getAllAptos() throws SQLException, Exception {
			ArrayList<Apartamento> viviendas = new ArrayList<>();

			
			String sql = String.format( "SELECT * FROM %1$s.APARTAMENTO", USUARIO);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			
			System.out.println(sql);
			
					
			while (rs.next()) {
				System.out.println("entra al next");
				
				Apartamento viv = convertResultSetToApto(rs);
				
				if(viv!= null)
				viviendas.add(viv);
			}
			return viviendas;
		}
		
		
		
		
		public Apartamento findAptoByid(int idApto) throws SQLException
		{
			
			Apartamento apto = null;

			String sql = String.format("SELECT * FROM %1$s.APARTAMENTO WHERE APARTAMENTOID = %2$d", USUARIO, idApto); 

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			if(rs.next()) {
				apto = convertResultSetToApto(rs);
			}

			return apto;
			
			
		}
		
		public void deleteApto(Apartamento apto) throws SQLException
		{	
			String sql = String.format("DElETE FROM %1$s.APARTAMENTO WHERE APARTAMENTOID = %2$s ", 
						USUARIO, apto.getId());
			
			System.out.println(sql);
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			
			sql = String.format("DELETE FROM %1$s.APARTAMENTO WHERE APARTAMENTOID = %2$s ", 
					USUARIO, apto.getId());
			
			System.out.println(sql);
			prepStmt = conn.prepareStatement(sql);
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
		public Apartamento convertResultSetToApto(ResultSet resultSet) throws SQLException {

		
			Apartamento apto = null; 
			
			boolean amoblado = resultSet.getBoolean("AMOBLADO");
			boolean ocupado = resultSet.getBoolean("OCUPADO");
			String ubicacion = resultSet.getString("UBICACION"); 
			int id = resultSet.getInt("APARTAMENTOID"); 
			
			
			
			
			 apto = new Apartamento(id, amoblado, ocupado, ubicacion);
	
			
			return apto; 
		}
		
}
