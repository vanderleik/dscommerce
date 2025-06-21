package com.api.dscommerce.services;

import com.api.dscommerce.dto.OrderDTO;
import com.api.dscommerce.dto.OrderItemDTO;
import com.api.dscommerce.entities.*;
import com.api.dscommerce.repositories.OrderItemRepository;
import com.api.dscommerce.repositories.OrderRepository;
import com.api.dscommerce.repositories.ProductRepository;
import com.api.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository  productRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService, ProductRepository  productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }


    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order result = orderRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Recurso não encontrado"));

        return new OrderDTO(result);

    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAIMENT);

        User user = userService.authenticated();
        order.setClient(user);

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());

            order.getItems().add(item);
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }
}
