package jshop.services;

import jshop.DAO.OrdersDAO;
import jshop.model.Ordered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    private OrdersDAO ordersDAO;

    @Autowired
    public void setOrdersDao(OrdersDAO ordersDAO) {
        this.ordersDAO = ordersDAO;
    }

    public OrdersServiceImpl() {
    }

    @Override
    @Transactional
    public List<Ordered> allOrders() {
        return ordersDAO.allOrders();
    }

    @Override
    @Transactional
    public void save(Ordered ordered){
        ordersDAO.save(ordered);
    }

    @Override
    @Transactional
    public void delete(Ordered ordered) {
        ordersDAO.delete(ordered);
    }

    @Override
    @Transactional
    public void update(Ordered ordered) {
        ordersDAO.update(ordered);
    }

    @Override
    @Transactional
    public Ordered find(int id){
        return ordersDAO.findById(id);
    }

    //склеивание в одну строку купленные товары через запятую для БД
    public String thingsNames(List<String> things){
        String s = "";
        for (String name: things) {
            s += name + ", ";
        }
        s = s.substring(0, s.length() - 2);
        return s;
    }

    //склеивание количества каждого купленного товара через запятую в БД
    public String thingsNumber(List<Integer> value){
        String s = "";
        for (Integer v: value) {
            s += v + ", ";
        }
        s = s.substring(0, s.length() - 2);
        return s;
    }
}
