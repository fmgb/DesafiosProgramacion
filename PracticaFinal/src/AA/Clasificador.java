/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AA;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author fmgb92
 */
public class Clasificador {

    private ArrayList<Prototipo> Training = new ArrayList();
    private ArrayList<Prototipo> Test = new ArrayList();
    private final Lector lector;
    Knn knn;

    public Clasificador(String ficheroTrainning, String ficheroTest, int K) {
        this.knn = new Knn(K);
        //Leemos el fichero de Trainning
        lector = new Lector(ficheroTrainning);
        lector.leer();
        //Leemos el fichero de Test
        Training = lector.getPrototipos();
        lector.setRutaFichero(ficheroTest);
        lector.leer();

        Test = lector.getPrototipos();
    }

    public char realizarTest(String contornoTest) {
        return knn.realizarKnnSinPonderar(contornoTest);
    }

    //REVISAR FORMULA PORCENTAJE ACIERTO.
    public double[] porcentajeAcierto(ArrayList<Prototipo> test, ArrayList<Prototipo> training) {
        int[] resultados = new int[26];
        for (int d : resultados) {
            d = 0;
        }
        //AÑADIDO
        knn.setTraining(training);
        int p, aciertos = 0, errores = 0;
        System.out.println("Entro a realizar el test");
        for (int i = 0; i < test.size(); i++) {
//            System.out.println("Voy por" + i);
            int pmin = Integer.MIN_VALUE;
            char aux = knn.realizarKnnSinPonderar(test.get(i).getContorno());

            // Devuelva el char que ha calculado que es.
            if (aux == test.get(i).getEtiqueta()) {
                ++aciertos;
                resultados[aux - 65] = ++resultados[aux - 65];
//                resultados[aux - 65] = resultados[aux - 65] + (100 / 40) + 0.1 * (100 / (100 % (test.size() / 26)));
            } else {
                ++errores;
            }
        }
        System.err.println("***************************************");
        System.out.println("Tamaño del test" + test.size() + "\n Errores: " + errores + "\n Aciertos: " + aciertos);
        System.err.println("***************************************");
        double[] resultadosFinales = new double[26];
        for (int i = 0; i < resultados.length; ++i) {
            resultadosFinales[i] = resultados[i] / 40.0 * 100;
        }
        return resultadosFinales;
    }

