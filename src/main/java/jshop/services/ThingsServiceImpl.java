package jshop.services;

import jshop.DAO.ThingsDAO;
import jshop.DAO.ThingsDAOImpl;
import jshop.model.Things;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//прослойка между контроллером и DAO (работой с БД)
@Service
public class ThingsServiceImpl implements ThingsService {
    private ThingsDAO thingsDAO; //создаём экземпляр ДАО

    @Autowired
    public void setThingsDAO(ThingsDAOImpl thingsDAO) {
        this.thingsDAO = thingsDAO;
    } //инициализируем

    public ThingsServiceImpl() {
    }
//перевызов методов из DAO
    @Override
    @Transactional //метод работает с БД (обмен с БД данными)
    public List<Things> allThings() {
        return thingsDAO.allThings();
    }

    @Transactional
    public void save(Things things){
        thingsDAO.save(things);
    }

    @Transactional
    public void delete(Things things) {
        thingsDAO.delete(things);
    }

    @Transactional
    public void update(Things things){
        thingsDAO.update(things);
    }

    @Transactional
    public Things find(int id) {
        return thingsDAO.findById(id);
    }

    //бизнесс-логика
    //проверка при добавлении в корзину на дубликаты продуктов-вещей
    public boolean check(Things things, Map<Things, Integer> basketMap) {

        for (Map.Entry item: basketMap.entrySet()) {
            Things g = (Things) item.getKey();
            if (things.getName().equals(g.getName()))
                return true;
        }
        return false;
    }

    //пересчёт цены в зависимости от количества товара
    public Map<Things, Integer> restatement(Map<Things, Integer> basketMap, List<Integer> number){
        Map<Things, Integer> tmp = new LinkedHashMap<>();
        int i = 0;
        for (Map.Entry item: basketMap.entrySet()) {
            tmp.put((Things) item.getKey(), number.get(i));
            i++;
        }
        return tmp;
    }

    //удаление из корзины товар (очищаем view)
    public List<Things> basketDelete(Things things, List<Things> basketThings){
        List<Things> tmp = new ArrayList<>();
        for (Things g: basketThings) {
            if (!g.getName().equals(things.getName()))
                tmp.add(g);
        }
        return tmp;
    }
}
