package presentation;

import ext.DaoImpl2;
import metier.MetierImpl;
// la couche qui va subir la maintenance

public class pres1 {
    public static void main(String[] args) {
        DaoImpl2 dao = new DaoImpl2(); // instanciation statique
        MetierImpl metier = new MetierImpl();
        metier.setDao(dao); //injection de dépendance
        System.out.println("Résultat:"+metier.calcul());
    }
}