    public void realizarCrossValidationSinReductores(String ruta) {
        System.out.println("Tengo esta ruta\n" + ruta);
        System.out.println("Y voy a realizarlo con K= " + knn.getK());
        double[] resultado0, resultado1, resultado2, resultado3, resultado4;
        ArrayList<Prototipo> trainingAux = new ArrayList();
        String resultadoEnFichero = "";
        ArrayList<Prototipo> cvarray0 = Prototipo.leerEnDisco(ruta + "CrossValidation/CrossValidation0.cad");
        ArrayList<Prototipo> cvarray1 = Prototipo.leerEnDisco(ruta + "CrossValidation/CrossValidation1.cad");
        ArrayList<Prototipo> cvarray2 = Prototipo.leerEnDisco(ruta + "CrossValidation/CrossValidation2.cad");
        ArrayList<Prototipo> cvarray3 = Prototipo.leerEnDisco(ruta + "CrossValidation/CrossValidation3.cad");
        ArrayList<Prototipo> cvarray4 = Prototipo.leerEnDisco(ruta + "CrossValidation/CrossValidation4.cad");
        System.out.println("He leido los ficheros");
        //Entrenamiento del 0-1-2-3, test 4
        trainingAux.addAll(cvarray0);
        trainingAux.addAll(cvarray1);
        trainingAux.addAll(cvarray2);
        trainingAux.addAll(cvarray3);
        resultado4 = porcentajeAcierto(cvarray4, trainingAux);
        System.out.println("Realizado el test 4");

        //Borramos TrainingAux y empezamos otra vez
        trainingAux.clear();
        //Entrenamiento del 0-1-2-4, Test 3
        trainingAux.addAll(cvarray0);
        trainingAux.addAll(cvarray1);
        trainingAux.addAll(cvarray2);
        trainingAux.addAll(cvarray4);
        resultado3 = porcentajeAcierto(cvarray3, trainingAux);
        System.out.println("Realizado el test 3");
        trainingAux.clear();
        //Entrenamiento del 0-1-3-4, Test 2
        trainingAux.addAll(cvarray0);
        trainingAux.addAll(cvarray1);
        trainingAux.addAll(cvarray3);
        trainingAux.addAll(cvarray4);
        resultado2 = porcentajeAcierto(cvarray2, trainingAux);
        System.out.println("Realizado el test 2");

        trainingAux.clear();
        //Entrenamiento del 0-2-3-4, Test 1
        trainingAux.addAll(cvarray0);
        trainingAux.addAll(cvarray2);
        trainingAux.addAll(cvarray3);
        trainingAux.addAll(cvarray4);
        resultado1 = porcentajeAcierto(cvarray1, trainingAux);
        System.out.println("Realizado el test 1");

        trainingAux.clear();
        //Entrenamiento del 1-2-3-4, Test 0
        trainingAux.addAll(cvarray1);
        trainingAux.addAll(cvarray2);
        trainingAux.addAll(cvarray3);
        trainingAux.addAll(cvarray4);
        resultado0 = porcentajeAcierto(cvarray0, trainingAux);
        System.out.println("Realizado el test 0");

        System.out.println("**********************************************************");
        resultadoEnFichero += "Se ha utilizado el Knn con una K de: " + knn.getK();
        System.out.println("Se ha utilizado el Knn con una K de: " + knn.getK() + "\n");
        resultadoEnFichero += "Los resultados obtenidos han sido: \n" + " Letra \t\t" + "Test 0:\t\t" + "Test 1:\t\t" + "Test 2\t\t" + "Test 3\t\t" + "Test 4\t\t" + "MEDIA\n";
        System.out.println("Los resultados obtenidos han sido: \n" + " Letra \t\t" + "Test 0:\t\t" + "Test 1:\t\t" + "Test 2\t\t" + "Test 3\t\t" + "Test 4\t\t" + "MEDIA\n");
        double[] media = new double[6];
        double mediaAux = 0.0;
        for (int i = 0; i < resultado1.length; i++) {
            media[0] = resultado1[i] + resultado2[i] + resultado3[i] + resultado4[i] + resultado0[i];
            media[0] = media[0] / 5.0;
//            mediaAux = media[0];
            media[1] += resultado0[i];
            media[2] += resultado1[i];
            media[3] += resultado2[i];
            media[4] += resultado3[i];
            media[5] += resultado4[i];
            System.out.println("  " + ((char) (i + 65)) + "\t\t" + resultado0[i] + "\t\t" + resultado1[i] + "\t\t" + resultado2[i] + "\t\t" + resultado3[i] + "\t\t" + resultado4[i] + "\t\t" + media[0] + "\n");
            resultadoEnFichero += "  " + ((char) (i + 65)) + "\t\t" + resultado0[i] + "\t\t" + resultado1[i] + "\t\t" + resultado2[i] + "\t\t" + resultado3[i] + "\t\t" + resultado4[i] + "\t\t" + media[0] + "\n";
        }
        for (int i = 1; i < media.length; ++i) {
            mediaAux += media[i] / 26;
        }
        System.out.println("MEDIA" + "\t" + media[1] / 26.0 + "\t" + media[2] / 26.0 + "\t" + media[3] / 26.0 + "\t" + media[4] / 26.0 + "\t" + media[5] / 26.0 + "\t" + mediaAux/5 + "\n");
        resultadoEnFichero += "MEDIA" + "\t" + media[1] / 26.0 + "\t" + media[2] / 26.0 + "\t" + media[3] / 26.0 + "\t" + media[4] / 26.0 + "\t" + media[5] / 26.0 + "\t" + mediaAux/5 + "\n";
        EscribeEnFicheroLaString("/home/fmgb92/Universidad/Repositorios/DP/DP/" + "Resultados/CVConReductor/EditingCondensing/resultadoEditing4Condensing9Knn" + knn.getK() + ".txt", resultadoEnFichero);
        System.out.println("Texto Escrito en: \n" + "/home/fmgb92/Universidad/Repositorios/DP/DP/" + "Resultados/CVConReductor/EditingCondensing/resultadoEditing4Condensing9Knn" + knn.getK() + ".txt");
    }

