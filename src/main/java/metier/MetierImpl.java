package metier;

import dao.IDao;

//MetierImpl il depend de l'interface IDao
public class MetierImpl implements IMetier {
    private IDao dao;//couplage faible cad la classse metier depend directement de l'interface
    //cad la classe metier , il est pret à fonctionner avec n'importe quelle classe qui implemente l'interface IDao
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
