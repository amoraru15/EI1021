package gestor;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GestorConsultas {

	// Sustituye esta clase por tu versión de la misma de la práctica 1
	private RandomAccessFile stream;

    /**
     * Constructor del gestor de consultas de la tienda.
     * Crea un fichero con datos de prueba
     */
    public GestorConsultas() {
        creaFichero("comics.dat");
    }

    /**
     * Cierra el flujo/stream asociado al fichero de comics.
     * @throws IOException 
     */
    public void cierraGestor() throws IOException {
    	stream.close();
    }


    /**
     * Devuelve un vector con los autores de comics en el catalogo de la tienda
     *
     * @return Vector de cadenas con los autores
     * @throws IOException 
     */
    public String[] listaAutores() throws IOException {

    	Comic comic = new Comic();
    	Set<String> conjuntoAutores = new HashSet<String>();//Creamos un conjunto para no guardar autores repetidos
    	stream.seek(0);
    	while(stream.getFilePointer() != stream.length()) {	// Mientras no lleguemos al final del archivo
    		comic.leeDeFichero(stream);						// Vamos añadiendo los autores a nuestro conjunto
    		conjuntoAutores.add(comic.getAutor());
    	}
    	
    	String[] listaAutores = conjuntoAutores.toArray(new String [conjuntoAutores.size()]);

    	return listaAutores;
    }

    /**
     * Metodo auxiliar privado que busca un comic con un codigo dado y devuelve su posicion en el fichero
     * Si no lo encuentra, devuelve -1
     *
     * @param    codigoBuscado    codigo del comic buscado
     * @return byte de inicio del registro en el fichero
     * @throws IOException 
     */
    private long buscaCodigo(int codigoBuscado) throws IOException {

    	Comic comic = new Comic();
    	long punteroAnterior;
    	stream.seek(0);
    	while (stream.getFilePointer() != stream.length()) {
    		punteroAnterior = stream.getFilePointer();	// Nos guardamos el puntero para saber
    		comic.leeDeFichero(stream);						// Donde empieza el comic que vamos a leer
    		if (comic.getCodigo() == codigoBuscado)	// Si el codigo es correcto
    			return punteroAnterior;				// Devolvemos el puntero que habiamos guardado
    	}

    	return -1; 
    }


    /**
     * Busca los comics de un determinado autor en el fichero y los devuelve como un vector de cadenas
     * Si no hay ninguno, devuelve el vector vacio
     *
     * @param    autorBuscado    autor del comic buscado
     * @return Vector de cadenas asociadas a los comics del autor
     * @throws IOException 
     * @throws EOFException
     */
    public String[] buscaAutor(String autorBuscado) throws EOFException, IOException {

		Comic comic = new Comic();
		ArrayList<String> listaAutores = new ArrayList<String>();
		stream.seek(0);
		while (stream.getFilePointer() != stream.length()) {
			comic.leeDeFichero(stream);
			if (comic.getAutor().equals(autorBuscado)) {	// Si el nombre del autor es el correcto	
				listaAutores.add(comic.getTitulo());		// Añadimos los elementos a la colección
			}
		}

		String[] listaComics = listaAutores.toArray(new String[listaAutores.size()]);
		
		
		return listaComics;
    }

    /**
     * Da de baja un ejemplar del comic con un codigo dado y devuelve una cadena con sus datos
     * Si no hay ninguno, devuelve una cadena vacia
     *
     * @param    codigoBuscado    codigo del comic buscado
     * @return cadena con informacion del comic
     * @throws IOException 
     */
    public String bajaEjemplar(int codigoBuscado) throws IOException {
    	
    	long puntero = buscaCodigo(codigoBuscado);
    	if (puntero == -1) return "No se encuentra el comic en la tienda.";
    	
    	stream.seek(puntero);	// Movemos el puntero del fichero donde toca
    	Comic comic = new Comic();
    	comic.leeDeFichero(stream);	// Leemos el comic
    	int cantidadComics = comic.getCantidad(); 
    	if (cantidadComics == 0) return "No quedan ejemplares del comic en la tienda.";
    	cantidadComics--;
    	comic.setCantidad(cantidadComics);
    	String cadenaComic = comic.toString();	// Lo convertimos a una cadena
    	
    	stream.seek(puntero);	// Movemos el puntero donde antes y pasamos a decrementar el numero de ejemplares
    	stream.readInt(); stream.readUTF(); stream.readUTF();
		stream.readFloat(); stream.writeInt(cantidadComics);

    	return cadenaComic;
    }

    /**
     * Da de alta un ejemplar del comic con un codigo dado y devuelve una cadena con sus datos
     * Si el comic no estaba en el fichero, devuelve la cadena vacia
     *
     * @param    codigoBuscado    codigo del comic buscado
     * @return cadena con informacion del comic
     * @throws IOException 
     */
    public String altaEjemplar(int codigoBuscado) throws IOException {

    	long puntero = buscaCodigo(codigoBuscado);
    	String cadenaComic = new String();
    	if (puntero == -1) return "No se encuentra el cómic en la tienda.";
    	
    	stream.seek(puntero);	// Movemos el puntero del fichero donde toca
    	Comic comic = new Comic();
    	comic.leeDeFichero(stream);	// Leemos el comic
    	int cantidadComics = comic.getCantidad(); cantidadComics++;
    	comic.setCantidad(cantidadComics);
    	cadenaComic = comic.toString();	// Lo convertimos a una cadena
    	
    	stream.seek(puntero);	// Movemos el puntero donde antes y pasamos a incrementar el numero de ejemplares
    	stream.readInt(); stream.readUTF(); stream.readUTF();
		stream.readFloat(); stream.writeInt(cantidadComics);

    	return cadenaComic;
    }


    /**
     * Si no existe, crea un fichero con unos datos de prueba
     *
     * @param   nombreFichero   nombre del fichero a crear
     */
    public void creaFichero(String nombreFichero) {

    	File file = new File(nombreFichero);
    	if(!file.exists()) {	//Si el archivo no existe se crea y se rellena con datos
    		try {
				stream = new RandomAccessFile(file,"rw");
				
				Comic c = new Comic(1, "Watchmen", "A. Moore", 20.00f, 3);
    			c.escribeEnFichero(stream);
				
    			c = new Comic(2, "Akira", "K. Otomo", 130.00f, 1);
    			c.escribeEnFichero(stream);
    			
	    		c = new Comic(3, "Bone", "J. Smith", 20.00f, 10);
    			c.escribeEnFichero(stream);
	    		
	    		c = new Comic(4, "The League of Extraordinay Gentlemen", "A. Moore", 50.00f, 5);
    			c.escribeEnFichero(stream);
	    		
	    		c = new Comic(5, "Barrio Lejano", "J. Taniguchi", 35.00f, 2);
    			c.escribeEnFichero(stream);
	    		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	else {
			try {
				stream = new RandomAccessFile(file,"rw");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
    }
}





