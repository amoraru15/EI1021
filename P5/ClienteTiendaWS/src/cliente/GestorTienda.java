package cliente;


import javax.ws.rs.NotFoundException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class GestorTienda {

    // URI del recurso que permite acceder a la agenda
    final private String baseURI = "http://localhost:8080/TiendaWS/servicios/tienda/";
    Client cliente = null;

    /**
     * Constructor de la clase
     */
    public GestorTienda() {
        // POR IMPLEMENTAR
    	this.cliente = ClientBuilder.newClient();
    }

    /**
     * Obtiene una cadena con todos los autores
     * Realiza una peticion a la URI {baseURI}
     *
     * @return vector de cadenas, cada una con un autor
     * @throws NotFoundException
     */
    public String[] listaAutores() throws NotFoundException {
        // POR IMPLEMENTAR
    	Response response = cliente.target(baseURI).request().get();
    	if(response.getStatus() !=200 ) {
    		throw new NotFoundException();
    		
    	}
    	String cadenaAutores = response.readEntity(String.class);
    	String [] lista = cadenaAutores.split(";");
    	response.close();
        return lista; // A MODIFICAR
    }

    /**
     * Obtiene los datos de un contacto.
     * Realiza una peticion a la URI {baseURI}/{autor}
     *
     * @param    autor    autor del comic buscado
     * @return cadena con informacion sobre el comic "codigo#titulo#autor#precio#cantidad"
     * @throws NotFoundException    si no se ha encontrado el autor
     */
    public String[] buscaAutor(String autor) throws NotFoundException {
        // POR IMPLEMENTAR
    	Response response = cliente.target(baseURI).path("autor").request(MediaType.TEXT_PLAIN).get();
    	if(response.getStatus() != 201) {
    		throw new NotFoundException();
    	}
    	String buscaAutor = response.readEntity(String.class);
    	String [] listaAutor = buscaAutor.split("#");
    	response.close();
     	return listaAutor; // A MODIFICAR
    }

    /**
     * Compra un ejemplar del comic con un codigo dado
     * Realiza una peticion a la URI {baseURI}/{codigo}
     *
     * @param    codigo    cadena con los datos del comic, vacia si no se encuentra o no quedan ejemplares
     */
    public String compraComic(int codigo) {
        // POR IMPLEMENTAR
    	Response response = cliente.target(baseURI).path("codigo").queryParam("cantidad").request().put(Entity.text("-1"));
    	if(response.getStatus() != 201) {
    		throw new NotFoundException();
    	}
    	
    	response.close();
        return null; // A MODIFICAR
    }

    /**
     * Vende un ejemplar del comic a la tienda
     * Realiza una peticion a la URI {baseURI}/{codigo}
     *
     * @return cadena con los datos del comic, vacia si no se encuentra
     * @param    codigo    codigo del comic a vender
     */
    public String vendeComic(int codigo) {
        // POR IMPLEMENTAR
    	Response response = cliente.target(baseURI).path("codigo").queryParam("cantidad").request().put(Entity.text("1"));
    	if(response.getStatus() != 201) {
    		throw new NotFoundException();
    	}
    	
    	response.close();
        return null; // A MODIFICAR
    }


} // fin clase
