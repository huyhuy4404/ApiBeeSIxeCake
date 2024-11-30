package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Order;
import com.poly.apibeesixecake.model.OrderDetail;
import com.poly.apibeesixecake.model.ProductDetail;
import com.poly.apibeesixecake.repository.OrderDetailRepository;
import com.poly.apibeesixecake.repository.OrderRepository;
import com.poly.apibeesixecake.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    public OrderDetail getOrderDetailById(Integer idorderdetail) {
        return orderDetailRepository.findById(idorderdetail)
                .orElse(null);
    }

    public List<OrderDetail> getOrderDetailsByOrderId(Integer idorder) {
        return orderDetailRepository.findByOrder_Idorder(idorder);
    }

    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        Integer orderId = orderDetail.getOrder().getIdorder();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Đơn hàng không tồn tại."));

        Integer productDetailId = orderDetail.getProductdetail().getIdproductdetail();
        ProductDetail productDetail = productDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new IllegalArgumentException("Chi tiết sản phẩm không tồn tại."));

        // Kiểm tra số lượng tối đa có thể thêm vào
        if (orderDetail.getQuantity() > productDetail.getQuantityinstock()) {
            throw new IllegalArgumentException("Số lượng không được lớn hơn số lượng tồn kho.");
        }

        // Kiểm tra xem chi tiết đơn hàng đã tồn tại chưa
        List<OrderDetail> existingDetails = orderDetailRepository.findByOrder_Idorder(orderId);

        for (OrderDetail existingDetail : existingDetails) {
            if (existingDetail.getProductdetail().getIdproductdetail().equals(productDetailId)) {
                // Nếu đã tồn tại, cộng dồn số lượng
                int newQuantity = existingDetail.getQuantity() + orderDetail.getQuantity();
                if (newQuantity > productDetail.getQuantityinstock()) {
                    throw new IllegalArgumentException("Tổng số lượng không được lớn hơn số lượng tồn kho.");
                }
                existingDetail.setQuantity(newQuantity);
                return orderDetailRepository.save(existingDetail); // Cập nhật chi tiết đơn hàng
            }
        }

        // Nếu chưa tồn tại, thêm mới
        orderDetail.setOrder(order);
        orderDetail.setProductdetail(productDetail);
        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetail updateOrderDetail(Integer idorderdetail, OrderDetail orderDetailDetails) {
        // Tìm OrderDetail theo ID
        OrderDetail orderDetail = orderDetailRepository.findById(idorderdetail)
                .orElseThrow(() -> new IllegalArgumentException("Chi tiết đơn hàng không tồn tại."));

        // Chỉ cập nhật trường statusreview nếu orderDetailDetails.getStatusreview() != null
        if (orderDetailDetails.getStatusreview() != null) {
            orderDetail.setStatusreview(orderDetailDetails.getStatusreview());
        }

        // Kiểm tra các trường khác chỉ khi cần thiết
        if (orderDetailDetails.getOrder() != null) {
            Integer orderId = orderDetailDetails.getOrder().getIdorder();
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Đơn hàng không tồn tại."));
            orderDetail.setOrder(order);
        }

        if (orderDetailDetails.getProductdetail() != null) {
            Integer productDetailId = orderDetailDetails.getProductdetail().getIdproductdetail();
            ProductDetail productDetail = productDetailRepository.findById(productDetailId)
                    .orElseThrow(() -> new IllegalArgumentException("Chi tiết sản phẩm không tồn tại."));
            orderDetail.setProductdetail(productDetail);
        }

        // Kiểm tra số lượng trước khi cập nhật
        if (orderDetailDetails.getQuantity() != null) {
            if (orderDetailDetails.getQuantity() > orderDetail.getProductdetail().getQuantityinstock()) {
                throw new IllegalArgumentException("Số lượng không được lớn hơn số lượng tồn kho.");
            }
            orderDetail.setQuantity(orderDetailDetails.getQuantity());
        }

        // Lưu lại và trả về
        return orderDetailRepository.save(orderDetail);
    }


    public void deleteOrderDetail(Integer idorderdetail) {
        if (!orderDetailRepository.existsById(idorderdetail)) {
            throw new IllegalArgumentException("Chi tiết đơn hàng không tồn tại.");
        }
        orderDetailRepository.deleteById(idorderdetail);
    }
}
