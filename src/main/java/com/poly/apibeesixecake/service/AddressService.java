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

        // Nếu chưa có địa chỉ nào, set isDefault = true
        if (addressRepository.findByAccount_Idaccount(address.getAccount().getIdaccount()).isEmpty()) {
            address.setIsDefault(true);
        } else {
            address.setIsDefault(false);
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

            // Nếu địa chỉ được cập nhật thành địa chỉ mặc định
            if (addressDetails.getIsDefault() != null && addressDetails.getIsDefault()) {
                // Đặt tất cả các địa chỉ khác thành không mặc định
                List<Address> addresses = addressRepository.findByAccount_Idaccount(account.getIdaccount());
                for (Address addr : addresses) {
                    if (!addr.getIdaddress().equals(idaddress)) {
                        addr.setIsDefault(false);
                        addressRepository.save(addr);
                    }
                }
            }

            address.setAccount(account);
            address.setHousenumber(addressDetails.getHousenumber());
            address.setRoadname(addressDetails.getRoadname());
            address.setWard(addressDetails.getWard());
            address.setDistrict(addressDetails.getDistrict());
            address.setCity(addressDetails.getCity());
            address.setIsDefault(addressDetails.getIsDefault());

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
    public Address getDefaultAddressByAccountId(String idaccount) {
        List<Address> defaultAddresses = addressRepository.findByAccount_IdaccountAndIsDefaultTrue(idaccount);
        if (defaultAddresses.isEmpty()) {
            return null; // Hoặc ném ngoại lệ tùy thuộc vào yêu cầu của bạn
        }
        return defaultAddresses.get(0); // Giả sử chỉ có một địa chỉ mặc định
    }
}
