package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.CartItem;
import com.poly.apibeesixecake.model.ProductDetail;
import com.poly.apibeesixecake.model.ShoppingCart;
import com.poly.apibeesixecake.repository.CartItemRepository;
import com.poly.apibeesixecake.repository.ProductDetailRepository;
import com.poly.apibeesixecake.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem getCartItemById(Integer idcartitem) {
        return cartItemRepository.findById(idcartitem).orElse(null);
    }

    public List<CartItem> getCartItemsByShoppingCartId(Integer idshoppingcart) {
        return cartItemRepository.findByShoppingcart_Idshoppingcart(idshoppingcart);
    }

    public CartItem createCartItem(CartItem cartItem) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartItem.getShoppingcart().getIdshoppingcart())
                .orElseThrow(() -> new IllegalArgumentException("Giỏ hàng không tồn tại."));

        ProductDetail productDetail = productDetailRepository.findById(cartItem.getProductdetail().getIdproductdetail())
                .orElseThrow(() -> new IllegalArgumentException("Chi tiết sản phẩm không tồn tại."));

        // Kiểm tra số lượng tối đa có thể thêm vào
        if (cartItem.getQuantity() > productDetail.getQuantityinstock()) {
            throw new IllegalArgumentException("Số lượng không được lớn hơn số lượng trong kho.");
        }

        // Kiểm tra xem mục giỏ hàng đã tồn tại chưa
        List<CartItem> existingItems = cartItemRepository.findByShoppingcart_Idshoppingcart(shoppingCart.getIdshoppingcart());

        for (CartItem existingItem : existingItems) {
            if (existingItem.getProductdetail().getIdproductdetail().equals(cartItem.getProductdetail().getIdproductdetail())) {
                // Nếu đã tồn tại, cộng dồn số lượng
                int newQuantity = existingItem.getQuantity() + cartItem.getQuantity();
                if (newQuantity > productDetail.getQuantityinstock()) {
                    throw new IllegalArgumentException("Tổng số lượng không được lớn hơn số lượng trong kho.");
                }
                existingItem.setQuantity(newQuantity);
                existingItem.setTotal(newQuantity * existingItem.getUnitprice());
                return cartItemRepository.save(existingItem); // Cập nhật giỏ hàng
            }
        }

        // Nếu chưa tồn tại, thêm mới
        cartItem.setShoppingcart(shoppingCart);
        cartItem.setProductdetail(productDetail);
        cartItem.setUnitprice(productDetail.getUnitprice());
        cartItem.setTotal(cartItem.getQuantity() * cartItem.getUnitprice());

        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(Integer idcartitem, CartItem cartItemDetails) {
        CartItem cartItem = cartItemRepository.findById(idcartitem)
                .orElseThrow(() -> new IllegalArgumentException("Mục giỏ hàng không tồn tại."));

        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartItemDetails.getShoppingcart().getIdshoppingcart())
                .orElseThrow(() -> new IllegalArgumentException("Giỏ hàng không tồn tại."));


        ProductDetail productDetail = productDetailRepository.findById(cartItemDetails.getProductdetail().getIdproductdetail())
                .orElseThrow(() -> new IllegalArgumentException("Chi tiết sản phẩm không tồn tại."));

        if (cartItemDetails.getQuantity() > productDetail.getQuantityinstock()) {
            throw new IllegalArgumentException("Số lượng không được lớn hơn số lượng trong kho.");
        }

        cartItem.setShoppingcart(shoppingCart);
        cartItem.setProductdetail(productDetail);
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
}
