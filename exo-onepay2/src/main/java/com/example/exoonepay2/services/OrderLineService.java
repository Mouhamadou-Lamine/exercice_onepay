package com.example.exoonepay2.services;

import com.example.exoonepay2.entities.OrderLine;
import com.example.exoonepay2.exception.AppException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderLineService {

    Object findById(Integer id);

    List<OrderLine> findAll();

    OrderLine save(OrderLine order) throws AppException;

}
