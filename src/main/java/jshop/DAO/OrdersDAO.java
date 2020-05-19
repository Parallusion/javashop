package jshop.DAO;
import jshop.model.Ordered;

import java.util.List;

public interface OrdersDAO {
    List<Ordered> allOrders();
    void save(Ordered ordered);
    void delete(Ordered ordered);
    void update(Ordered ordered);
    Ordered findById(int id);
}
