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
import vos.RFC1;
import vos.RFC12;
import vos.RFC12b;
import vos.RFC2;
import vos.RFC4;
import vos.RFC8;
import vos.RFC9;
import vos.Reserva;
import vos.ReservaColectiva;
import vos.Servicios;

@Path ("RFC12")
public class RFC12Service {

	
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
			 * Metodo get que obtiene todas las reservas de un cliente . <br/>
			 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>URL: </b> http://localhost:8080/Iteracion1/rest/reservas/{{idcliente}} <br/>
			 * @return	<b>Response Status 200</b> - JSON que contiene las reserva  <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */			
			@GET
			@Path("/alojamientos/max/year/{yearId: .+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response getAlojamientosDisponiblesEnRangoFechasApartamentos(@PathParam("yearId") int yearId) {
				
				try {
					
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					
					List<RFC12> rfc4 = tm.rfc12Max(yearId);
					
					
					return Response.status(200).entity(rfc4).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
			
			@GET
			@Path("/alojamientos/min/year/{yearId: .+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response getAlojamientosDisponiblesEnRangoFechasApartamentosde(@PathParam("yearId") int yearId) {
				
				try {
					
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					
					List<RFC12> rfc4 = tm.rfc12Min(yearId);
					
					
					return Response.status(200).entity(rfc4).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
			
			@GET
			@Path("/operadores/min/year/{yearId: .+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response RFC12(@PathParam("yearId") int yearId) {
				
				try {
					
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					
					List<RFC12b> rfc4 = tm.rfc12MinOperadores(yearId);
					
					
					return Response.status(200).entity(rfc4).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
			
			@GET
			@Path("/operadores/max/year/{yearId: .+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response RFC12maxop(@PathParam("yearId") int yearId) {
				
				try {
					
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					
					List<RFC12b> rfc4 = tm.rfc12MaxOperadores(yearId);
					
					
					return Response.status(200).entity(rfc4).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
}
