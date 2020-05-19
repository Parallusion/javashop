package jshop.services;

import jshop.model.MyException;
import jshop.model.Ordered;

import java.util.List;

public interface OrdersService {
    List<Ordered> allOrders();
    void save(Ordered ordered) throws MyException;
    void delete(Ordered ordered) throws MyException;
    void update(Ordered ordered) throws MyException;
    Ordered find(int id) throws MyException;
    String thingsNames(List<String> things);
    String thingsNumber(List<Integer> value);
}
