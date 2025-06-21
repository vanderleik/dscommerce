package com.api.dscommerce.services;

import com.api.dscommerce.dto.OrderDTO;
import com.api.dscommerce.entities.Order;
import com.api.dscommerce.repositories.OrderRepository;
import com.api.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private static OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order result = orderRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Recurso não encontrado"));

        return new OrderDTO(result);

    }

}
