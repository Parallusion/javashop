package jshop.services;

import jshop.model.Things;
import jshop.model.MyException;

import java.util.List;
import java.util.Map;

public interface ThingsService {
    List<Things> allThings();
    void save(Things things) throws MyException;
    void update(Things things) throws MyException;
    void delete(Things things) throws MyException;
    Things find(int id) throws MyException;
    boolean check(Things things, Map<Things, Integer> basketMap);
    Map<Things, Integer> restatement(Map<Things, Integer> basketMap, List<Integer> number);
    List<Things> basketDelete(Things things, List<Things> basketGoods);
}
