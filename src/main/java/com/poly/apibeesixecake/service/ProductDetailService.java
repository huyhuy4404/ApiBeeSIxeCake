package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Product;
import com.poly.apibeesixecake.model.ProductDetail;
import com.poly.apibeesixecake.model.Size;
import com.poly.apibeesixecake.repository.ProductDetailRepository;
import com.poly.apibeesixecake.repository.ProductRepository;
import com.poly.apibeesixecake.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SizeRepository sizeRepository;

    public List<ProductDetail> getAllProductDetails() {
        return productDetailRepository.findAll();
    }

    public ProductDetail getProductDetailById(Integer idproductdetail) {
        return productDetailRepository.findById(idproductdetail).orElse(null);
    }

    public ProductDetail createProductDetail(ProductDetail productDetail) {
        Integer productId = productDetail.getProduct().getIdproduct();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại."));

        Integer sizeId = productDetail.getSize().getIdsize();
        Size size = sizeRepository.findById(sizeId)
                .orElseThrow(() -> new IllegalArgumentException("Kích thước không tồn tại."));

        List<ProductDetail> existingProductDetails = productDetailRepository.findByProduct_Idproduct(productId);
        for (ProductDetail existing : existingProductDetails) {
            if (existing.getSize().getIdsize().equals(sizeId)) {
                throw new IllegalArgumentException("Sản phẩm với kích thước này đã tồn tại.");
            }
        }

        productDetail.setProduct(product);
        productDetail.setSize(size);
        return productDetailRepository.save(productDetail);
    }

    public ProductDetail updateProductDetail(Integer idproductdetail, ProductDetail productDetailDetails) {
        ProductDetail productDetail = productDetailRepository.findById(idproductdetail)
                .orElseThrow(() -> new IllegalArgumentException("Chi tiết sản phẩm không tồn tại."));

        // Kiểm tra xem đơn giá và số lượng tồn kho có hợp lệ không
        if (productDetailDetails.getUnitprice() == null || productDetailDetails.getUnitprice() <= 0) {
            throw new IllegalArgumentException("Đơn giá phải là một số dương hợp lệ.");
        }
        if (productDetailDetails.getQuantityinstock() == null || productDetailDetails.getQuantityinstock() < 0) {
            throw new IllegalArgumentException("Số lượng tồn kho phải là số dương hợp lệ.");
        }

        Integer productId = productDetailDetails.getProduct().getIdproduct();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại."));

        Integer sizeId = productDetailDetails.getSize().getIdsize();
        Size size = sizeRepository.findById(sizeId)
                .orElseThrow(() -> new IllegalArgumentException("Kích thước không tồn tại."));

        List<ProductDetail> existingProductDetails = productDetailRepository.findByProduct_Idproduct(productId);
        for (ProductDetail existing : existingProductDetails) {
            if (!existing.getIdproductdetail().equals(idproductdetail) && existing.getSize().getIdsize().equals(sizeId)) {
                throw new IllegalArgumentException("Sản phẩm với kích thước này đã tồn tại.");
            }
        }

        productDetail.setUnitprice(productDetailDetails.getUnitprice());
        productDetail.setQuantityinstock(productDetailDetails.getQuantityinstock());
        productDetail.setProduct(product);
        productDetail.setSize(size);

        return productDetailRepository.save(productDetail);
    }

    public void deleteProductDetail(Integer idproductdetail) {
        if (!productDetailRepository.existsById(idproductdetail)) {
            throw new IllegalArgumentException("Chi tiết sản phẩm không tồn tại.");
        }
        productDetailRepository.deleteById(idproductdetail);
    }

    public List<ProductDetail> getProductDetailsByProductId(Integer idproduct) {
        return productDetailRepository.findByProduct_Idproduct(idproduct);
    }
}
