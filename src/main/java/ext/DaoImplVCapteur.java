package ext;

import dao.IDao;
//j'ai pas besois de modifier le code source
public class DaoImplVCapteur implements IDao {
    @Override
    public double getData() {
        System.out.println("version VCapteur ");
        double data = 15;
        return data;
    }
}
