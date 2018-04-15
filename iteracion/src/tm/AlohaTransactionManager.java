/**-------------------------------------------------------------------
 * ISIS2304 - Sistemas Transaccionales
 * Departamento de Ingenieria de Sistemas
 * Universidad de los Andes
 * Bogota, Colombia
 * 
 * Actividad: Tutorial Parranderos: Arquitectura
 * Autores:
 * 			Juan Pablo Campos	-	
 * 			Santiago Beltran	-	
 * -------------------------------------------------------------------
 */
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dao.DAOApartamento;
import dao.DAOCliente;
import dao.DAOHabitacion;
import dao.DAOHotel;
import dao.DAOOperador;
import dao.DAOReserva;
import dao.DAOServicios;
import dao.DAOVivienda;
import vos.Apartamento;
import vos.Cliente;
import vos.Comunidad;
import vos.Habitacion;
import vos.Hotel;
import vos.PersonaOperador;
import vos.Reserva;
import vos.ReservaColectiva;
import vos.Servicios;
import vos.Vivienda;

/**
 * @author Juan Pablo Campos
 * @author Santiago Beltrán
 * 
 * Clase que representa al Manejador de Transacciones de la Aplicacion (Fachada en patron singleton de la aplicacion)
 * Responsabilidades de la clase: 
 * 		Intermediario entre los servicios REST de la aplicacion y la comunicacion con la Base de Datos
 * 		Modelar y manejar autonomamente las transacciones y las reglas de negocio.
 */
