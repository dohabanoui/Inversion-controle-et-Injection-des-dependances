package metier;

import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//MetierImpl il depend de l'interface IDao
//@Component
@Service //un alias de @Component
public class MetierImpl implements IMetier {
    //@Autowired //injection à la variable dao un objet de type IDao
    // @Qualifier("VWebServ") //injecter un objet de type IDao qui est annoté par @Component("VWebServ")
    private IDao dao;//couplage faible cad la classse metier depend directement de l'interface
    //cad la classe metier , il est pret à fonctionner avec n'importe quelle classe qui implemente l'interface IDao


    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double data = dao.getData();
        double res =  data * 23;
        return res;
    }

    //pour permettre d'injecter dans la variable dao
    //un objet d'une classe qui implemente l'interface IDao
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
