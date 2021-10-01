package com.example.exoonepay2.controller;

import com.example.exoonepay2.entities.OrderLine;
import com.example.exoonepay2.exception.AppException;
import com.example.exoonepay2.services.OrderLineServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderLineController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderLineController.class);

    @Autowired
    OrderLineServiceImpl orderLineService;

    /**
     * get by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public OrderLine getById(@PathVariable Integer id){
        return orderLineService.findById(id);
    }

    /**
     * get all order
     * @return
     */
    @GetMapping
    public List<OrderLine> getAll() {
        return orderLineService.findAll();
    }

    /**
     * add order
     * @param order
     * @return
     */
    @PostMapping
    public OrderLine createOrder(@RequestBody OrderLine order) throws AppException {
        try {
            return orderLineService.save(order);
        } catch (AppException e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

    }

    /**
     * Update order
     * @param order
     * @return
     * @throws AppException
     */
    @PutMapping
    public OrderLine updateOrder(@RequestBody OrderLine order) throws AppException {

        try {
            return orderLineService.save(order);
        }catch (AppException e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

    }
}
