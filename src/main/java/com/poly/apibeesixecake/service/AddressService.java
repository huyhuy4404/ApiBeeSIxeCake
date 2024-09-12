package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Account;
import com.poly.apibeesixecake.model.Address;
import com.poly.apibeesixecake.repository.AccountRepository;
import com.poly.apibeesixecake.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Integer idaddress) {
        return addressRepository.findById(idaddress).orElse(null);
    }

    public Address createAddress(Address address) {
        Account account = accountRepository.findById(address.getAccount().getIdaccount()).orElse(null);
        if (account == null) {
            throw new IllegalArgumentException("Tài khoản không tồn tại.");
        }
        address.setAccount(account);
        return addressRepository.save(address);
    }

    public Address updateAddress(Integer idaddress, Address addressDetails) {
        Address address = addressRepository.findById(idaddress).orElse(null);
        if (address != null) {
            Account account = accountRepository.findById(addressDetails.getAccount().getIdaccount()).orElse(null);
            if (account == null) {
                throw new IllegalArgumentException("Tài khoản không tồn tại.");
            }
            address.setAccount(account);
            address.setHousenumber(addressDetails.getHousenumber());
            address.setRoadname(addressDetails.getRoadname());
            address.setWard(addressDetails.getWard());
            address.setDistrict(addressDetails.getDistrict());
            address.setCity(addressDetails.getCity());
            return addressRepository.save(address);
        }
        return null;
    }

    public void deleteAddress(Integer idaddress) {
        addressRepository.deleteById(idaddress);
    }

    public List<Address> findByAccount_Idaccount(String idaccount) {
        Account account = accountRepository.findById(idaccount).orElse(null);
        if (account == null) {
            throw new IllegalArgumentException("Tài khoản không tồn tại.");
        }
        return addressRepository.findByAccount_Idaccount(idaccount);
    }
}
