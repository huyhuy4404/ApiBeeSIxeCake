package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Size;
import com.poly.apibeesixecake.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeService {
    @Autowired
    private SizeRepository sizeRepository;

    private boolean sizeNameExists(String sizeName) {
        return sizeRepository.findAll().stream()
                .anyMatch(s -> s.getSizename().equalsIgnoreCase(sizeName));
    }

    public List<Size> getAllSize() {
        return sizeRepository.findAll();
    }

    public Size getSizeById(Integer idsize) {
        return sizeRepository.findById(idsize).orElse(null);
    }

    public Size createSize(Size size) {
        if (sizeNameExists(size.getSizename())) {
            throw new IllegalArgumentException("Tên kích thước đã tồn tại.");
        }
        return sizeRepository.save(size);
    }

    public Size updateSize(Integer idsize, Size sizeDetails) {
        Size size = sizeRepository.findById(idsize).orElse(null);
        if (size != null) {
            if (sizeNameExists(sizeDetails.getSizename()) &&
                    !sizeDetails.getSizename().equalsIgnoreCase(size.getSizename())) {
                throw new IllegalArgumentException("Tên kích thước đã tồn tại.");
            }
            size.setSizename(sizeDetails.getSizename());
            return sizeRepository.save(size);
        }
        return null;
    }

    public void deleteSize(Integer idsize) {
        if (!sizeRepository.existsById(idsize)) {
            throw new IllegalArgumentException("Kích thước không tồn tại.");
        }
        sizeRepository.deleteById(idsize);
    }
}
