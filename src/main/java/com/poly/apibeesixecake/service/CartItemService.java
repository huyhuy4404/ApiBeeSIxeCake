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
    public List<CartItem> findByProductDetailId(Integer idproductdetail) {
        return cartItemRepository.findByProductdetail_Idproductdetail(idproductdetail);
    }

    public CartItem createCartItem(CartItem cartItem) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartItem.getShoppingcart().getIdshoppingcart()).orElse(null);
        if (shoppingCart == null) {
            throw new IllegalArgumentException("Giỏ hàng không tồn tại.");
        }

        ProductDetail productDetail = productDetailRepository.findById(cartItem.getProductdetail().getIdproductdetail()).orElse(null);
        if (productDetail == null) {
            throw new IllegalArgumentException("Sản phẩm chi tiết không tồn tại.");
        }
        if (cartItem.getQuantity() > productDetail.getQuantityinstock()) {
            throw new IllegalArgumentException("Số lượng trong giỏ hàng không được vượt quá số lượng có sẵn trong kho.");
        }
        cartItem.setShoppingcart(shoppingCart);
        cartItem.setProductdetail(productDetail);
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(Integer idcartitem, CartItem cartItemDetails) {
        CartItem cartItem = cartItemRepository.findById(idcartitem).orElse(null);
        if (cartItem != null) {
            ShoppingCart shoppingCart = shoppingCartRepository.findById(cartItemDetails.getShoppingcart().getIdshoppingcart()).orElse(null);
            if (shoppingCart == null) {
                throw new IllegalArgumentException("Giỏ hàng không tồn tại.");
            }

            ProductDetail productDetail = productDetailRepository.findById(cartItemDetails.getProductdetail().getIdproductdetail()).orElse(null);
            if (productDetail == null) {
                throw new IllegalArgumentException("Sản phẩm chi tiết không tồn tại.");
            }
            if (cartItemDetails.getQuantity() > productDetail.getQuantityinstock()) {
                throw new IllegalArgumentException("Số lượng trong giỏ hàng không được vượt quá số lượng có sẵn trong kho.");
            }

            cartItem.setQuantity(cartItemDetails.getQuantity());
            cartItem.setShoppingcart(shoppingCart);
            cartItem.setProductdetail(productDetail);
            return cartItemRepository.save(cartItem);
        }
        return null;
    }

    public void deleteCartItem(Integer idcartitem) {
        if (!cartItemRepository.existsById(idcartitem)) {
            throw new IllegalArgumentException("Mục giỏ hàng không tồn tại.");
        }
        cartItemRepository.deleteById(idcartitem);
    }

    public List<CartItem> findByShoppingCartId(Integer idshoppingcart) {
        return cartItemRepository.findByShoppingcart_Idshoppingcart(idshoppingcart);
    }
    public void deleteByProductDetailId(Integer idproductdetail) {
        List<CartItem> cartItems = cartItemRepository.findByProductdetail_Idproductdetail(idproductdetail);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy mặt hàng này.");
        }
        cartItemRepository.deleteAll(cartItems);
    }
}