    public static void EscribeEnFicheroLaString(String rutaFichero, String texto) {
        FileWriter fichero;
        fichero = null;
        PrintWriter pw;
        try {
            fichero = new FileWriter(rutaFichero);
            pw = new PrintWriter(fichero);

            pw.println(texto);

        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    public void realizarCrossValidationConReductores(String fichero, boolean editing, boolean CNN) {
        //REALIZADO EDITING Y CONDENSING, FALTA REALIZARLO CON VARIOS K.
        // REALIZAR BAGGING!
    }

    public void repartirTraining(String ruta) {
        System.out.println("La ruta es:\n" + ruta);
        HashMap<Character, ArrayList<String>> hash = new HashMap();
//       int total = 0;
        for (Prototipo p : Training) {
//            ++total;
            if (hash.containsKey(p.getEtiqueta())) {
                ArrayList<String> s = hash.get(p.getEtiqueta());
                s.add(p.getContorno());
                hash.put(p.getEtiqueta(), s);
            } else {
                ArrayList<String> s = new ArrayList();
                s.add(p.getContorno());
                hash.put(p.getEtiqueta(), s);
            }
        }
        /*        System.out.println("Hay un total de " + total);
         System.out.println("**********************************************");
         total = 0;
         System.out.println("Fichero" + fichero);
         */ ArrayList<Prototipo> cvarray0 = new ArrayList();
        ArrayList<Prototipo> cvarray1 = new ArrayList();
        ArrayList<Prototipo> cvarray2 = new ArrayList();
        ArrayList<Prototipo> cvarray3 = new ArrayList();
        ArrayList<Prototipo> cvarray4 = new ArrayList();
        for (char name : hash.keySet()) {
            int contador = 0;
            for (String s : hash.get(name)) {
                int limite = hash.get(name).size();
                Prototipo proto = new Prototipo(name, s);
                if (contador < limite / 5) {
                    cvarray0.add(proto);
                } else if (limite / 5 <= contador && contador < (limite / 5) * 2) {
                    cvarray1.add(proto);
                } else if ((limite / 5) * 2 <= contador && contador < (limite / 5) * 3) {
                    cvarray2.add(proto);
                } else if ((limite / 5) * 3 <= contador && contador < (limite / 5) * 4) {
                    cvarray3.add(proto);
                } else {
                    cvarray4.add(proto);
                }
                //               ++total;
                ++contador;
            }
//           System.out.println("Hay un total de: " + contador + " distintas clases para: " + name);
        }

        //Escribir en disco
        System.out.println("Las arrays tienen un tamaño de:");
        System.out.println("Array 0: " + cvarray0.size());
        System.out.println("Array 1: " + cvarray1.size());
        System.out.println("Array 2: " + cvarray2.size());
        System.out.println("Array 3: " + cvarray3.size());
        System.out.println("Array 4: " + cvarray4.size());

        Prototipo.escribirEnDisco(cvarray0, ruta + "CrossValidation0.cad");//CrossValidation0.cad");
        Prototipo.escribirEnDisco(cvarray1, ruta + "CrossValidation1.cad");
        Prototipo.escribirEnDisco(cvarray2, ruta + "CrossValidation2.cad");
        Prototipo.escribirEnDisco(cvarray3, ruta + "CrossValidation3.cad");
        Prototipo.escribirEnDisco(cvarray4, ruta + "CrossValidation4.cad");
        /*       System.out.println("Total " + total);
         System.out.println("**********************************************");
         System.out.println("TAMAÑO DE CVTEST " + cvarray0.size() + "\n");
         System.out.println("TAMAÑO DE CVT1 " + cvarray1.size() + "\n");
         System.out.println("TAMAÑO DE CVT2 " + cvarray2.size() + "\n");
         System.out.println("TAMAÑO DE CVT3 " + cvarray3.size() + "\n");
         System.out.println("TAMAÑO DE CVT4 " + cvarray4.size() + "\n");
         
         */    }

    public void setTraining(ArrayList<Prototipo> training) {
        Training = training;
        System.out.println("Tamaño del entrenamiento: " + training.size());
    }

    public void Editing(int K, String ruta) {
        ArrayList<Prototipo> S = new ArrayList(Training), R = new ArrayList();
        knn.setK(K);
        knn.setTraining(Training);
        System.out.println("Entro a Editing con Knn=" + knn.getK());
        int i = 0;
        for (Prototipo p : S) {
            if (p.getEtiqueta() != knn.realizarKnnSinPonderar(p.getContorno())) {
                R.add(p);
                System.out.println("He entrado.");
            }
        }
        System.out.println("Lo he realizado con K=" + K);
        System.out.println("Se ha eliminado un total de: " + R.size());
        S.removeAll(R);
        Prototipo.escribirEnDisco(S, ruta + "Editing1/EditingKnn" + K + ".cad");
    }

    public void MultiEdit(int K, String ruta) { // Training set
        System.out.println("Ruta\n" + ruta);
        ArrayList<Prototipo> S = new ArrayList(Training); // Edited set
        ArrayList<Prototipo> R;
        knn.setK(K);
        do {
            R = new ArrayList(); // Misclassified set
            knn.setTraining(S);
            for (Prototipo p : S) {
                if (p.getEtiqueta() != knn.realizarKnnSinPonderar(p.getContorno())) { // Misclassified example
                    R.add(p);
// Remove example
                }
            } // for
            S.removeAll(R);
// Remove all misclassified examples
        } while (!R.isEmpty());
//        knn.setModoPonderando(prevMode);
        System.out.println("He eliminado un total de: " + R.size());
        Prototipo.escribirEnDisco(S, ruta + "MultiEdit/knn" + knn.getK() + "/MultiEditKnn" + knn.getK() + ".cad");
    }

    public void Condensing(int K, String ruta) {
        ArrayList<Prototipo> S = new ArrayList();
        boolean updated;
        knn.setK(K);
        knn.setTraining(S);
        int i = 0;
        Collections.shuffle(Training);
        do {
            System.out.println("Voy por" + ++i + " y S tiene un tamaño de: " + S.size());
            updated = false;
            for (Prototipo p : Training) {
                if (p.getEtiqueta() != knn.realizarKnnSinPonderar(p.getContorno())) {
                    S.add(p);
                    System.out.println("He entrado en el arraylist");
                    updated = true;
                }
            }
        } while (S.size() < Training.size() && updated);
        System.out.println("Lo he realizado con K=" + K);
        System.out.println("Tengo un tamaño despues de realizar el condensing: " + S.size());
        Prototipo.escribirEnDisco(S, ruta + "EditingCondensing/knn"+ K + "/Editing4CondensingKnn" + K + ".cad");
    }

    public static void bagging(ArrayList<Prototipo> training, int m, float percent) {
        ArrayList<ArrayList<Prototipo>> result = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            result.add(new ArrayList<>(training));

            int elemToDel = (int) ((float) result.get(i).size() * (1 - percent));

            for (int j = 0; j < elemToDel; j++) {
                Random r = new Random(result.get(i).size() - 1);
                result.get(i).remove(r.nextInt());
            }

        }
        
        

        
    }
}
