package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Discount;
import com.poly.apibeesixecake.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public Discount getDiscountById(Integer iddiscount) {
        return discountRepository.findById(iddiscount).orElse(null);
    }

    public Discount createDiscount(Discount discount) {
        if (discountCodeExists(discount.getDiscountcode())) {
            throw new IllegalArgumentException("Mã giảm giá đã tồn tại.");
        }
        return discountRepository.save(discount);
    }

    public Discount updateDiscount(Integer iddiscount, Discount discountDetails) {
        Discount existingDiscount = discountRepository.findById(iddiscount).orElse(null);
        if (existingDiscount != null) {
            if (discountCodeExists(discountDetails.getDiscountcode()) &&
                    !discountDetails.getDiscountcode().equalsIgnoreCase(existingDiscount.getDiscountcode())) {
                throw new IllegalArgumentException("Mã giảm giá đã tồn tại.");
            }
            existingDiscount.setDiscountcode(discountDetails.getDiscountcode());
            existingDiscount.setDiscountpercentage(discountDetails.getDiscountpercentage());
            existingDiscount.setStartdate(discountDetails.getStartdate());
            existingDiscount.setEnddate(discountDetails.getEnddate());
            return discountRepository.save(existingDiscount);
        }
        return null;
    }

    public void deleteDiscount(Integer iddiscount) {
        if (!discountRepository.existsById(iddiscount)) {
            throw new IllegalArgumentException("Giảm giá không tồn tại.");
        }
        discountRepository.deleteById(iddiscount);
    }

    private boolean discountCodeExists(String discountCode) {
        return discountRepository.findAll().stream()
                .anyMatch(d -> d.getDiscountcode().equalsIgnoreCase(discountCode));
    }
    public Discount getDiscountByCode(String discountCode) {
        return discountRepository.findAll().stream()
                .filter(d -> d.getDiscountcode().equalsIgnoreCase(discountCode))
                .findFirst()
                .orElse(null);
    }
}
