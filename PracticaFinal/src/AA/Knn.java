/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AA;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author fmgb92
 */
public class Knn {

    private int K;
    private boolean ponderado;
    private ArrayList<Prototipo> training;

    public Knn() {
        K = 1;
        ponderado = false;
    }

    public Knn(int K) {
        this.K = K;
        ponderado = false;
    }

    public Knn(int K, boolean ponderado) {
        this.K = K;
        this.ponderado = ponderado;
    }

    public int getK() {
        return K;
    }

    public void setK(int K) {
        this.K = K;
    }

    public boolean getPonderado() {
        return ponderado;
    }

    public void setPonderado(boolean ponderado) {
        this.ponderado = ponderado;
    }

    public ArrayList<Prototipo> getTraining() {
        return training;
    }

    public void setTraining(ArrayList<Prototipo> training) {
        this.training = training;
    }

    public void inicializarVecinos(Prototipo[] vector) {
        for (int i = 0; i < vector.length; i++) {
            Prototipo p1 = new Prototipo('A', "a");
            vector[i] = p1;
            if (ponderado) {
                vector[i].setDistancia(Integer.MIN_VALUE);
            } else {
                vector[i].setDistancia(Integer.MAX_VALUE);
            }
        }
    }

    public char realizarKnnSinPonderar(String contornoTest) {
        Prototipo[] vecinos = new Prototipo[K];
        inicializarVecinos(vecinos);
        int distancia;
        for (Prototipo p : training) {
            distancia = LevensteinDistance.computeLevenshteinDistance(p.getContorno(), contornoTest);
            if (distancia < vecinos[K - 1].getDistancia()) {
                //Significa que es mejor que al menos uno de los vecinos que ya tenemos.
                p.setDistancia(distancia);
                desplazarVector(vecinos, p);
            }
        }
        return comprobarVector(vecinos);
    }

    //TODO PROBAR ESTE METODO, no estoy muy seguro.
    public char realizarKnnPonderado(String contornoTest, ArrayList<Prototipo> training) {
        Prototipo[] vecinos = new Prototipo[K];
        inicializarVecinos(vecinos);
        int distancia;
        double peso;
        for (Prototipo p : training) {
            distancia = LevensteinDistance.computeLevenshteinDistance(p.getContorno(), contornoTest);
            //PONDERACION EN BASE A LA FORMULA DE WIKIPEDIA
            peso = 1 / (((distancia * distancia)) + 0.001);
            if (peso > vecinos[K - 1].getDistancia()) {
                p.setDistancia(distancia);
                desplazarVector(vecinos, p);
            }
        }
        //CREO QUE DEVERIA DE DEVOLVER EL PRIMER ELEMENTO YA QUE SERA EL QUE MENOS DISTANCIA TENGA
        return vecinos[K - 1].getEtiqueta();
    }

    public void desplazarVector(Prototipo[] vector, Prototipo pro) {
        Prototipo valoraux = pro;
        for (int i = 0; i < vector.length; i++) {
            if (valoraux.getDistancia() < vector[i].getDistancia()) {
                Prototipo aux = vector[i];
                vector[i] = valoraux;
                valoraux = aux;
            }
        }
    }

    //VERIFICAR ESTE METODO 
    public char comprobarVector(Prototipo[] vector) {
        HashMap<Character, Integer> recuento = new HashMap<>();
        HashMap<Character, Integer> repetidos = new HashMap<>();
        for (Prototipo pro : vector) {
            if (recuento.containsKey(pro.getEtiqueta())) {
                int i = recuento.get(pro.getEtiqueta());
                recuento.put(pro.getEtiqueta(), i + 1);
            } else {
                recuento.put(pro.getEtiqueta(), 1);
            }
        }

        char resultado = ' ';
        int aux = Integer.MIN_VALUE;

        for (Character c : recuento.keySet()) {
            if (recuento.get(c) > aux) {
                aux = recuento.get(c);
                resultado = c;
            }
        }
        for (Character c : recuento.keySet()) {
            if (recuento.get(c) == aux) {
                repetidos.put(c, aux);
            }
        }
        if (repetidos.size() > 1) {
            //Si hay más de dos repetidos, el valor mostrado sera el primer elemento mas cercano del vector que sea repetido.
            for (Prototipo vector1 : vector) {
                if (repetidos.containsKey(vector1.getEtiqueta())) {
                    resultado = vector1.getEtiqueta();
                    break;
                }
            }
        }
        return resultado;
    }
}
