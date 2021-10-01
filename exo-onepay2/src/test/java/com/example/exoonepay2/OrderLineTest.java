package com.example.exoonepay2;

import com.example.exoonepay2.entities.OrderLine;
import com.example.exoonepay2.services.OrderLineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertTrue;

@SpringBootTest
public class OrderLineTest {

    @Autowired
    OrderLineService orderLineService;

    /**
     * test get all orders
     */
    @Test
    public void testGetAllOrders(){

        List<OrderLine> orderLines = orderLineService.findAll();

        assertTrue(orderLines.size() > 0);
    }

    /**
     * test get order by ID
     */
    @Test
    public void testGetOrderById (){
        Integer id = 72;
        OrderLine orderLine = (OrderLine) orderLineService.findById(72);

        assertTrue(orderLine.getId().equals(id));
    }
}
