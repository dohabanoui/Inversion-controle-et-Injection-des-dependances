package dao;
//annotation
//cree moi un objet de cette classe au demarrage
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Component("dao")
@Repository("dao") //un alias de @Component
public class DaoImpl implements IDao {// version base de donnes
    public double getData() {

        System.out.println("version base de donnes ");
        double data = 10.0;
        return data;
    }
}
