package com.example.order;

import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import com.example.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getAllOrders_returnsAllOrders() {
        Order o = new Order(1L, 2, new BigDecimal("199.98"));
        when(orderRepository.findAll()).thenReturn(List.of(o));

        List<Order> result = orderService.getAllOrders();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProductId()).isEqualTo(1L);
    }

    @Test
    void getOrderById_exists_returnsOrder() {
        Order o = new Order(2L, 1, new BigDecimal("499.99"));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(o));

        Optional<Order> result = orderService.getOrderById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getStatus()).isEqualTo("PENDING");
    }

    @Test
    void getOrderById_notFound_returnsEmpty() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    void createOrder_savesAndReturnsOrder() {
        Order o = new Order(3L, 4, new BigDecimal("79.96"));
        when(orderRepository.save(o)).thenReturn(o);

        Order result = orderService.createOrder(o);

        assertThat(result.getQuantity()).isEqualTo(4);
        verify(orderRepository, times(1)).save(o);
    }
}
