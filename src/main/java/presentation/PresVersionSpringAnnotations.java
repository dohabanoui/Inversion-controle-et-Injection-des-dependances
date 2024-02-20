package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresVersionSpringAnnotations {
    public static void main(String[] args) {
        //version annotation
        ApplicationContext springContext = new AnnotationConfigApplicationContext("dao","metier"); //ce sont des packages
        IMetier metier = springContext.getBean(IMetier.class);
        System.out.println("result = " + metier.calcul());

    }
}
