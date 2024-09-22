package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Size;
import com.poly.apibeesixecake.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/size")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping
    public ResponseEntity<List<Size>> getAllSize() {
        List<Size> sizes = sizeService.getAllSize();
        return ResponseEntity.ok(sizes);
    }

    @GetMapping("/{idsize}")
    public ResponseEntity<?> getSizeById(@PathVariable Integer idsize) {
        Size size = sizeService.getSizeById(idsize);
        if (size == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Kích thước không tồn tại.");
            }});
        }
        return ResponseEntity.ok(size);
    }

    @PostMapping
    public ResponseEntity<?> createSize(@RequestBody Size size) {
        try {
            Size createdSize = sizeService.createSize(size);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSize);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idsize}")
    public ResponseEntity<?> updateSize(@PathVariable Integer idsize, @RequestBody Size sizeDetails) {
        try {
            Size updatedSize = sizeService.updateSize(idsize, sizeDetails);
            if (updatedSize == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                    put("error", "Kích thước không tồn tại.");
                }});
            }
            return ResponseEntity.ok(updatedSize);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idsize}")
    public ResponseEntity<?> deleteSize(@PathVariable Integer idsize) {
        if (sizeService.getSizeById(idsize) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Kích thước không tồn tại.");
            }});
        }

        try {
            sizeService.deleteSize(idsize);
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "Xóa thành công.");
            }});
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }
}
