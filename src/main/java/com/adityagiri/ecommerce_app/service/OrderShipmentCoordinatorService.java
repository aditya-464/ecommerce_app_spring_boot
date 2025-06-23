package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.order.CreateOrUpdateOrCancelOrderResponseDTO;
import com.adityagiri.ecommerce_app.entity.Shipment;
import com.adityagiri.ecommerce_app.enums.OrderStatus;

public interface OrderShipmentCoordinatorService {
    //    OrderService methods
    CreateOrUpdateOrCancelOrderResponseDTO updateOrderStatus(String orderRefNumber, OrderStatus orderStatus);
    CreateOrUpdateOrCancelOrderResponseDTO updateShipmentObject(String orderRefNumber, Shipment shipment);

}
