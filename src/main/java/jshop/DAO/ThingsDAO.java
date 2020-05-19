package jshop.DAO;
import jshop.model.Things;

import java.util.List;

public interface ThingsDAO {
    List<Things> allThings();
    void save(Things things);
    void delete(Things things);
    void update(Things things);
    Things findById(int id);
}
