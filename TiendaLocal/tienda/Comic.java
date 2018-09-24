package tienda;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Scanner;

public class Comic implements Serializable {


    /**
     * Clase que permite escribir y leer un Comic de teclado y en un fichero de acceso directo
     */
    private static final long serialVersionUID = 1L;

    private int codigo;
    private String titulo;
    private String autor;
    private float precio;
    private int cantidad;

    /**
     * Constructor por defecto de un Comic
     */
    public Comic() {
        super();
    } // fin de constructor por defecto

    /**
     * Constructor de un Comic con argumentos
     *
     * @param    codigo        codigo del comic
     * @param    titulo        titulo del comic
     * @param    autor        autor del comic
     * @param    precio        precio del comic
     * @param    cantidad    ejemplares disponibles del comic
     */
    public Comic(int codigo, String titulo, String autor, float precio, int cantidad) {
        super();
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
        this.cantidad = cantidad;
    }  // fin de constructor con argumentos

    /**
     * @return el codigo del comic
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo codigo a asignar
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return el titulo del comic
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo titulo a asignar
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return el autor del comic
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @param autor autor a asignar
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * @return el precio del comic
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * @param precio precio a asignar
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * @return el numero de ejemplares disponibles
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad cantidad a asignar
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    /**
     * Escribe los datos de un comic en una cadena y la devuelve
     *
     * @return cadena con los datos del comic
     */
    @Override
    public String toString() {
        return "[codigo=" + codigo + ", titulo='" + titulo + "', autor='"
                + autor + "', disponibles=" + cantidad + ", precio=" + precio + " euros]";
    } // fin de toString

    /**
     * Lee los datos de un comic de un stream de entrada
     */
    public void leeDeTeclado(Scanner teclado) {

        System.out.println("Introduce el Codigo: "); this.setCodigo(teclado.nextInt());
        System.out.println("Introduce el Titulo: "); this.setTitulo(teclado.next());
        System.out.println("Introduce el Autor: "); this.setAutor(teclado.next());
        System.out.println("Introduce el Precio: "); this.setPrecio(teclado.nextFloat());
        System.out.println("Introduce el Numero de Ejemplares: "); this.setCantidad(teclado.nextInt());
        

    } // fin leeDeTeclado

    /**
     * Lee los datos de un comic de la posicion actual de un fichero
     *
     * @param    stream    stream asociado al fichero
     * @throws EOFException, IOException
     */
    public void leeDeFichero(RandomAccessFile stream) throws EOFException, IOException {
    	//try {
    		this.setCodigo(stream.readInt());
    		//System.out.println("codigo leido" + codigo +  " puntero " + stream.getFilePointer());
    		this.setTitulo(stream.readUTF());
    		//System.out.println("titulo leido " + titulo +  " puntero " + stream.getFilePointer());
    		this.setAutor(stream.readUTF());
    		//System.out.println("autor leido " + autor + " puntero " + stream.getFilePointer());
    		this.setPrecio(stream.readFloat());
    		//System.out.println("precio leido " + precio + " puntero " + stream.getFilePointer());
    		this.setCantidad(stream.readInt());
    		//System.out.println("cantidad leido " + cantidad +" puntero " + stream.getFilePointer());
    	//}catch (EOFException e) {
    		//System.out.println("Excepción controlada en Comic.java: Final del fichero");// TODO: handle exception
		//}
    } // fin leeDeFichero

    /**
     * Escribe los datos de un comic en la posicion actual de un fichero
     *
     * @param    stream    stream asociado al fichero
     * @throws IOException 
     */
    public void escribeEnFichero(RandomAccessFile stream) throws IOException {
    	
		stream.writeInt(this.getCodigo());
		stream.writeUTF(this.getTitulo());
		stream.writeUTF(this.getAutor());
		stream.writeFloat(this.getPrecio());
		stream.writeInt(this.getCantidad());
		
    } // fin escribeEnFichero


}
