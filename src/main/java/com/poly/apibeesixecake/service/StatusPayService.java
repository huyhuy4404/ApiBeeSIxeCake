package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.StatusPay;
import com.poly.apibeesixecake.repository.StatusPayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusPayService {
    @Autowired
    private StatusPayRepository statusPayRepository;

    public List<StatusPay> getAllStatusPays() {
        return statusPayRepository.findAll();
    }

    public StatusPay getStatusPayById(Integer idstatuspay) {
        return statusPayRepository.findById(idstatuspay).orElse(null);
    }

    public StatusPay createStatusPay(StatusPay statusPay) {
        return statusPayRepository.save(statusPay);
    }

    public StatusPay updateStatusPay(Integer idstatuspay, StatusPay statusPayDetails) {
        StatusPay statusPay = statusPayRepository.findById(idstatuspay).orElse(null);
        if (statusPay != null) {
            statusPay.setStatuspayname(statusPayDetails.getStatuspayname());
            return statusPayRepository.save(statusPay);
        }
        return null;
    }

    public void deleteStatusPay(Integer idstatuspay) {
        statusPayRepository.deleteById(idstatuspay);
    }

}
