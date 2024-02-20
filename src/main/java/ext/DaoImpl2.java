package ext;
import dao.IDao;
import org.springframework.stereotype.Component;

@Component("VWebServ")
public class DaoImpl2 implements IDao {
    @Override
    public double getData() {
        System.out.println("version wweb service ");
        double data = 12;
        return data;
    }
}
