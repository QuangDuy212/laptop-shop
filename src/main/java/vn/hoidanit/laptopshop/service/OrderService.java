package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public void saveOrder(Order order) {
        this.orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    public Optional<Order> getOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public void handleUpdateOrder(Optional<Order> updateOrder, Order order) {
        if (updateOrder.isPresent()) {
            updateOrder.get().setStatus(order.getStatus());
            this.orderRepository.save(updateOrder.get());
        }
    }

    public void handleDeleteAOrder(long id, List<OrderDetail> orderDetails) {
        Optional<Order> order = this.orderRepository.findById(id);
        if (order.isPresent()) {
            // step 1 : delete order detail
            for (OrderDetail od : orderDetails) {
                this.orderDetailRepository.delete(od);
            }
            // step 2 : delete order
            this.orderRepository.deleteById(id);
        }
    }

    public long countOrders() {
        List<Order> orders = this.getAllOrders();
        long count = orders.size();
        return count;
    }

    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findAllByUser(user);
    }
}
