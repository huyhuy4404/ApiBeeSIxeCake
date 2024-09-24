package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Discount;
import com.poly.apibeesixecake.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        List<Discount> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/{iddiscount}")
    public ResponseEntity<?> getDiscountById(@PathVariable Integer iddiscount) {
        Discount discount = discountService.getDiscountById(iddiscount);
        if (discount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Giảm giá không tồn tại.");
            }});
        }
        return ResponseEntity.ok(discount);
    }

    @PostMapping
    public ResponseEntity<?> createDiscount(@RequestBody Discount discount) {
        try {
            Discount createdDiscount = discountService.createDiscount(discount);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{iddiscount}")
    public ResponseEntity<?> updateDiscount(@PathVariable Integer iddiscount, @RequestBody Discount discountDetails) {
        try {
            Discount updatedDiscount = discountService.updateDiscount(iddiscount, discountDetails);
            if (updatedDiscount == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                    put("error", "Giảm giá không tồn tại.");
                }});
            }
            return ResponseEntity.ok(updatedDiscount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{iddiscount}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Integer iddiscount) {
        try {
            discountService.deleteDiscount(iddiscount);
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "Xóa thành công.");
            }});
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }
    @GetMapping("/code/{discountcode}")
    public ResponseEntity<?> getDiscountByCode(@PathVariable String discountcode) {
        Discount discount = discountService.getDiscountByCode(discountcode);
        if (discount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Giảm giá không tồn tại.");
            }});
        }
        return ResponseEntity.ok(discount);
    }
}
