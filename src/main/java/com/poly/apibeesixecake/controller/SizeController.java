package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Size;
import com.poly.apibeesixecake.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/size")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping
    public ResponseEntity<?> getAllSize() {
        List<Size> sizes = sizeService.getAllSize();
        return new ResponseEntity<>(sizes, HttpStatus.OK);
    }

    @GetMapping("/{idsize}")
    public ResponseEntity<?> getSizeById(@PathVariable Integer idsize) {
        Size size = sizeService.getSizeById(idsize);
        if (size != null) {
            return new ResponseEntity<>(size, HttpStatus.OK);
        }
        return new ResponseEntity<>("Kích thước không tồn tại.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createSize(@RequestBody Size size) {
        try {
            Size createdSize = sizeService.createSize(size);
            return new ResponseEntity<>(createdSize, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idsize}")
    public ResponseEntity<?> updateSize(@PathVariable Integer idsize, @RequestBody Size sizeDetails) {
        try {
            Size updatedSize = sizeService.updateSize(idsize, sizeDetails);
            if (updatedSize != null) {
                return new ResponseEntity<>(updatedSize, HttpStatus.OK);
            }
            return new ResponseEntity<>("Kích thước không tồn tại.", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idsize}")
    public ResponseEntity<?> deleteSize(@PathVariable Integer idsize) {
        // Kiểm tra xem idsize có tồn tại không
        if (sizeService.getSizeById(idsize) == null) {
            return new ResponseEntity<>("Kích thước không tồn tại.", HttpStatus.NOT_FOUND);
        }

        try {
            sizeService.deleteSize(idsize);
            return new ResponseEntity<>("Xóa thành công.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
