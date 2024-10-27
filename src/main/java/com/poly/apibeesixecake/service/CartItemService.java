package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.CartItem;
import com.poly.apibeesixecake.model.ProductDetail;
import com.poly.apibeesixecake.repository.CartItemRepository;
import com.poly.apibeesixecake.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;


    @Autowired
    private ProductDetailRepository productDetailRepository;

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem getCartItemById(Integer idcartitem) {
        return cartItemRepository.findById(idcartitem).orElse(null);
    }

    public List<CartItem> getCartItemsByIDaccount(String idaccount) {
        return cartItemRepository.findByAccount_Idaccount(idaccount);
    }

    public CartItem createCartItem(CartItem cartItem) {
        // Kiểm tra số lượng phải lớn hơn 0
        if (cartItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
        }

        ProductDetail productDetail = productDetailRepository.findById(cartItem.getProductdetail().getIdproductdetail())
                .orElseThrow(() -> new IllegalArgumentException("Chi tiết sản phẩm không tồn tại."));

        // Tính tổng số lượng đã đặt từ tất cả các tài khoản cho sản phẩm này
        List<CartItem> existingItems = cartItemRepository.findByProductdetail_Idproductdetail(cartItem.getProductdetail().getIdproductdetail());
        int totalQuantityOrdered = existingItems.stream().mapToInt(CartItem::getQuantity).sum();

        // Kiểm tra số lượng tối đa có thể thêm vào
        int availableStock = productDetail.getQuantityinstock() - totalQuantityOrdered; // Số lượng còn lại có thể đặt
        if (cartItem.getQuantity() > availableStock) {
            throw new IllegalArgumentException("Số lượng không được lớn hơn số lượng còn lại trong kho: " + availableStock);
        }

        // Kiểm tra xem mục giỏ hàng đã tồn tại chưa
        List<CartItem> userItems = cartItemRepository.findByAccount_IdaccountAndProductdetail_Idproductdetail(
                cartItem.getAccount().getIdaccount(), cartItem.getProductdetail().getIdproductdetail());

        if (!userItems.isEmpty()) {
            // Nếu đã tồn tại, cập nhật số lượng
            CartItem existingCartItem = userItems.get(0);
            int newQuantity = existingCartItem.getQuantity() + cartItem.getQuantity();

            // Kiểm tra lại số lượng trong kho
            if (newQuantity > availableStock) {
                throw new IllegalArgumentException("Số lượng không được lớn hơn số lượng còn lại trong kho: " + availableStock);
            }

            existingCartItem.setQuantity(newQuantity);
            existingCartItem.setTotal(newQuantity * productDetail.getUnitprice());
            return cartItemRepository.save(existingCartItem);
        }

        // Nếu chưa tồn tại, thêm mới
        cartItem.setProductdetail(productDetail);
        cartItem.setUnitprice(productDetail.getUnitprice());
        cartItem.setTotal(cartItem.getQuantity() * cartItem.getUnitprice());

        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(Integer idcartitem, CartItem cartItemDetails) {
        CartItem cartItem = cartItemRepository.findById(idcartitem)
                .orElseThrow(() -> new IllegalArgumentException("Mục giỏ hàng không tồn tại."));

        ProductDetail productDetail = productDetailRepository.findById(cartItemDetails.getProductdetail().getIdproductdetail())
                .orElseThrow(() -> new IllegalArgumentException("Chi tiết sản phẩm không tồn tại."));

        // Kiểm tra số lượng
        if (cartItemDetails.getQuantity() <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
        }

        // Kiểm tra số lượng không được lớn hơn số lượng trong kho
        if (cartItemDetails.getQuantity() > productDetail.getQuantityinstock()) {
            throw new IllegalArgumentException("Số lượng không được lớn hơn số lượng trong kho.");
        }

        // Cập nhật thông tin giỏ hàng
        cartItem.setQuantity(cartItemDetails.getQuantity());
        cartItem.setUnitprice(productDetail.getUnitprice());
        cartItem.setTotal(cartItem.getQuantity() * cartItem.getUnitprice());

        return cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(Integer idcartitem) {
        if (!cartItemRepository.existsById(idcartitem)) {
            throw new IllegalArgumentException("Mục giỏ hàng không tồn tại.");
        }
        cartItemRepository.deleteById(idcartitem);
    }
    public void updateCartItemsForExpiredProducts() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        for (CartItem cartItem : cartItems) {
            ProductDetail productDetail = productDetailRepository.findById(cartItem.getProductdetail().getIdproductdetail())
                    .orElseThrow(() -> new IllegalArgumentException("Chi tiết sản phẩm không tồn tại."));

            // Nếu sản phẩm hết hạn hoặc không còn trong kho, xử lý
            if (productDetail.getQuantityinstock() <= 0 || productDetail.getExpirationdate().isBefore(LocalDateTime.now())) {
                cartItem.setQuantity(0); // Hoặc xóa cartItem
                cartItemRepository.save(cartItem);
            }
        }
    }
}
