/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AA;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author fmgb92
 */
public class Prototipo implements Serializable{
    private char etiqueta;
    private String contorno;
    private int distancia = Integer.MAX_VALUE;
    
    public Prototipo(char etiqueta, String contorno) {
        this.etiqueta = etiqueta;
        this.contorno = contorno;
    }
        
    public Prototipo(Prototipo copia) {
        etiqueta = copia.etiqueta;
        contorno = copia.contorno;
        distancia = copia.distancia;
    }

    public char getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(char etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getContorno() {
        return contorno;
    }

    public void setContorno(String contorno) {
        this.contorno = contorno;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Prototipo other = (Prototipo) obj;
        if (etiqueta != other.etiqueta) {
            return false;
        }
        if (!contorno.equals(other.contorno)) {
            return false;
        }
        return distancia == other.distancia;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.etiqueta;
        hash = 97 * hash + Objects.hashCode(this.contorno);
        hash = 97 * hash + this.distancia;
        return hash;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Prototipo{\n" + "HashCode: " + hashCode() + "\netiqueta= " + etiqueta + "\ncontorno= " + contorno + "\n Distancia= " + distancia +"\n}\n";
    }
    
    /**
     * @reference Constantino Callado Pérez
     * @param trainning
     * @param direccionFichero 
     */
    public static void escribirEnDisco(ArrayList<Prototipo> trainning, String direccionFichero) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(direccionFichero));
            os.writeObject(trainning);
        } catch (IOException e) {
            
            System.out.println("Ha fallado en la escritura en el disco" + e.getMessage());
        }
    }
    
    /**
     * @refence Constantino Callado Pérez
     * @param direccionFichero 
     * @return Arraylist con todos los prototipos leidos.
     */
    public static ArrayList<Prototipo> leerEnDisco(String direccionFichero) {
        ArrayList<Prototipo> trainning = new ArrayList();
        
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(direccionFichero));
            trainning =(ArrayList<Prototipo>)is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ha fallado en la escritura en el disco" + e.getMessage());
        }
        return trainning;
    }
}
