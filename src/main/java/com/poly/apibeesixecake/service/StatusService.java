package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Status;
import com.poly.apibeesixecake.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    public Status getStatusById(Integer idstatus) {
        return statusRepository.findById(idstatus).orElse(null);
    }

    public Status createStatus(Status status) {
        if (statusNameExists(status.getStatusname())) {
            throw new IllegalArgumentException("Tên trạng thái đã tồn tại.");
        }
        return statusRepository.save(status);
    }

    public Status updateStatus(Integer idstatus, Status statusDetails) {
        Status existingStatus = statusRepository.findById(idstatus).orElse(null);
        if (existingStatus != null) {
            if (statusNameExists(statusDetails.getStatusname()) &&
                    !statusDetails.getStatusname().equalsIgnoreCase(existingStatus.getStatusname())) {
                throw new IllegalArgumentException("Tên trạng thái đã tồn tại.");
            }
            existingStatus.setStatusname(statusDetails.getStatusname());
            return statusRepository.save(existingStatus);
        }
        return null;
    }

    public void deleteStatus(Integer idstatus) {
        if (!statusRepository.existsById(idstatus)) {
            throw new IllegalArgumentException("Trạng thái không tồn tại.");
        }
        statusRepository.deleteById(idstatus);
    }

    private boolean statusNameExists(String statusName) {
        return statusRepository.findAll().stream()
                .anyMatch(s -> s.getStatusname().equalsIgnoreCase(statusName));
    }
    public List<Status> getStatusesByPartialName(String statusname) {
        return statusRepository.findByStatusnameIgnoreCaseContaining(statusname);
    }
}
