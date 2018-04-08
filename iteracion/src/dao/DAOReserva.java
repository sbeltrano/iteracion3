package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ContratoApto;
import vos.ContratoHabitacion;
import vos.ContratoVivienda;
import vos.Habitacion;
import vos.PersonaOperador;
import vos.Reserva;

public class DAOReserva {

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
			public DAOReserva() {
				recursos = new ArrayList<Object>();
			}
			
			
			
			
			/**
			 * Metodo que agregar la informacion de un nuevo operador en la Base de Datos a partir del parametro ingresado<br/>
			 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
			 * @param PersonaOperador operador que desea agregar a la Base de Datos
			 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
			 * @throws Exception Si se genera un error dentro del metodo.
			 */
			public void addReserva(Reserva reserva) throws SQLException, Exception {
				
				if(reserva instanceof ContratoVivienda)
				{
					
					ContratoVivienda  contratoVivienda = (ContratoVivienda) reserva;
					
				
				String sql = String.format("INSERT INTO %1$s.RESERVA (FECHAINICIAL, FECHAFINAL, PRECIO, RESERVAID, MENAJE, NUMEROHABITACIONES, TIPOR)"
						+ " VALUES ('%2$s', '%3$s', %4$s, %5$s, %6$s,%7$s, '%8$s' )", 
											USUARIO, 
											contratoVivienda.getFechaInicial(),
											contratoVivienda.getFechaFinal(),
											contratoVivienda.getPrecio(),
											contratoVivienda.getId(),
											contratoVivienda.isMenaje() ? 1: 0,
											contratoVivienda.getNumeroHabitaciones(), 
											"CONTRATOVIVIENDA");
											
											
				System.out.println(sql);
				
				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				prepStmt.executeQuery();
				}
				else if (reserva instanceof ContratoApto)
				{

					ContratoApto contratoApto = (ContratoApto) reserva;
					
					String sql = String.format("INSERT INTO %1$s.RESERVA (FECHAINICIAL, FECHAFINAL, PRECIO, RESERVAID,PRECIOSERVICIOS, INCLUYEADMIN, INCLUYESERVPUBLICOS , TIPOR, COMUNIDADID)"
							+ " VALUES ('%2$s', '%3$s', %4$s, %5$s, %6$s, %7$s, %8$s, '%9$s',  )", 
												USUARIO, 
												contratoApto.getFechaInicial(),
												contratoApto.getFechaFinal(),
												contratoApto.getPrecio(),
												contratoApto.getId(),
												contratoApto.getPrecioServicio() ? 1: 0,
												contratoApto.isIncluyeAdmin() ? 1 : 0,
												contratoApto.isIncluyeServPublicos()? 1 : 0,
												"CONTRATOAPARTAMENTO");
												
												
					System.out.println(sql);
					
					PreparedStatement prepStmt = conn.prepareStatement(sql);
					recursos.add(prepStmt);
					prepStmt.executeQuery();
					
				}else if (reserva instanceof ContratoHabitacion)
				{
				
					ContratoHabitacion contratoHab = (ContratoHabitacion) reserva; 

					String sql = String.format("INSERT INTO %1$s.RESERVA (FECHAINICIAL, FECHAFINAL, PRECIO, RESERVAID, ACCESOCOCINA, BAÑOPRIVADO, COMUDAS, HABITACIONINDIVIDUAL, DURACION, COMUNIDADID) "
							+ "VALUES ('%2$s', '%3$s', %4$s, %5$s, %6$s, %7$s, %8$s, %9$s, %10$s, '%11$s')", 
												USUARIO, 
												contratoHab.getFechaInicial(),
												contratoHab.getFechaFinal(),
												contratoHab.getPrecio(),
												contratoHab.getId(),
												contratoHab.isAccesoCocina() ? 1: 0,
												contratoHab.isBañoPrivado() ? 1 : 0, 
												contratoHab.isComidas() ? 1 : 0, 
												contratoHab.isHabitacionIndividual()? 1:0,
												contratoHab.getDuracion(), 
												"CONTRATOHABITACION");
												
												
					System.out.println(sql);
					PreparedStatement prepStmt = conn.prepareStatement(sql);
					recursos.add(prepStmt);
					prepStmt.executeQuery();
					
				}else 
				{

					String sql = String.format("INSERT INTO %1$s.RESERVA (FECHAINICIAL, FECHAFINAL, PRECIO, RESERVAID) VALUES ('%2$s', '%3$s', %4$s, %5$s)", 
												USUARIO, 
												reserva.getFechaInicial(),
												reserva.getFechaFinal(),
												reserva.getPrecio(),
												reserva.getId());
												
					System.out.println(sql);
					
					PreparedStatement prepStmt = conn.prepareStatement(sql);
					recursos.add(prepStmt);
					prepStmt.executeQuery();
					
					
				}
				
				
				
			}	
			
			public void cancelarReserva(Reserva reserva) throws SQLException
			{
				
				String sql = String.format("DELETE FROM %1$s.RESERVAS WHERE RESERVAID = %2$s", USUARIO, reserva.getId()); 
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
			 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla RESERVAS) en una instancia de la clase Cliente	 * @param resultSet ResultSet con la informacion de un bebedor que se obtuvo de la base de datos.
			 * @return Reserva cuyos atributos corresponden a los valores asociados a un registro particular de la tabla Reservas.
			 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
			 */
			public Reserva convertResultSetToHabitacion(ResultSet resultSet) throws SQLException {

			
				Reserva reserva = null;
				String tipo = resultSet.getString("TIPOR");
				
				int id = resultSet.getInt("RESERVAID");
				String fechaFinal = resultSet.getString("FECHAFINAL");
				String fechaInicial = resultSet.getString("FECHAINICIAL");
				int precio = resultSet.getInt("PRECIO");
				
				if(tipo.equals("CONTRATOVIVIENDA"))
				{
				
				boolean menaje = resultSet.getBoolean("MENAJE");
				int numeroHabitaciones = resultSet.getInt("NUMEROHABITACIONES");
				
				reserva = new ContratoVivienda(null, id, fechaInicial, fechaFinal, precio, menaje, numeroHabitaciones);
				
				}else if (tipo.equals("CONTRATOAPTO"))
				{
					
					boolean incluyeAdmin = resultSet.getBoolean("INCLUYEADMIN");
					boolean  incluyeServPublicos = resultSet.getBoolean("INCLUYESERVPUBLICOS"); 
					boolean precioServicio = resultSet.getBoolean("PRECIOSERVPUBLICOS");
	
					reserva  = new ContratoApto(id, fechaInicial, fechaFinal, precio, incluyeServPublicos, incluyeAdmin, precioServicio);
					
					
				}else if (tipo.equals("CONTRATOHABITACION"))
				{
					boolean accesoCocina = resultSet.getBoolean("ACCESOCOCINA");
					boolean bañoPrivado = resultSet.getBoolean("COMIDAS");
					int duracion = resultSet.getInt("DURACION");
					boolean comidas = resultSet.getBoolean("COMIDAS");
					boolean habitacionIndividual = resultSet.getBoolean("HABITACIONINDIVIDUAL"); 
					int precioServicios = resultSet.getInt("PRECIOSERVICIOS"); 
					
					
					reserva = new ContratoHabitacion(id, fechaInicial, fechaFinal, precio, accesoCocina, bañoPrivado, comidas, duracion, habitacionIndividual, precioServicios);
				}
				
				
				
				reserva = new Reserva(id, fechaInicial, fechaFinal, precio);
				
				
				
				return reserva;
			}
			
	
	
}
