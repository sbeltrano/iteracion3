package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohaTransactionManager;
import vos.Apartamento;
import vos.ContratoApto;
import vos.ContratoHabitacion;
import vos.ContratoVivienda;
import vos.Hotel;
import vos.Reserva;
import vos.ReservaColectiva;
import vos.Respuesta;
import vos.Servicios;

@Path ("reservas")
public class ReservaService {

	
			//----------------------------------------------------------------------------------------------------------------------------------
			// ATRIBUTOS
			//----------------------------------------------------------------------------------------------------------------------------------
			
			/**
			 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
			 */
			@Context
			private ServletContext context;

			//----------------------------------------------------------------------------------------------------------------------------------
			// METODOS DE INICIALIZACION
			//----------------------------------------------------------------------------------------------------------------------------------
			/**
			 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
			 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
			 */
			private String getPath() {
				return context.getRealPath("WEB-INF/ConnectionData");
			}


			private String doErrorMessage(Exception e){
				return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
			}

			//----------------------------------------------------------------------------------------------------------------------------------
			// METODOS REST
			//----------------------------------------------------------------------------------------------------------------------------------
	
	
	
			/**
			 * Metodo Post Que hace una reserva a un apartamento. <br/>
			 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>URL: </b> http://localhost:8080/Iteracion1/rest/reservas/{{idcliente}}/apartamento/{{idapartamento}} <br/>
			 * @return	<b>Response Status 200</b> - JSON que contiene la reserva  <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */			
			@POST
			@Path("/{idcliente: \\d+}/apartamento/{idapartamento: \\d+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response postContratoApto(ContratoApto reserva,@PathParam("idcliente") int idcliente, @PathParam("idapartamento")int idapartamento) {
				
				try {
					System.out.println("entra al post disponibilidad " + reserva.getFechaFinal());
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					
					
					tm.addReservaApartamento(reserva, idcliente, idapartamento,0);
					
					
					return Response.status(200).entity(reserva).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
			
			

			/**
			 * Metodo Post Que hace una reserva a una habitacion . <br/>
			 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>URL: </b> http://localhost:8080/Iteracion1/rest/alojamientos/{{id}}/hotel <br/>
			 * @return	<b>Response Status 200</b> - JSON que contiene al cliente  <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */			
			@POST
			@Path("{idcliente: \\d+}/habitacion/{idhabitacion: \\d+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response postContratHabitacion(ContratoHabitacion reserva,@PathParam("idcliente") int idCliente, @PathParam("idhabitacion")int idhabitacion) 
			{
				
				try {
					System.out.println("entra al post disponibilidad " + reserva.getFechaFinal());
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					
					
					tm.addReservaHabitacion(reserva, idCliente, idhabitacion,0);
					
					
					return Response.status(200).entity(reserva).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
	
	

			/**
			 * Metodo Post Que hace una reserva a una vivienda . <br/>
			 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>URL: </b> http://localhost:8080/Iteracion1/rest/alojamientos/{{id}}/hotel <br/>
			 * @return	<b>Response Status 200</b> - JSON que contiene al cliente  <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */			
			@POST
			@Path("{idcliente: \\d+}/vivienda/{idvivienda: \\d+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response postContratoVivienda(ContratoVivienda reserva,@PathParam("idcliente") int idCliente, @PathParam("idvivienda")int idvivienda) 
			{
				
				try {
					System.out.println("entra al post disponibilidad " + reserva.getFechaFinal());
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					tm.addReservaVivienda(reserva, idCliente, idvivienda,0);
					
					
					return Response.status(200).entity(reserva).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
			
			

			/**
			 * Metodo Post Que hace una reserva colectiva. <br/>
			 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>URL: </b> http://localhost:8080/Iteracion1/rest/alojamientos/{{id}}/hotel <br/>
			 * @return	<b>Response Status 200</b> - JSON que contiene al cliente  <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */			
			@POST
			@Path("/colectiva")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response postReservaColectiva (ReservaColectiva reservaColectiva) 
			{
				
				try {	
						
				
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					Respuesta respuesta = tm.agregarReservaColectiva(reservaColectiva);
						System.out.println(respuesta);
						return Response.status(200).entity(respuesta).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
			
			
			/**
			 * Metodo get que obtiene todas las reservas de un cliente . <br/>
			 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>URL: </b> http://localhost:8080/Iteracion1/rest/reservas/{{idcliente}} <br/>
			 * @return	<b>Response Status 200</b> - JSON que contiene las reserva  <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */			
			@GET
			@Path("/{idcliente: \\d+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response getReservasCliente(@PathParam("idcliente") int idcliente) {
				
				try {
					
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					
					List<Reserva> reservas = tm.getReservasClientes(idcliente);
					
					
					return Response.status(200).entity(reservas).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}


			/**
			 * Metodo que elimina una reserva . <br/>
			 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>URL: </b> http://localhost:8080/Iteracion1/rest/reservas/{{idreserva}} <br/>
			 * @return	<b>Response Status 200</b> - JSON que contiene la reserva  <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */		
			@DELETE
			@Path("{id: \\d+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response deleteReserva(@PathParam("id") int id) {
				
				try {
					
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					
					tm.deleteReserva(id, false);
					
					return Response.status(200).entity(id).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
	
			
			/**
			 * Metodo que elimina una reserva . <br/>
			 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>URL: </b> http://localhost:8080/Iteracion1/rest/reservas/{{idreserva}} <br/>
			 * @return	<b>Response Status 200</b> - JSON que contiene la reserva  <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */		
			@DELETE
			@Path("/colectiva/{id: \\d+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response deleteReservaTrue(@PathParam("id") int id) {	
				
				try {
					System.out.println("Entra al service de delete");
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					
					tm.deleteReservaColectiva(id);
					
					return Response.status(200).entity(id).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
	
			
			
			
			


}
