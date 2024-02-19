package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class pres22 {
    // on utilise le couplage faible(depend que des interfaces)
    // on utilise l'instanciation dynamique
    public static void main(String[] args) throws Exception{
        //DaoImpl2 dao = new DaoImpl2(); // instanciation statique
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez saisir le nom de la classe Dao : ");
        /**
         *  dao.DaoImpl
         *  ext.DaoImpl2
         *  ext.DaoImplVCapteur
         */
        String daoClassName = scanner.nextLine();
        Class cDao = Class.forName(daoClassName);
        IDao dao = (IDao) cDao.getConstructor().newInstance(); // => new DaoImpl()

        //MetierImpl metier = new MetierImpl();
        System.out.println("Veuillez saisir le nom de la classe Metier : ");
         //  metier.MetierImpl
        String metierClassName = scanner.nextLine();
        Class cMetier = Class.forName(metierClassName);
        IMetier metier = (IMetier) cMetier.getConstructor().newInstance();  // on cree un objet de cette classe


        //metier.setDao(dao);
        Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
        setDao.invoke(metier, dao); //injection de dépendance

        System.out.println("Résultat:"+metier.calcul());
    }
}
