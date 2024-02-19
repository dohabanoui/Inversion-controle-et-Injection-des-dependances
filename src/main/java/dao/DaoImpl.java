package dao;

public class DaoImpl implements IDao {// version base de donnes
    public double getData() {
        System.out.println("version base de donnes ");
        double data = 10.0;
        return data;
    }
}
