package ext;

import dao.IDao;
import org.springframework.stereotype.Component;

//j'ai pas besois de modifier le code source
@Component("VCapteur")
public class DaoImplVCapteur implements IDao {
    @Override
    public double getData() {
        System.out.println("version VCapteur ");
        double data = 15;
        return data;
    }
}
