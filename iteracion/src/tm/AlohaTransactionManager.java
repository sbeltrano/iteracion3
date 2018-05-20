/**-------------------------------------------------------------------
 * ISIS2304 - Sistemas Transaccionales
 * Departamento de Ingenieria de Sistemas
 * Universidad de los Andes
 * Bogota, Colombia
 * 
 * Alohandes 
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
import dao.DAORFC1;
import dao.DAORFC10;
import dao.DAORFC11;
import dao.DAORFC12;
import dao.DAORFC13;
import dao.DAOReserva;
import dao.DAOServicios;
import dao.DAOVivienda;
import vos.Apartamento;
import vos.Cliente;
import vos.Comunidad;
import vos.ContratoApto;
import vos.ContratoHabitacion;
import vos.ContratoVivienda;
import vos.Habitacion;
import vos.Hotel;
import vos.PersonaOperador;
import vos.RFC1;
import vos.RFC10;
import vos.RFC11;
import vos.RFC12;
import vos.RFC13;
import vos.Reserva;
import vos.ReservaColectiva;
import vos.Respuesta;
import vos.Servicios;
import vos.Vivienda;
import vos.RFC1;
import vos.RFC2;
import vos.RFC3;
import vos.RFC4;
import vos.RFC5y6;
import vos.RFC7;
import vos.RFC8;
import vos.RFC9;
import dao.DAORFC1;
import dao.DAORFC2;
import dao.DAORFC3;
import dao.DAORFC4;
import dao.DAORFC5y6;
import dao.DAORFC7;
import dao.DAORFC8;
import dao.DAORFC9;

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
	public void addReservaApartamento(Reserva reserva, int idCliente, int idApartamento, int colectiva) throws Exception 
	{

		DAOReserva daoReserva = new DAOReserva( );
		DAOApartamento daoApartamento = new DAOApartamento(); 


		try
		{

			daoReserva.setConn(conn);
			daoApartamento.setConn(conn);



			if(daoApartamento.estaOcupado(idApartamento))
			{
				throw new Exception("El apartamento está ocupado");
			}


			//Cliente cliente = getClienteById(idCliente); 


			daoReserva.addReserva(reserva, idCliente, colectiva);
			daoApartamento.ocupar(idApartamento, reserva.getId());





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

				if(this.conn!=null && colectiva == 0){
					conn.close();		
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
	public void addReservaVivienda(Reserva reserva, int idCliente, int idVivienda, int colectiva) throws Exception 
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


			daoReserva.addReserva(reserva, idCliente, colectiva);
			daoVivienda.reservar(idVivienda);



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
	public void addReservaHabitacion(Reserva reserva, int idCliente, int idHabitacion, int colectiva) throws Exception 
	{

		DAOReserva daoReserva = new DAOReserva( );
		DAOHabitacion daoHabitacion = new DAOHabitacion(); 


		try
		{

			this.conn = darConexion();

			daoReserva.setConn(conn);
			daoHabitacion.setConn(conn);


			System.out.println("Despues de darconexion");
			Cliente cliente = getClienteById(idCliente); 




			System.out.println("despues de addreserva");

			if(daoHabitacion.estaOcupada(idHabitacion))
			{
				throw new Exception("La habitación está ocupada");
			}

			daoReserva.addReserva(reserva, idCliente, colectiva);
			daoHabitacion.reservar(idHabitacion,reserva.getId());


			if(colectiva == 0)
			{
				darConexion().commit();
			}


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


	public Hotel getHotelById(int id) throws Exception {
		DAOHotel daoHabitacion = new DAOHotel();
		Hotel habitacion = null;
		try 
		{
			this.conn = darConexion();
			daoHabitacion.setConn(conn);
			habitacion = daoHabitacion.findHotelById(id);

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

	public Servicios getServicioByHabitacionId(int idHabitacion) throws Exception {
		DAOServicios daoServicios = new DAOServicios();
		Servicios servicio = null;
		System.out.println("Entra al getServicioByhabitcion");
		try 
		{
			this.conn = darConexion();
			daoServicios.setConn(conn);
			servicio = daoServicios.getServicioHabitacion(idHabitacion);

			if(servicio == null)
			{
				throw new Exception("El servicio con el id de habitacion = " + idHabitacion + " no se encuentra persistido en la base de datos.");				
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
		return servicio;
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


	public ArrayList<Reserva> getReservasHotel(int idHotel) throws Exception {
		DAOReserva daoReserva = new DAOReserva();
		DAOHabitacion daoHabitacion = new DAOHabitacion(); 
		ArrayList<Reserva> reservas = new ArrayList<>();

		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoHabitacion.setConn(conn);

			ArrayList<Integer> reservasId = daoHabitacion.getReservasId(idHotel); 

			for (Integer integer : reservasId) {

				String condicion = "RESERVAID = " + integer; 
				reservas.add(daoReserva.getReservaCondicion(condicion)); 

			}


			if(reservas.size() == 0 )
			{
				throw new Exception("No hay reservas para esa habitacion");				
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
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();		
					return reservas;
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


	public List<Reserva> getReservasColectivaByID(int idColectivo) throws Exception {
		DAOReserva daoReserva = new DAOReserva();
		List<Reserva> reservas = null;
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(true);
			daoReserva.setConn(conn);
			reservas = daoReserva.getReservasColectiva(idColectivo);

			if(reservas.size() == 0 || reservas == null)
			{
				throw new Exception("No hay reservas con este id");				
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


	public boolean habitacionEstaOcupada(int id) throws Exception {
		DAOHabitacion daoHab = new DAOHabitacion();
		boolean retorno = false; 
		try 
		{
			this.conn = darConexion();
			daoHab.setConn(conn);
			retorno = daoHab.estaOcupada(id);

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
				daoHab.cerrarRecursos();
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
		return retorno;
	}

	public ArrayList<Habitacion> getHabitacionesCondicion(String condicion) throws Exception {
		DAOHabitacion daoHab = new DAOHabitacion();
		ArrayList<Habitacion> retorno = new ArrayList<>(); 
		try 
		{
			this.conn = darConexion();
			daoHab.setConn(conn);
			retorno = daoHab.getHabitacionesCondicion(condicion);

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
				daoHab.cerrarRecursos();
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
		return retorno;
	}

	public ArrayList<Apartamento> getAptosCondicion(String condicion) throws Exception {
		DAOApartamento daoApto = new DAOApartamento();
		ArrayList<Apartamento> retorno = new ArrayList<>(); 
		try 
		{
			this.conn = darConexion();
			daoApto.setConn(conn);
			retorno = daoApto.getAptosCondicion(condicion);

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
		return retorno;
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
	public void deleteReserva(int idReserva, boolean reservaColectiva) throws Exception 
	{


		DAOReserva daoReserva = new DAOReserva( );

		try
		{		

			daoReserva.setConn( conn );

			if(!reservaColectiva)
			{

				conn.setAutoCommit(true);
			}


			daoReserva.cancelarReserva(idReserva);


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
				if(this.conn!=null && !reservaColectiva){
					System.out.println("Cierra la conexion en delete reserva");
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
	public Respuesta agregarReservaColectiva(ReservaColectiva reservaColectiva) throws Exception 
	{



		DAOVivienda daoVivienda = new DAOVivienda();
		DAOServicios daoServicios = new DAOServicios(); 
		Respuesta respuesta = new Respuesta(""); 


		try
		{


			String descripcion = reservaColectiva.getDescripcion();
			Servicios serviciosDeseados = reservaColectiva.getServicios(); 
			Reserva reserva = reservaColectiva.getReserva(); 
			ContratoApto contratoApto = reservaColectiva.getContratoApto(); 

			//
			//Si la reserva es para una habitacion de un apartamento
			//
			if(reserva instanceof ContratoHabitacion)
			{




			}
			//
			// Si la reserva es de un apartamento
			//
			else if(contratoApto != null)
			{
				respuesta.agregar("Empieza reserva colectiva de apartamentos" );


				respuesta.agregar("Busca los apartamentos con la condicion requerida" );
				//La descripcion puede ser AMOBLADO O NO AMOBLADO
				String condicion = descripcion.equals("AMOBLADO") ? "AMOBLADO = 1" : "AMOBLADO = 0";

				condicion+= "AND OCUPADO = 0"; 


				ArrayList<Apartamento> aptosValidos = getAptosCondicion(condicion);


				if(aptosValidos.size() < reservaColectiva.getNumeroReservas())
				{	
					respuesta.agregar("No hay suficientes apartamentos con el requisito dado: " + aptosValidos.size() + " validos , " +  reservaColectiva.getNumeroReservas() + " necesarios");
					return respuesta; 		
				}



				int id = contratoApto.getId();
				this.conn = darConexion(); 
				conn.setAutoCommit(false);


				for(int i = 0; i < reservaColectiva.getNumeroReservas(); i++)
				{	

					respuesta.agregar("Comienzo de la transacción de reserva individual del apartamento con el id " + aptosValidos.get(i).getId());
					reservaColectiva.getContratoApto().setId(id);
					addReservaApartamento(contratoApto, reservaColectiva.getClienteId(), aptosValidos.get(i).getId(), reservaColectiva.getId());

					id++;
				}

				conn.commit(); 
				//Si se esta reservando un hotel
			}else if(reserva instanceof ContratoVivienda)
			{
				daoVivienda.setConn(conn);
				//TODO: Hacer el caso de contrato vivienda

				ContratoVivienda contratoViv = (ContratoVivienda) reserva; 

				String menaje = contratoViv.isMenaje() ? "1": "0"; 
				int numeroHab = contratoViv.getNumeroHabitaciones(); 


				String condicion = "MENAJE = " + menaje + " AND NUMEROHABITACIONES = " + numeroHab;

				ArrayList<Vivienda> viviendasCondicion = daoVivienda.getViviendasCondicion(condicion); 

				if(viviendasCondicion.size() < reservaColectiva.getNumeroReservas())
				{	conn.rollback(); 
				throw new Exception("No hay suficientes viviendas que cumplan los requisitos");
				}			

				for (int i = 0; i < reservaColectiva.getNumeroReservas(); i++) {

					addReservaVivienda(reserva, reservaColectiva.getClienteId(), viviendasCondicion.get(i).getId(), reservaColectiva.getId());

				}


			}
			else 
			{

				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoServicios.setConn(conn);

				respuesta.agregar("Comienza de una habitación con descripcion = " + descripcion);




				String condicion = String.format("DESCRIPCION = '%1$s'", descripcion); 

				respuesta.agregar("Revisa cuales habitaciones cumplen la descripcion de habitacion");
				List<Habitacion> habitacionesDescripcion = getHabitacionesCondicion(condicion); 
				ArrayList<Habitacion> habitacionesValidas = new ArrayList<>();

				respuesta.agregar("Revisa cuales habitaciones cumplen los requisitos de servicios");
				for (int i = 0; i < habitacionesDescripcion.size(); i++) 
				{


					Habitacion habActual = habitacionesDescripcion.get(i); 


					Servicios sevicioActual = daoServicios.getServicioHabitacion(habActual.getId()); 


					if(serviciosDeseados.cumpleCondiciones(sevicioActual) && !habitacionEstaOcupada(habActual.getId())	)
					{
						habitacionesValidas.add(habActual);
					}

				}



				if(habitacionesValidas.size() < reservaColectiva.getNumeroReservas())
				{	
					respuesta.agregar("Lanza excepcion porque no hay suficientes habitaciones que cumplan los requisitos");
					darConexion().rollback(); 

					return respuesta; 
				}


				int id = reserva.getId();
				//conn cerrada

				darConexion().commit(); 
				for(int i = 0; i < reservaColectiva.getNumeroReservas(); i++)
				{	

					respuesta.agregar("Comienzo de la transacción de reserva individual de la habitación " + habitacionesValidas.get(i).getId());
					reservaColectiva.getReserva().setId(id);
					addReservaHabitacion(reserva, reservaColectiva.getClienteId(), habitacionesValidas.get(i).getId(), reservaColectiva.getId());
					id++;
				}


			}


			darConexion().commit(); 
			respuesta.agregar("Reserva exitosa");
			return respuesta;

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			System.out.println("Transacción de reserva masiva fallida");
			respuesta.agregar("[EXCEPTION] SQLException:" + sqlException.getMessage()); respuesta.agregar("Transacción de reserva masiva fallida");
			darConexion().rollback();

			return respuesta;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			respuesta.agregar("[EXCEPTION] General Exception:" + exception.getMessage()); respuesta.agregar("Transacción de reserva masiva fallida");
			darConexion().rollback(); 
			return respuesta;
		} 
		finally {
			try {


				daoVivienda.cerrarRecursos(); 
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
	 * Metodo que modela la transaccion que elimina de la base de datos las reservas colectivas con el id dado <br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el bebedor que entra por parametro <br/>
	 * @param Cliente - bebedor a eliminar. bebedor != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public void deleteReservaColectiva(int reservaColectiva) throws Exception 
	{


		DAOApartamento daoApartamento = new DAOApartamento();
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		DAOReserva daoReserva = new DAOReserva( );

		try
		{

			List<Reserva> reservas = getReservasColectivaByID(reservaColectiva);

			this.conn = darConexion(); 
			conn.setAutoCommit(false);


			daoApartamento.setConn(conn);
			daoHabitacion.setConn(conn);

			for (Reserva reserva : reservas) {


				if (reserva instanceof ContratoApto)
				{

					daoApartamento.desocuparColectiva(reserva.getId());

				}else if(reserva instanceof ContratoVivienda)
				{

				}
				else
				{
					daoHabitacion.desocuparColectiva(reserva.getId());
				}		

			}			

			daoReserva.setConn(conn);
			daoReserva.cancelarReservaColectiva(reservas.get(0).getId());

			conn.commit();
			System.out.println("Exito");
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			darConexion().rollback();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			darConexion().rollback();
			throw exception;
		} 
		finally {
			try {

				daoApartamento.cerrarRecursos();
				daoHabitacion.cerrarRecursos();
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
	 * Metodo que modela la transaccion que elimina de la base de datos las reservas colectivas con el id dado <br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el bebedor que entra por parametro <br/>
	 * @param Cliente - bebedor a eliminar. bebedor != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public Respuesta desHabilitarHotel(int idHotel) throws Exception 
	{

		DAOReserva daoReserva = new DAOReserva( );           
		DAOHotel daoHotel = new DAOHotel(); 
		DAOHabitacion daoHabitacion = new DAOHabitacion(); 
		Respuesta respuesta = new Respuesta(""); 
		respuesta.agregar("Comienza transaccion para deshabilitar hotel");
		int i = 0;
		try
		{
			respuesta.agregar("Busca las habitacines del hotel");
			List<Habitacion>habitacionesHotel = getHabitacionesCondicion("HOTELERIAID = " + idHotel);
			respuesta.agregar("Busca las reservas de las habitaciones");

			List<Reserva> reservas  = getReservasHotel(idHotel);
			System.out.println("Tamaño reservas hotel = " + reservas.size());


			this.conn = darConexion();
			conn.setAutoCommit(true);	
			daoHotel.setConn(conn);

			respuesta.agregar("Deshabilita el hotel con id = " + idHotel);
			daoHotel.desHabilitarHotel(idHotel);

			daoHabitacion.setConn(conn);

			respuesta.agregar("Deshabilita la habitacion del hotel con id = " + idHotel);
			for (int j = 0; j < habitacionesHotel.size(); j++) {
				
				daoHabitacion.desHabilitarHabitacion(habitacionesHotel.get(j).getId());
			}
			
			for (i = 0; i< habitacionesHotel.size() && i< reservas.size(); i++ ) {

				
				
				int j = 0;	
				
				String condicion = String.format("DESCRIPCION = '%1$s' AND OCUPADA = 0 AND HABILITADO = 1", habitacionesHotel.get(i).getDescripcion()); 
				
				List<Habitacion> habitacionesCumplen = daoHabitacion.getHabitacionesCondicion(condicion);
				System.out.println("tamaño habitaciones cumplen = " + habitacionesCumplen.size());
				
				if(habitacionesCumplen.size() == 0)
				{
					respuesta.agregar("Ya no hay más habitaciones");
					return respuesta;
				}
				
				if(reservas.get(i) != null && habitacionesCumplen.get(j) != null)
				daoHabitacion.setReservaId(reservas.get(i).getId(), habitacionesCumplen.get(j).getId());



			}
			
			
			respuesta.agregar("No se pudo encontrar una habitacion dispobile para  las resservas:" );
			for (i = i ; i <reservas.size(); i++) {
				respuesta.agregar("" +reservas.get(i).getId());
			}
				
			
			
			
			daoReserva.setConn(conn);



			conn.commit(); 
			return respuesta;

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
	 * Metodo que modela la transaccion que elimina de la base de datos las reservas colectivas con el id dado <br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el bebedor que entra por parametro <br/>
	 * @param Cliente - bebedor a eliminar. bebedor != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public Respuesta reHabilitarHotel(int idHotel) throws Exception 
	{


		DAOHotel daoHotel = new DAOHotel(); 
		DAOHabitacion daoHabitacion = new DAOHabitacion(); 
		Respuesta respuesta = new Respuesta(""); 
		respuesta.agregar("Comienza transaccion para deshabilitar hotel");
		try
		{
			respuesta.agregar("Busca las habitacines del hotel");
			List<Habitacion>habitacionesHotel = getHabitacionesCondicion("HOTELERIAID = " + idHotel);
			respuesta.agregar("Busca las reservas de las habitaciones");

			this.conn = darConexion();
			conn.setAutoCommit(false);	
			daoHotel.setConn(conn);

			respuesta.agregar("reHabilita el hotel con id = " + idHotel);
			daoHotel.reHabilitarHotel(idHotel);

			daoHabitacion.setConn(conn);

			for (Habitacion habitacion : habitacionesHotel) {

				respuesta.agregar("reHabilita la habitacion del hotel con id = " + idHotel);
				daoHabitacion.reHabilitarHabitacion(habitacion.getId());
			}




			conn.commit(); 
			return respuesta;

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


	public void desHabilitarHabitacion(int idHabitacion) throws Exception 
	{

		DAOHabitacion daoHabitacion = new DAOHabitacion(); 

		
		try
		{

			this.conn = darConexion();
			conn.setAutoCommit(false);	

			daoHabitacion.setConn(conn);
			daoHabitacion.desHabilitarHabitacion(idHabitacion);

			conn.commit(); 

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

	public Respuesta reHabilitarHabitacion(int idHabitacion) throws Exception 
	{



		DAOHabitacion daoHabitacion = new DAOHabitacion(); 
		Respuesta respuesta = new Respuesta(""); 
		respuesta.agregar("Comienza transaccion para reHabilitar hotel");
		try
		{


			this.conn = darConexion();
			conn.setAutoCommit(false);	

			daoHabitacion.setConn(conn);
			respuesta.agregar("reHabilita la habitacion del hotel con id = " + idHabitacion);
			daoHabitacion.reHabilitarHabitacion(idHabitacion);

			conn.commit(); 
			return respuesta;

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

	//----------------------------------------------------------------------------------------------------------------------------------
	// REQUERIMIENTOS DE CONSULTA
	//----------------------------------------------------------------------------------------------------------------------------------


	//----------------------------------------------------------------------------------------------------------------------------------
		// REQUERIMIENTOS DE CONSULTA
		//----------------------------------------------------------------------------------------------------------------------------------


		/**
		 * - MOSTRAR EL DINERO RECIBIDO POR CADA PROVEEDOR DE ALOJAMIENTO DURANTE EL AÑO ACTUAL Y EL AÑO CORRIDO <br/>
		 * @param name -id del operador a buscar. id != null
		 * @return Bebedor - operador que se obtiene como resultado de la consulta.
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<RFC1> getDineroOperadores() throws Exception {
			DAORFC1 daoRFC1 = new DAORFC1();
			List<RFC1> reservasOperadores;
			try 
			{
				this.conn = darConexion();
				daoRFC1.setConn(conn);

				reservasOperadores = daoRFC1.getDineroOperadores();
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
					daoRFC1.cerrarRecursos();
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
			return reservasOperadores;
		}

		/**
		 * - MOSTRAR LAS 20 OFERTAS MÁS POPULARES <br/>
		 * @param name -id del operador a buscar. id != null
		 * @return Bebedor - operador que se obtiene como resultado de la consulta.
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<RFC2> get20PopularesApto() throws Exception {
			DAORFC2 daoRFC2 = new DAORFC2();
			List<RFC2> reservasOperadores;
			try 
			{
				this.conn = darConexion();
				daoRFC2.setConn(conn);

				reservasOperadores = daoRFC2.get20MasPopularesApto();
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
					daoRFC2.cerrarRecursos();
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
			return reservasOperadores;
		}
		
		public List<RFC2> get20PopularesHabitacion() throws Exception {
			DAORFC2 daoRFC2 = new DAORFC2();
			List<RFC2> reservasOperadores;
			try 
			{
				this.conn = darConexion();
				daoRFC2.setConn(conn);

				reservasOperadores = daoRFC2.get20MasPopularesHabitacion();
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
					daoRFC2.cerrarRecursos();
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
			return reservasOperadores;
		}
		
		public List<RFC2> get20PopularesVivienda() throws Exception {
			DAORFC2 daoRFC2 = new DAORFC2();
			List<RFC2> reservasOperadores;
			try 
			{
				this.conn = darConexion();
				daoRFC2.setConn(conn);

				reservasOperadores = daoRFC2.get20MasPopularesAptoVivienda();
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
					daoRFC2.cerrarRecursos();
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
			return reservasOperadores;
		}

		public List<RFC3> getIndiceOcupacionApto() throws Exception {
			DAORFC3 daoRFC3 = new DAORFC3();
			List<RFC3> reservasOperadores;
			try 
			{
				this.conn = darConexion();
				daoRFC3.setConn(conn);

				reservasOperadores = daoRFC3.getIndiceOcupamientoApto();
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
					daoRFC3.cerrarRecursos();
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
			return reservasOperadores;
		}
		
		public List<RFC3> getIndiceOcupacionHabitacion() throws Exception {
			DAORFC3 daoRFC3 = new DAORFC3();
			List<RFC3> reservasOperadores;
			try 
			{
				this.conn = darConexion();
				daoRFC3.setConn(conn);

				reservasOperadores = daoRFC3.getIndiceOcupamientoHabitacin();
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
					daoRFC3.cerrarRecursos();
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
			return reservasOperadores;
		}
		
		public List<RFC3> getIndiceOcupacionVivienda() throws Exception {
			DAORFC3 daoRFC3 = new DAORFC3();
			List<RFC3> reservasOperadores;
			try 
			{
				this.conn = darConexion();
				daoRFC3.setConn(conn);

				reservasOperadores = daoRFC3.getIndiceOcupamientoVivienda();
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
					daoRFC3.cerrarRecursos();
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
			return reservasOperadores;
		}
		
		/**
		 * - AlojamientosDisponiblesEnRangoDeFechas <br/>
		 * @param name -id del operador a buscar. id != null
		 * @return Bebedor - operador que se obtiene como resultado de la consulta.
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<RFC4> getAlojamientosDisponiblesEnRangoFechasApto(String fechaInicial, String fechaFinal) throws Exception {
			DAORFC4 daoRFC4 = new DAORFC4();
			List<RFC4> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC4.setConn(conn);

				alojamientos = daoRFC4.getAlojamientosPorFechaApartamento(fechaInicial,fechaFinal);
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
					daoRFC4.cerrarRecursos();
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
			return alojamientos;
		}

		public List<RFC4> getAlojamientosDisponiblesEnRangoFechasHabitacion(String fechaInicial, String fechaFinal) throws Exception {
			DAORFC4 daoRFC4 = new DAORFC4();
			List<RFC4> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC4.setConn(conn);

				alojamientos = daoRFC4.getAlojamientosPorFechaHabitacion(fechaInicial,fechaFinal);
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
					daoRFC4.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC4> getAlojamientosDisponiblesEnRangoFechasViviendas(String fechaInicial, String fechaFinal) throws Exception {
			DAORFC4 daoRFC4 = new DAORFC4();
			List<RFC4> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC4.setConn(conn);

				alojamientos = daoRFC4.getAlojamientosPorFechaVivienda(fechaInicial,fechaFinal);
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
					daoRFC4.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC5y6> getUsoAlohandesParaTipo(String tipoUsuario) throws Exception {
			DAORFC5y6 daoRFC5y6 = new DAORFC5y6();
			List<RFC5y6> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC5y6.setConn(conn);

				alojamientos = daoRFC5y6.usoAlohandesParaTipoUsuario(tipoUsuario);
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
					daoRFC5y6.cerrarRecursos();
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
			return alojamientos;
		}

		public List<RFC5y6> getUsoAlohandesParaUsuario(int usuarioId) throws Exception {
			DAORFC5y6 daoRFC5y6 = new DAORFC5y6();
			List<RFC5y6> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC5y6.setConn(conn);

				alojamientos = daoRFC5y6.usoAlohandesParaUsuario(usuarioId);
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
					daoRFC5y6.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC7> rfc7Apartamento(String fechaInicial,String fechaFinal) throws Exception {
			DAORFC7 daoRFC7 = new DAORFC7();
			List<RFC7> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC7.setConn(conn);

				alojamientos = daoRFC7.fechasMayorDemandaApartamento(fechaInicial, fechaFinal);
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
					daoRFC7.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC7> rfc7Habitacion(String fechaInicial,String fechaFinal) throws Exception {
			DAORFC7 daoRFC7 = new DAORFC7();
			List<RFC7> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC7.setConn(conn);

				alojamientos = daoRFC7.fechasMayorDemandaHabitacion(fechaInicial, fechaFinal);
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
					daoRFC7.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC7> rfc7Vivienda(String fechaInicial,String fechaFinal) throws Exception {
			DAORFC7 daoRFC7 = new DAORFC7();
			List<RFC7> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC7.setConn(conn);

				alojamientos = daoRFC7.fechasMayorDemandaVivienda(fechaInicial, fechaFinal);
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
					daoRFC7.cerrarRecursos();
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
			return alojamientos;
		}

		public List<RFC8> rfc8Apartamentos(int apartamentoId) throws Exception {
			DAORFC8 daoRFC8 = new DAORFC8();
			List<RFC8> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC8.setConn(conn);

				alojamientos = daoRFC8.rfc8Apartamentos(apartamentoId);
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
					daoRFC8.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC8> rfc8Habitaciones(int apartamentoId) throws Exception {
			DAORFC8 daoRFC8 = new DAORFC8();
			List<RFC8> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC8.setConn(conn);

				alojamientos = daoRFC8.rfc8Habitaciones(apartamentoId);
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
					daoRFC8.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC8> rfc8Viviendas(int apartamentoId) throws Exception {
			DAORFC8 daoRFC8 = new DAORFC8();
			List<RFC8> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC8.setConn(conn);

				alojamientos = daoRFC8.rfc8Viviendas(apartamentoId);
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
					daoRFC8.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC9> rfc9Viviendas() throws Exception {
			DAORFC9 daoRFC9 = new DAORFC9();
			List<RFC9> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC9.setConn(conn);

				alojamientos = daoRFC9.rfc8Viviendas();
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
					daoRFC9.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC9> rfc9Apartamentos() throws Exception {
			DAORFC9 daoRFC9 = new DAORFC9();
			List<RFC9> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC9.setConn(conn);

				alojamientos = daoRFC9.rfc9Apartamentos();
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
					daoRFC9.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC9> rfc9Habitaciones() throws Exception {
			DAORFC9 daoRFC9 = new DAORFC9();
			List<RFC9> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC9.setConn(conn);

				alojamientos = daoRFC9.rfc8Habitaciones();
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
					daoRFC9.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC13> rfc13suites() throws Exception {
			DAORFC13 daoRFC9 = new DAORFC13();
			List<RFC13> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC9.setConn(conn);

				alojamientos = daoRFC9.rfc13Suites();
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
					daoRFC9.cerrarRecursos();
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
			return alojamientos;
		}

		public List<RFC13> rfc13year(int year) throws Exception {
			DAORFC13 daoRFC9 = new DAORFC13();
			List<RFC13> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC9.setConn(conn);

				alojamientos = daoRFC9.rfc13Suites();
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
					daoRFC9.cerrarRecursos();
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
			return alojamientos;
		}

		public List<RFC12> rfc12(int year) throws Exception{
			DAORFC12 daoRFC9 = new DAORFC12();
			List<RFC12> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC9.setConn(conn);

				alojamientos = daoRFC9.rfc12(year);
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
					daoRFC9.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC10> rfc10Nombre(String fechaInicial,String fechaFinal) throws Exception {
			DAORFC10 daoRFC7 = new DAORFC10();
			List<RFC10> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC7.setConn(conn);

				alojamientos = daoRFC7.rfc10Nombre(fechaInicial, fechaFinal);
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
					daoRFC7.cerrarRecursos();
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
			return alojamientos;
		}

		public List<RFC10> rfc10Rol(String fechaInicial,String fechaFinal) throws Exception {
			DAORFC10 daoRFC7 = new DAORFC10();
			List<RFC10> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC7.setConn(conn);

				alojamientos = daoRFC7.rfc10Rol(fechaInicial, fechaFinal);
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
					daoRFC7.cerrarRecursos();
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
			return alojamientos;
		}

		public List<RFC10> rfc10Carnet(String fechaInicial,String fechaFinal) throws Exception {
			DAORFC10 daoRFC7 = new DAORFC10();
			List<RFC10> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC7.setConn(conn);

				alojamientos = daoRFC7.rfc10Carnet(fechaInicial, fechaFinal);
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
					daoRFC7.cerrarRecursos();
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
			return alojamientos;
		}
		
		public List<RFC11> rfc11Nombre(String fechaInicial,String fechaFinal) throws Exception {
			DAORFC11 daoRFC7 = new DAORFC11();
			List<RFC11> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC7.setConn(conn);

				alojamientos = daoRFC7.rfc11Nombre(fechaInicial, fechaFinal);
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
					daoRFC7.cerrarRecursos();
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
			return alojamientos;
		}

		public List<RFC11> rfc11Rol(String fechaInicial,String fechaFinal) throws Exception {
			DAORFC11 daoRFC7 = new DAORFC11();
			List<RFC11> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC7.setConn(conn);

				alojamientos = daoRFC7.rfc11Rol(fechaInicial, fechaFinal);
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
					daoRFC7.cerrarRecursos();
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
			return alojamientos;
		}

		public List<RFC11> rfc11Carnet(String fechaInicial,String fechaFinal) throws Exception {
			DAORFC11 daoRFC7 = new DAORFC11();
			List<RFC11> alojamientos;
			try 
			{
				this.conn = darConexion();
				daoRFC7.setConn(conn);

				alojamientos = daoRFC7.rfc11Carnet(fechaInicial, fechaFinal);
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
					daoRFC7.cerrarRecursos();
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
			return alojamientos;
		}

}
