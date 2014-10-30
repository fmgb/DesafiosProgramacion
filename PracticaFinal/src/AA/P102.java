/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AA;

/**
 *
 * @author fmgb92
 */
public class P102 {
    /* 
    doube time_start, time_end;
    time_start = System.currentTimeMillis();
    ReallyHeavyTask();
    time_end= System.currentTimeMillis();
    System("LA tarea ha tardado: " time_end - time_start "Milliseconds" 
    */
    private Clasificador cla;
    
    public P102(String fichero1, String fichero2, int i) {
        cla = new Clasificador(fichero1, fichero2 , i );
    }
    
    public char getContourClass(String data) {
        return cla.realizarTest(data);
    }
    
    public Clasificador getClasificador() {
        return cla;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int k = 4; //DADO QUE LA MEJOR PUNTUACION HA SIDO EL K=4
        //boolean condensing = true;
        boolean editing = true;
        String ruta = "/home/fmgb92/Universidad/Repositorios/DP/DP/EditingCondensing/knn9/";
 //       int multiedit = 3;
 //       String rutaMultiEdit = "/home/fmgb92/Universidad/Repositorios/DP/DP/MultiEdit/knn"+ multiedit + "/";
        String s = "/home/fmgb92/Universidad/Repositorios/DP/DP/";
        // TODO code application logic here
            //Training primero y luego Test.
        //Sustituir s por args[0];
        P102 programa = new P102(args[1], args[0], k);
     //   programa.getClasificador().setTraining(Prototipo.leerEnDisco(rutaMultiEdit+"MultiEditKnn"+ multiedit + ".cad"));
  //      Clasificador.EscribeEnFicheroLaString("./fichero", s);
       programa.getClasificador().setTraining(Prototipo.leerEnDisco(s+"EditingCondensing/knn9/Editing4CondensingKnn9.cad"));
  //      Clasificador cla = new Clasificador(args[1], args[0], k);//Integer.parseInt(args[3]));
       // if(condensing)
         //   programa.getClasificador().realizarCrossValidationSinReductores(s);//args[2]+"Condensing/knn1/");
 //       if (editing)
 //           programa.getClasificador().realizarCrossValidationSinReductores(args[2]+"Editing/knn11/");
       
 //       programa.getClasificador().repartirTraining(rutaMultiEdit+"CrossValidation/");
  //  programa.getClasificador().repartirTraining(s+"EditingCondensing/knn9/CrossValidation/");
       programa.getClasificador().realizarCrossValidationSinReductores(s+"EditingCondensing/knn9/");
//        cla.repartirTraining(args[2]);
//            programa.getClasificador().MultiEdit(8,args[2]);
       // programa.getClasificador().Condensing(9, s);
 //       cla.realizarCrossValidationSinReductores(args[2]);
    }
   
}
