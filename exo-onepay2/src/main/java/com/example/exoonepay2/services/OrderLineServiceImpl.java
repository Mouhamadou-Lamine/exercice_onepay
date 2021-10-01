package com.example.exoonepay2.services;

import com.example.exoonepay2.dao.OrderLineRepository;
import com.example.exoonepay2.entities.OrderLine;
import com.example.exoonepay2.exception.AppException;
import com.example.exoonepay2.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderLineServiceImpl implements OrderLineService{

    @Autowired
    OrderLineRepository orderLineRepository;

    /**
     * Get order by id
     * @param id
     * @return
     */
    @Override
    public OrderLine findById(Integer id) {

        Optional optOrderLine = orderLineRepository.findById(id);

        if (optOrderLine.isPresent()){
            return (OrderLine) optOrderLine.get();
        } else {
            throw new NotFoundException("Order not found with id " + id);
        }
    }

    /**
     * Get all order
     * @return
     */
    @Override
    public List<OrderLine> findAll() {
        return orderLineRepository.findAll();
    }

    /**
     * Save new or update an order
     * @param order
     * @return
     * @throws AppException
     */
    @Override
    public OrderLine save(OrderLine order) throws AppException {
        return orderLineRepository.save(order);
    }
}
