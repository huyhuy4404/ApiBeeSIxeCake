package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Address;
import com.poly.apibeesixecake.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<Address> getAllAddress() {
        return addressService.getAllAddress();
    }

    @GetMapping("/{idaddress}")
    public ResponseEntity<?> getAddressById(@PathVariable Integer idaddress) {
        Address address = addressService.getAddressById(idaddress);
        if (address == null) {
            return new ResponseEntity<>("Địa chỉ không tồn tại.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody Address address) {
        try {
            Address createdAddress = addressService.createAddress(address);
            return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Tài khoản không tồn tại.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idaddress}")
    public ResponseEntity<?> updateAddress(@PathVariable Integer idaddress, @RequestBody Address addressDetails) {
        try {
            Address updatedAddress = addressService.updateAddress(idaddress, addressDetails);
            if (updatedAddress == null) {
                return new ResponseEntity<>("Địa chỉ không tồn tại.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Tài khoản không tồn tại.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idaddress}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer idaddress) {
        Address address = addressService.getAddressById(idaddress);
        if (address == null) {
            return new ResponseEntity<>("Địa chỉ không tồn tại.", HttpStatus.NOT_FOUND);
        }
        addressService.deleteAddress(idaddress);
        return new ResponseEntity<>("Xóa địa chỉ thành công.", HttpStatus.OK);
    }

    @GetMapping("/account/{idaccount}")
    public ResponseEntity<?> findByAccount_Idaccount(@PathVariable String idaccount) {
        try {
            List<Address> addresses = addressService.findByAccount_Idaccount(idaccount);
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Tài khoản không tồn tại.", HttpStatus.NOT_FOUND);
        }
    }
}
