/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author fmgb92
 */
class Lector {
    public String RutaFichero = "";
    private ArrayList<Prototipo> conjunto = new ArrayList();
    
    public Lector(String RutaFichero) {
        this.RutaFichero = RutaFichero;
    }
    
    public void setRutaFichero(String RutaFichero) {
        this.RutaFichero = RutaFichero;
        conjunto.clear();
    }
    
    public String getRutaFichero() {
        return RutaFichero;
    }
    public ArrayList<Prototipo> getPrototipos() {
        return conjunto;
    }
    
    public void mostrarTextoLeido() {
        for(int i = 0; i < conjunto.size(); ++i) {
            System.out.println("Linea nº " + i + ": " + conjunto.get(i));
        }
    }
    
    public void leer() {
        FileReader fr = null;
        try {
            File archivo = new File(RutaFichero);
            fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            
            String linea;
            while((linea = br.readLine()) != null) {
                String [] cadena =  linea.split(" ");
                conjunto.add(new Prototipo(cadena [0].charAt(0), cadena[1]));            }
        }catch(IOException e) {
            System.err.println(e.toString());
        }finally {
            if(null != fr) {
                try {
                    fr.close();
                } catch (IOException ex) {
                   System.err.println(ex);   
                }
            }
        }
    }
}
