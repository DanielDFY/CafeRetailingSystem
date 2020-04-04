package fudan.se.lab5.service;

import fudan.se.lab5.dto.Order;
import fudan.se.lab5.dto.PaymentInfo;

public interface OrderService {
    PaymentInfo pay(Order order);
}
