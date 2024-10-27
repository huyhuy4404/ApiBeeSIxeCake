package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Address;
import com.poly.apibeesixecake.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddress() {
        List<Address> addresses = addressService.getAllAddress();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{idaddress}")
    public ResponseEntity<?> getAddressById(@PathVariable Integer idaddress) {
        Address address = addressService.getAddressById(idaddress);
        if (address == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Địa chỉ không tồn tại.");
            }});
        }
        return ResponseEntity.ok(address);
    }

    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody Address address) {
        try {
            Address createdAddress = addressService.createAddress(address);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", "Tài khoản không tồn tại.");
            }});
        }
    }

    @PutMapping("/{idaddress}")
    public ResponseEntity<?> updateAddress(@PathVariable Integer idaddress, @RequestBody Address addressDetails) {
        try {
            Address updatedAddress = addressService.updateAddress(idaddress, addressDetails);
            if (updatedAddress == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                    put("error", "Địa chỉ không tồn tại.");
                }});
            }
            return ResponseEntity.ok(updatedAddress);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", "Tài khoản không tồn tại.");
            }});
        }
    }

    @DeleteMapping("/{idaddress}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer idaddress) {
        Address address = addressService.getAddressById(idaddress);
        if (address == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Địa chỉ không tồn tại.");
            }});
        }
        addressService.deleteAddress(idaddress);
        return ResponseEntity.ok(new HashMap<String, String>() {{
            put("message", "Xóa địa chỉ thành công.");
        }});
    }

    @GetMapping("/account/{idaccount}")
    public ResponseEntity<?> findByAccount_Idaccount(@PathVariable String idaccount) {
        try {
            List<Address> addresses = addressService.findByAccount_Idaccount(idaccount);
            return ResponseEntity.ok(addresses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Tài khoản không tồn tại.");
            }});
        }
    }
    @GetMapping("/account/{idaccount}/default")
    public ResponseEntity<?> getDefaultAddress(@PathVariable String idaccount) {
        Address defaultAddress = addressService.getDefaultAddressByAccountId(idaccount);
        if (defaultAddress == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Không tìm thấy địa chỉ mặc định cho tài khoản.");
            }});
        }
        return ResponseEntity.ok(defaultAddress);
    }
}