public class AlohaTransactionManager {

	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private static String CONNECTION_DATA_PATH;



	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * Atributo que representa la conexion a la base de datos
	 */
	private Connection conn;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE CONEXION E INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * <b>Metodo Contructor de la Clase ParranderosTransactionManager</b> <br/>
	 * <b>Postcondicion: </b>	Se crea un objeto  ParranderosTransactionManager,
	 * 						 	Se inicializa el path absoluto del archivo de conexion,
	 * 							Se inicializna los atributos para la conexion con la Base de Datos
	 * @param contextPathP Path absoluto que se encuentra en el servidor del contexto del deploy actual
	 * @throws IOException Se genera una excepcion al tener dificultades con la inicializacion de la conexion<br/>
	 * @throws ClassNotFoundException 
	 */
	public AlohaTransactionManager(String contextPathP) {

		try {
			CONNECTION_DATA_PATH = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
			initializeConnectionData();
		} 
		catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo encargado de inicializar los atributos utilizados para la conexion con la Base de Datos.<br/>
	 * <b>post: </b> Se inicializan los atributos para la conexion<br/>
	 * @throws IOException Se genera una excepcion al no encontrar el archivo o al tener dificultades durante su lectura<br/>
	 * @throws ClassNotFoundException 
	 */
	private void initializeConnectionData() throws IOException, ClassNotFoundException {

		FileInputStream fileInputStream = new FileInputStream(new File(AlohaTransactionManager.CONNECTION_DATA_PATH));
		Properties properties = new Properties();

		properties.load(fileInputStream);
		fileInputStream.close();

		this.url = properties.getProperty("url");
		this.user = properties.getProperty("usuario");
		this.password = properties.getProperty("clave");
		this.driver = properties.getProperty("driver");

		//Class.forName(driver);
	}

	/**
	 * Metodo encargado de generar una conexion con la Base de Datos.<br/>
	 * <b>Precondicion: </b>Los atributos para la conexion con la Base de Datos han sido inicializados<br/>
	 * @return Objeto Connection, el cual hace referencia a la conexion a la base de datos
	 * @throws SQLException Cualquier error que se pueda llegar a generar durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("[ALOHANDES APP] Attempting Connection to: " + url + " - By User: " + user);
		return DriverManager.getConnection(url, user, password);
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS TRANSACCIONALES
	//----------------------------------------------------------------------------------------------------------------------------------




	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS AGREGAR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que modela la transaccion que agrega un bebedor a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el bebedor que entra como parametro <br/>
	 * @param bebedor - el bebedor a agregar. bebedor != null
	 * @throws Exception - Cualquier error que se genere agregando el bebedor
	 */
	public void addCliente(Cliente cliente) throws Exception 
	{

		DAOCliente daoCliente = new DAOCliente( );
		try
		{
			this.conn = darConexion(); 
			conn.setAutoCommit(true);
			daoCliente.setConn(conn);

			if(!cliente.getRol().equals(Comunidad.FAMILIAR)	&&!cliente.getRol().equals(Comunidad.ESTUDIANTE )&&!cliente.getRol().equals(Comunidad.PROFESOR))
			{
				throw new Exception("El cliente no cumple con los requisitos para inscribirse"); 
			}



			daoCliente.addCliente(cliente);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que agrega un operador a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el operador que entra como parametro <br/>
	 * @param bebedor - el operador a agregar. bebedor != null
	 * @throws Exception - Cualquier error que se genere agregando el bebedor
	 */
	public void addOperador(PersonaOperador operador) throws Exception 
	{

		DAOOperador daoOperador = new DAOOperador( );
		try
		{
			this.conn = darConexion(); 
			conn.setAutoCommit(true);
			daoOperador.setConn(conn);

			if(!operador.getRol().equals(Comunidad.FAMILIAR)	&&!operador.getRol().equals(Comunidad.ESTUDIANTE )&&!operador.getRol().equals(Comunidad.PROFESOR)
					&& !operador.getRol().equals(Comunidad.HOTEL))
			{
				throw new Exception("El operador no cumple con los requisitos para inscribirse"); 
			}


			System.out.println("Pasa el exception de tm");
			daoOperador.addOperador(operador);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Metodo que modela la transaccion que hace una reserva . <br/>
	 * <b> post: </b> se ha hecho la reserva <br/>
	 * @param hotel - la reserva. reserva != null
	 * @throws Exception - Cualquier error que se genere agregando el hotel
	 */
	public void addReservaApartamento(Reserva reserva, int idCliente, int idApartamento) throws Exception 
	{

		DAOReserva daoReserva = new DAOReserva( );
		DAOApartamento daoApartamento = new DAOApartamento(); 
		
		try
		{
			this.conn = darConexion(); 
			conn.setAutoCommit(false);
			daoReserva.setConn(conn);
			daoApartamento.setConn(conn);
			
			daoReserva.addReserva(reserva, idCliente);

			if(daoApartamento.estaOcupado(idApartamento))
			{
				throw new Exception("El apartamento está ocupado");
			}
			
			
			
			Cliente cliente = getClienteById(idCliente); 
			
			
			conn.commit();
				


		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			conn.rollback();
			throw sqlException;
			
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			conn.rollback();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				daoApartamento.cerrarRecursos();
				
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Metodo que modela la transaccion que hace un contrato de una vivienda. <br/>
	 * <b> post: </b> se ha hecho la reserva <br/>
	 * @param hotel - la reserva. reserva != null
	 * @throws Exception - Cualquier error que se genere agregando el hotel
	 */
	public void addReservaVivienda(Reserva reserva, int idCliente, int idVivienda) throws Exception 
	{

		DAOReserva daoReserva = new DAOReserva( );
		DAOVivienda daoVivienda = new DAOVivienda(); 

		try
		{
			this.conn = darConexion(); 

			daoReserva.setConn(conn);
			daoVivienda.setConn(conn);
			conn.setAutoCommit(false);
			
			Cliente cliente = getClienteById(idCliente); 
		

			if(daoVivienda.estaOcupada(idVivienda))
			{
				throw new Exception("La vivienda está ocupada");
			}


			daoReserva.addReserva(reserva, idCliente);
			
			


		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			conn.rollback();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				daoVivienda.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que hace una reserva . <br/>
	 * <b> post: </b> se ha hecho la reserva <br/>
	 * @param hotel - la reserva. reserva != null
	 * @throws Exception - Cualquier error que se genere agregando el hotel
	 */
	public void addReservaHabitacion(Reserva reserva, int idCliente, int idHabitacion) throws Exception 
	{

		DAOReserva daoReserva = new DAOReserva( );
		DAOHabitacion daoHabitacion = new DAOHabitacion(); 

		try
		{
			this.conn = darConexion(); 

			Cliente cliente = getClienteById(idCliente); 


			daoReserva.setConn(conn);
			daoHabitacion.setConn(conn);
			if(daoHabitacion.estaOcupada(idHabitacion))
			{
				throw new Exception("La habitación está ocupada");
			}
			
			daoReserva.addReserva(reserva, idCliente);
			daoHabitacion.reservar(idHabitacion);
			
			System.out.println("Reserva completada para la habitación con id:  " + idHabitacion);
			
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace(); 
			System.out.println("Reserva fallida para la habitación con id:  " + idHabitacion);
			conn.rollback();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			System.out.println("Reserva fallida para la habitación con id:  " + idHabitacion);
			conn.rollback(); 
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				daoHabitacion.cerrarRecursos(); 
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que agrega un hotel/hostal a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el operador que entra como parametro <br/>
	 * @param hotel - el hotel a agregar. hotel != null
	 * @throws Exception - Cualquier error que se genere agregando el hotel
	 */
	public void addHotel(Hotel hotel) throws Exception 
	{

		DAOHotel daoHotel = new DAOHotel( );
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		

		List<Habitacion> habitaciones = hotel.getHabitaciones(); 




		try
		{

			this.conn = darConexion(); 
			conn.setAutoCommit(false);
			daoHotel.setConn(conn);
			daoHabitacion.setConn(conn);
			daoHotel.addHotel(hotel);

			for (Habitacion habitacion : habitaciones) {

				daoHabitacion.addHabitacionHotel(habitacion, hotel.getId());
			}



			conn.commit();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			conn.rollback();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			conn.rollback();
			throw exception;
		} 
		finally {
			try {
				daoHotel.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addVivienda(int operadorId, Vivienda vivienda) throws Exception 
	{

		DAOVivienda daoVivienda = new DAOVivienda( );
		try
		{
			this.conn = darConexion(); 
			daoVivienda.setConn(conn);

			PersonaOperador operador = getOperadorById(operadorId); 


			daoVivienda.addVivienda(operadorId, vivienda);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoVivienda.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que agrega una habitación de un respectivo operador a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el operador que entra como parametro <br/>
	 * @param bebedor - el operador a agregar. bebedor != null
	 * @throws Exception - Cualquier error que se genere agregando el bebedor
	 */
	public void addHabitacionHotel(Habitacion habitacion, int id) throws Exception 
	{

		DAOHabitacion daoHabitacion = new DAOHabitacion( );
		try
		{
			this.conn = darConexion(); 
			daoHabitacion.setConn(conn);

			daoHabitacion.addHabitacionHotel(habitacion, id);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Metodo que modela la transaccion que agrega una habitación de un respectivo operador a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el operador que entra como parametro <br/>
	 * @param bebedor - el operador a agregar. bebedor != null
	 * @throws Exception - Cualquier error que se genere agregando el bebedor
	 */
	public void addHabitacionPersona(Habitacion habitacion, int idPersona) throws Exception 
	{

		DAOHabitacion daoHabitacion = new DAOHabitacion( );
		DAOServicios daoServicios = new DAOServicios();

		try
		{
			this.conn = darConexion(); 
			daoHabitacion.setConn(conn);
			daoServicios.setConn(conn);

			if(getOperadorById(idPersona) == null)
			{
				throw new Exception("El operador del alojamiento no está registrado");
			}

			daoHabitacion.addHabitacionPersona(habitacion, idPersona );
			daoServicios.addServicios(habitacion.getServicios(), habitacion.getId());

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHabitacion.cerrarRecursos();
				daoServicios.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Metodo que modela la transaccion que agrega un apto de un respectivo operador a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el operador que entra como parametro <br/>
	 * @param apto - el apto a agregar. apartamento != null
	 * @param apto - el id del operador a agregar. apartamento != null
	 * @throws Exception - Cualquier error que se genere agregando el apartemento
	 */
	public void addApartamento(Apartamento apto, int idPersona) throws Exception 
	{

		DAOApartamento daoApto = new DAOApartamento( );
		try
		{
			this.conn = darConexion(); 
			daoApto.setConn(conn);

			PersonaOperador operador = getOperadorById(idPersona);

			if( operador == null)
			{
				throw new Exception("El operador del alojamiento no está registrado");
			}



			daoApto.addApartamento(idPersona, apto);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoApto.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}







	public void addServicios()
	{


	}
	//-----------------------------------------------
	// METODOS GET
	//-----------------------------------------------
	/**
	 * Metodo que modela la transaccion que retorna todos los bebedores de la base de datos. <br/>
	 * @return List<Bebedor> - Lista de bebedores que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Cliente> getAllClientes() throws Exception {
		DAOCliente daoBebedor = new DAOCliente();
		List<Cliente> clientes;
		try 
		{
			this.conn = darConexion();
			daoBebedor.setConn(conn);


			clientes = daoBebedor.getClientes();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoBebedor.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los bebedores de la base de datos. <br/>
	 * @return List<Bebedor> - Lista de bebedores que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<PersonaOperador> getAllOperadores() throws Exception {
		DAOOperador daoOperador = new DAOOperador();
		List<PersonaOperador> operadores;
		try 
		{
			this.conn = darConexion();
			daoOperador.setConn(conn);


			operadores = daoOperador.getAllOperadores();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return operadores;
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los bebedores de la base de datos. <br/>
	 * @return List<Bebedor> - Lista de bebedores que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Hotel> getAllHoteles() throws Exception {
		DAOHotel daoHotel = new DAOHotel();
		List<Hotel> hoteles;
		try 
		{
			this.conn = darConexion();
			daoHotel.setConn(conn);


			hoteles = daoHotel.getHoteles();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHotel.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return hoteles;
	}

	public List<Vivienda> getAllViviendas() throws Exception {
		DAOVivienda daoVivienda = new DAOVivienda();
		List<Vivienda> vivienda;
		try 
		{
			this.conn = darConexion();
			daoVivienda.setConn(conn);


			vivienda = daoVivienda.getViviendas();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoVivienda.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return vivienda;
	}


	public List<Apartamento> getAllApartamentos() throws Exception {
		DAOApartamento daoVivienda = new DAOApartamento();
		List<Apartamento> vivienda;
		try 
		{
			this.conn = darConexion();
			daoVivienda.setConn(conn);


			vivienda = daoVivienda.getAllAptos();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoVivienda.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return vivienda;
	}

	/**
	 * Metodo que modela la transaccion que busca el cliente en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del cliente a buscar. id != null
	 * @return Cliente - cliente que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Cliente getClienteById(int id) throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		Cliente cliente = null;
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			cliente = daoCliente.findClienteById(id);
			if(cliente == null)
			{
				throw new Exception("El cliente con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cliente;
	}

	/**
	 * Metodo que modela la transaccion que busca el apartamento en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del cliente a buscar. id != null
	 * @return Apto - apartamento que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Apartamento getApartamentoById(int id) throws Exception {
		DAOApartamento daoApto = new DAOApartamento();
		Apartamento apto = null;
		try 
		{
			this.conn = darConexion();
			daoApto.setConn(conn);
			apto = daoApto.findAptoByid(id);

			if(apto == null)
			{
				throw new Exception("El cliente con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoApto.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return apto;
	}

	/**
	 * Metodo que modela la transaccion que busca la vivienda en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param id -id de la vivienda a buscar. id != null
	 * @return vivienda - vivienda que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Vivienda getViviendaById(int id) throws Exception {
		DAOVivienda daoApto = new DAOVivienda();
		Vivienda vivienda = null;
		try 
		{
			this.conn = darConexion();
			daoApto.setConn(conn);
			vivienda = daoApto.findViviendaById(id);

			if(vivienda == null)
			{
				throw new Exception("El cliente con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoApto.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return vivienda;
	}

	/**
	 * Metodo que modela la transaccion que busca la Habitacion en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param id -id de la habitación a buscar. id != null
	 * @return Habitacion - Habitacion que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Habitacion getHabitacionById(int id) throws Exception {
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		Habitacion habitacion = null;
		try 
		{
			this.conn = darConexion();
			daoHabitacion.setConn(conn);
			habitacion = daoHabitacion.findHabitacionById(id);

			if(habitacion == null)
			{
				throw new Exception("El cliente con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return habitacion;
	}


	/**
	 * Metodo que modela la transaccion que busca el cliente en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del bebedor a buscar. id != null
	 * @return Bebedor - Bebedor que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Reserva> getReservasClientes(int id) throws Exception {
		DAOReserva daoReserva = new DAOReserva();
		List<Reserva> reservas = null;
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			reservas = daoReserva.getReservasCliente(id);
			if(reservas == null)
			{
				throw new Exception("El cliente con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reservas;
	}


	/**
	 * Metodo que modela la transaccion que busca el operador en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del operador a buscar. id != null
	 * @return Bebedor - operador que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public PersonaOperador getOperadorById(int id) throws Exception {
		DAOOperador daoOperador = new DAOOperador();
		PersonaOperador operador = null;
		try 
		{
			this.conn = darConexion();
			daoOperador.setConn(conn);
			operador = daoOperador.findOperadorById(id);
			if(operador == null)
			{
				throw new Exception("El operador con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return operador;
	}



	//-------------------------------------------------------------
	//METODOS DELETE
	//--------------------------------------------------------------





	/**
	 * Metodo que modela la transaccion que elimina de la base de datos al bebedor que entra por parametro. <br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el bebedor que entra por parametro <br/>
	 * @param Hotel - hotel a eliminar. hotel != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public void deleteHotel(Hotel hotel) throws Exception 
	{
		DAOHotel daoHotel = new DAOHotel( );
		try
		{
			this.conn = darConexion();
			daoHotel.setConn( conn );

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHotel.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}


	/**
	 * Metodo que modela la transaccion que elimina de la base de datos a la vivienda que entra por parametro. <br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el bebedor que entra por parametro <br/>
	 * @param Hotel - hotel a eliminar. hotel != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public void deleteVivienda(int id) throws Exception 
	{
		DAOVivienda daoVivienda = new DAOVivienda( );
		try
		{
			this.conn = darConexion();
			daoVivienda.setConn( conn );

			daoVivienda.deleteVivienda(id);



		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoVivienda.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	/**
	 * Metodo que modela la transaccion que elimina de la base de datos al bebedor que entra por parametro. <br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el bebedor que entra por parametro <br/>
	 * @param Hotel - hotel a eliminar. hotel != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public void deleteHabitacion(Habitacion habitacion) throws Exception 
	{
		DAOHabitacion daoHabitacion = new DAOHabitacion( );
		try
		{
			this.conn = darConexion();
			daoHabitacion.setConn( conn );

			daoHabitacion.deleteHabitacion(habitacion);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}



	/**
	 * Metodo que modela la transaccion que elimina de la base de datos al bebedor que entra por parametro. <br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el bebedor que entra por parametro <br/>
	 * @param Cliente - bebedor a eliminar. bebedor != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public void deleteCliente(Cliente bebedor) throws Exception 
	{
		DAOCliente daoBebedor = new DAOCliente( );
		try
		{
			this.conn = darConexion();
			daoBebedor.setConn( conn );



		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoBebedor.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}


	/**
	 * Metodo que modela la transaccion que elimina de la base de datos al bebedor que entra por parametro. <br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el bebedor que entra por parametro <br/>
	 * @param Cliente - bebedor a eliminar. bebedor != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public void deleteReserva(int idReserva) throws Exception 
	{
		DAOReserva daoReserva = new DAOReserva( );
		try
		{
			this.conn = darConexion();
			daoReserva.setConn( conn );
			daoReserva.cancelarReserva(idReserva);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}


	//---------------------------------------------------------//---------------------------------------------------------
	//								METODOS DE MODIFICACION MASIVA
	//---------------------------------------------------------//---------------------------------------------------------



	/**
	 * Metodo que modela la transaccion que agrega reservas colectivas <br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el bebedor que entra por parametro <br/>
	 * @param Cliente - bebedor a eliminar. bebedor != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public void agregarReservaColectiva(ReservaColectiva reservaColectiva) throws Exception 
	{
		DAOReserva daoReserva = new DAOReserva( );           
		DAOHabitacion daoHabitacion = new DAOHabitacion(); 
	
		
		try
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoHabitacion.setConn(conn);
			conn.setAutoCommit(false);
			
			String condicion = "DESCRIPCION = " + reservaColectiva.getDescripcion();
			Servicios serviciosDeseados = reservaColectiva.getServicios(); 
			
			List<Habitacion> habitacionesDescripcion = daoHabitacion.getHabitacionesCondicion(condicion); 
			ArrayList<Habitacion> habitacionesValidas = new ArrayList<>();
			
			for (int i = 0; i < habitacionesDescripcion.size(); i++) 
			{
				
				Habitacion habActual = habitacionesDescripcion.get(i); 
				Servicios sevicioActual = habitacionesDescripcion.get(i).getServicios();
				
				if(serviciosDeseados.cumpleCondiciones(sevicioActual) && !daoHabitacion.estaOcupada(habActual.getId())	)
				{
					habitacionesValidas.add(habActual);
				}
				
			}
			
			if(habitacionesValidas.size() < reservaColectiva.getNumeroReservas())
			{	conn.rollback(); 
				throw new Exception("No hay suficientes habitaciones que cumplan los requisitos");
			}
			
			for(int i = 0; i < reservaColectiva.getNumeroReservas(); i++)
			{
				
				System.out.println("Comienzo de la transacción de reserva individual de la habitación " + habitacionesValidas.get(i).getId());
				addReservaHabitacion(reservaColectiva.getReserva(), 0, habitacionesValidas.get(i).getId());
			
			}
			
			System.out.println("Transacción de reserva masiva exitosa");
			
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			System.out.println("Transacción de reserva masiva fallida");
			conn.rollback();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			System.out.println("Transacción de reserva masiva fallida");
			conn.rollback(); 
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}
	
	

	/**
	 * Metodo que modela la transaccion que elimina de la base de datos las reservascolectivas con el id dado <br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el bebedor que entra por parametro <br/>
	 * @param Cliente - bebedor a eliminar. bebedor != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public void deleteReservaColectiva(Servicios servicio, int numeroReservas) throws Exception 
	{
		DAOReserva daoReserva = new DAOReserva( );           
		DAOHabitacion daoHabitacion = new DAOHabitacion(); 
		
		
		
		try
		{
			this.conn = darConexion();
			

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				daoHabitacion.cerrarRecursos(); 
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}
	







}
