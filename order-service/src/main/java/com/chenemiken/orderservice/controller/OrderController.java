package com.chenemiken.orderservice.controller;

import com.chenemiken.basedomains.dto.Order;
import com.chenemiken.basedomains.dto.OrderEvent;
import com.chenemiken.orderservice.kafka.OrderProducer;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

  private OrderProducer orderProducer;

  public OrderController(OrderProducer orderProducer){
    this.orderProducer = orderProducer;
  }

  @PostMapping("/orders")
  public String placeOrder(@RequestBody Order order){
    order.setId(UUID.randomUUID().toString());

    OrderEvent orderEvent = new OrderEvent();
    orderEvent.setStatus("PENDING");
    orderEvent.setMessage("order status is in pending state");
    orderEvent.setOrder(order);

    orderProducer.sendMessage(orderEvent);

    return "Order placed successfully ...";
  }
}
