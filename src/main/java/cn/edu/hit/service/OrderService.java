package cn.edu.hit.service;

import cn.edu.hit.po.*;

import java.util.Map;

public interface OrderService {

    Integer addOrder(Order order, User user, Cart cart);

    ProductExt<OrderExt> selallOrder(Integer getuId, ProductExt<OrderExt> productExt);

    Map<Long, Integer> selState(Integer getuId);

    Integer updateState(Order order);

    OrderExt selAll(Integer oId);

    void upId(Integer oId);
}
