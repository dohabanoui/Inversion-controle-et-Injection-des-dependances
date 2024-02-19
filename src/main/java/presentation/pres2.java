package presentation;

import dao.IDao;
import ext.DaoImpl2;
import metier.IMetier;
import metier.MetierImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class pres2 {
    // on utilise le couplage faible(depend que des interfaces)
    // on utilise l'instanciation dynamique
    public static void main(String[] args) throws Exception{
        //DaoImpl2 dao = new DaoImpl2(); // instanciation statique
        Scanner scanner = new Scanner(new File("config.txt"));
        String daoClassName = scanner.nextLine();
        //instanciation dynamique
        //il faut d'abord charger la classe en mémoire
        Class cDao = Class.forName(daoClassName); //Class est un objet qui contient la structure de la classe
        IDao dao = (IDao) cDao.getConstructor().newInstance(); // => new DaoImpl()

        //MetierImpl metier = new MetierImpl();
        String metierClassName = scanner.nextLine(); // on lit le nom de la classe metier
        Class cMetier = Class.forName(metierClassName); // on charge la classe metier en mémoire
        IMetier metier = (IMetier) cMetier.getConstructor().newInstance();  // on cree un objet de cette classe


        //metier.setDao(dao);
        Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
        setDao.invoke(metier, dao); //injection de dépendance

        System.out.println("Résultat:"+metier.calcul());
    }
}
