package servicios;


import modelo.GestorConsultas;

import java.io.EOFException;
import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("tienda")
public class RecursoTienda {
    // Gestor de las operaciones que acceden al libro de comics
    GestorConsultas gestor = null;


    /**
     * Constructor por defecto
     */
    public RecursoTienda() {
        System.out.println("Construyo RecursoTienda");
        gestor = new GestorConsultas();
    }

    /**
     * Obtiene la lista de autores de comics en el catalogo
     * Responde a peticiones a la URI {baseURI}
     *
     * @return cuerpo conteniendo la cadena con informacion de los autores separados por ';'
     * @throws IOException 
     */
    /* FALTAN ANOTACIONES JAX-RS*/
	@GET
	@Produces("text/plain")
    public Response listaAutores() throws IOException {
        /* POR IMPLEMENTAR */
		
		if(gestor.listaAutores() == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
			StringBuilder response = new StringBuilder();
			for(String s : gestor.listaAutores()) {
				response.append(s + ";");
			}
			ResponseBuilder rBuilder = Response.ok(response.toString());
			
			return rBuilder.build();
		
        //return null; // A MODIFICAR
    }

    /**
     * Obtiene los datos de un comic dado su autor
     * Responde a peticiones a la URI {baseURI}/{autor}
     *
     * @param autor nombre del autor
     * @return cuerpo conteniendo la cadena con informacion de los comics separados por ';'
     * @throws IOException 
     * @throws EOFException 
     */
    /* FALTAN ANOTACIONES JAX-RS*/
	@GET
	@Path("{autor}")
	@Produces("text/plain")
    public Response buscaAutor(@PathParam("autor") String autor) throws EOFException, IOException {
        /* POR IMPLEMENTAR */
		if(gestor.buscaAutor(autor) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}else {
			ResponseBuilder rBuilder = Response.ok(gestor.buscaAutor(autor));
			return rBuilder.build();
		}
        //return null; // A MODIFICAR
    }

    /**
     * Compra un ejemplar de un comic dado su codigo
     * Responde a peticiones a la URI {baseURI}/{codigo}
     *
     * @param codigo codigo del comic
     * @return cuerpo conteniendo la cadena con los datos del comic
     * @throws IOException 
     */
    /* FALTAN ANOTACIONES JAX-RS*/
	@DELETE
	@Path("{codigo}")
    public Response compraComic(@PathParam("codigo") int codigo) throws IOException {
        /* POR IMPLEMENTAR */
		if(gestor.bajaEjemplar(codigo) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}else {
			gestor.bajaEjemplar(codigo);
			ResponseBuilder rBuilder = Response.ok(null);
			
		}
        return null; // A MODIFICAR
    }

    /**
     * Vende un ejemplar de un comic dado su codigo
     * Responde a peticiones a la URI {baseURI}/{codigo}
     *
     * @param codigo codigo del comic
     * @return cuerpo conteniendo la cadena con los datos del comic
     * @throws IOException 
     */
    /* FALTAN ANOTACIONES JAX-RS*/
	@PUT
	@Path("{codigo}")
    public Response vendeComic(@PathParam("codigo") int codigo) throws IOException {
        /* POR IMPLEMENTAR */
		if(gestor.bajaEjemplar(codigo) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}else {
			gestor.altaEjemplar(codigo);
			ResponseBuilder rBuilder = Response.ok(null);
			//response rBuilder.build();
		}
        return null; // A MODIFICAR
    }


}
