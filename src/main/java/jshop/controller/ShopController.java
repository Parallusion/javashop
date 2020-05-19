package jshop.controller;

import jshop.model.Buyer;
import jshop.model.Things;
import jshop.model.MyException;
import jshop.model.Ordered;
import jshop.services.ThingsService;
import jshop.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShopController {

    ThingsService thingsService;
    OrdersService ordersService;
    Buyer buyer;
    List<Things> basketThings = new ArrayList<Things>();
    Map<Things, Integer> basketMap = new LinkedHashMap<>();

    @Autowired
    public void setThingsService(ThingsService thingsService) {
        this.thingsService = thingsService;
    }

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    //задаём ссылку по которой работает метод GET со startPage
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView startPage() {
        ModelAndView modelAndView = new ModelAndView(); //jsp страницы
        modelAndView.setViewName("redirect:/things"); //задаёт адрес страницы
        modelAndView.addObject("buyer", buyer); //помещаем элемент на страницу
        return modelAndView; //возвращаем страничку и мы видим сайт
    }

    @RequestMapping(value = "/things", method = RequestMethod.GET)
    public ModelAndView allThings() { //отображение товаров на странице
        List<Things> things = thingsService.allThings();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("things");
        modelAndView.addObject("thingsList", things);
        modelAndView.addObject("buyer", buyer);
        return modelAndView;
    }

    @RequestMapping(value = "/things/addThings", method = RequestMethod.GET)
    public ModelAndView addPage() { //страница добавления товара
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addThings");
        return modelAndView;
    }

    //POST (сабмитим введенные данные)
    @RequestMapping(value = "/things/addThings", method = RequestMethod.POST)
    public ModelAndView addThings(@ModelAttribute("things") Things things) throws MyException { //МоделАтрибут - указываем, что мы получаем от браузера в код, сумма полей составляет экземпляр класса Things
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/things");
        thingsService.save(things);
        return modelAndView;
    }

    @RequestMapping(value="/things/delete/{id}", method = RequestMethod.GET) //удаление товара по id
    public ModelAndView deleteThings(@PathVariable("id") int id) throws MyException { //удаление товара с магазина (БД) из Things
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/things");
        Things things = thingsService.find(id);
        thingsService.delete(things);
        return modelAndView;
    }

    @RequestMapping(value = "/addProfile", method = RequestMethod.GET) //добавление данных о пользователе
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addProfile");
        buyer = new Buyer();
        modelAndView.addObject("buyer", buyer); //закидываем на страницу пользователя как объект
        return modelAndView;
    }

    @RequestMapping(value = "/addProfile", method = RequestMethod.POST) //создать покупателя
    public ModelAndView addProfile(@ModelAttribute("newBuyer") Buyer newBuyer) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/profile");
        buyer = newBuyer;
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET) //страница отображения заполненных данных
    public ModelAndView buyerProfile() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("buyer", buyer);
        return modelAndView;
    }

    @RequestMapping(value="/things/addBasket/{id}", method = RequestMethod.GET) //добавление товара в корзину (так же как и Delete, только Save)
    public ModelAndView addBasket(@PathVariable("id") int id) throws MyException {
        Things things = thingsService.find(id);

        if (!thingsService.check(things, basketMap))
            basketMap.put(things, 1); //добавление товар, количество

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/things");
        return modelAndView;
    }

    @RequestMapping(value = "/basket", method = RequestMethod.GET) //отображение корзины
    public ModelAndView basket() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("basket");
        modelAndView.addObject("buyer", buyer);
        modelAndView.addObject("basketMap", basketMap);
        return modelAndView;
    }

    @RequestMapping(value="/basket/delete/{id}", method = RequestMethod.GET) //удаление из корзины
    public ModelAndView deleteBasket(@PathVariable("id") int id) throws MyException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/basket");
        Things things = thingsService.find(id);
        basketThings = thingsService.basketDelete(things, basketThings);
        return modelAndView;
    }

    @RequestMapping(value = "/basket", method = RequestMethod.POST)  //пересчет цены товара по отношению к количеству
    public ModelAndView refresh(@RequestParam List<Integer> number) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("basket");
        basketMap = thingsService.restatement(basketMap, number);
        modelAndView.addObject("buyer", buyer);
        modelAndView.addObject("basketMap", basketMap);
        return modelAndView;
    }

    @RequestMapping(value = "/ordered/confirm", method = RequestMethod.GET) //при заполненном профиле добавляем в корзину оформляется заказ
    public ModelAndView confirmOrder() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orderConfirm");

        modelAndView.addObject("buyer", buyer);
        modelAndView.addObject("basketMap", basketMap);
        return modelAndView;
    }

    @RequestMapping(value = "/ordered/confirm", method = RequestMethod.POST)
    public ModelAndView placeOrder(
            @RequestParam List<String> things //RequestParam - вытаскивает элемент со страницы по указанному имени
            , @RequestParam String email
            , @RequestParam String delivery
            , @RequestParam String phone
            , @RequestParam String name
            , @RequestParam List<Integer> value
            , @RequestParam Double total) throws MyException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/ordered");
        String names = ordersService.thingsNames(things);
        String number = ordersService.thingsNumber(value);
        Ordered ordered = new Ordered(); //создаём заказ со всеми данными
        ordered.setName(name);
        ordered.setEmail(email);
        ordered.setDelivery(delivery);
        ordered.setPhone(phone);
        ordered.setThings(names);
        ordered.setNumber(number);
        ordered.setSum(total);
        ordersService.save(ordered); //сохраняем заказ в БД
        return modelAndView;
    }
    @RequestMapping(value = "/ordered", method = RequestMethod.GET) //страница отображения заказов сделанных на сайте
    public ModelAndView ordered() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ordered");
        List<Ordered> ordereds = ordersService.allOrders();
        modelAndView.addObject("buyer", buyer);
        modelAndView.addObject("ordered", ordereds);
        return modelAndView;
    }
}
