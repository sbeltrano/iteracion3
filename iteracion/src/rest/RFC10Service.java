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
import vos.RFC10;
import vos.RFC2;
import vos.RFC4;
import vos.RFC8;
import vos.RFC9;
import vos.Reserva;
import vos.ReservaColectiva;
import vos.Servicios;

@Path ("RFC10")
public class RFC10Service {

	
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
			@Path("/carnet/fechaInicial/{fechaInicial: .+}/fechaFinal/{fechaFinal: .+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response getRFC10Carnet(@PathParam("fechaInicial") String fechaInicial, @PathParam("fechaFinal")String fechaFinal) {
				
				try {
					
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					
					List<RFC10> rfc4 = tm.rfc10Carnet(fechaInicial, fechaFinal);
					
					
					return Response.status(200).entity(rfc4).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
			@GET
			@Path("/nombre/fechaInicial/{fechaInicial: .+}/fechaFinal/{fechaFinal: .+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response getRFC10Nombre(@PathParam("fechaInicial") String fechaInicial, @PathParam("fechaFinal")String fechaFinal) {
				
				try {
					
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					
					List<RFC10> rfc4 = tm.rfc10Nombre(fechaInicial, fechaFinal);
					
					
					return Response.status(200).entity(rfc4).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
			
			@GET
			@Path("/rol/fechaInicial/{fechaInicial: .+}/fechaFinal/{fechaFinal: .+}")
			@Consumes({ MediaType.APPLICATION_JSON })
			@Produces({ MediaType.APPLICATION_JSON })
			public Response getRFC10Rol(@PathParam("fechaInicial") String fechaInicial, @PathParam("fechaFinal")String fechaFinal) {
				
				try {
					
					AlohaTransactionManager tm = new AlohaTransactionManager(getPath());
					
					List<RFC10> rfc4 = tm.rfc10Rol(fechaInicial, fechaFinal);
					
					
					return Response.status(200).entity(rfc4).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}
			


			

}
