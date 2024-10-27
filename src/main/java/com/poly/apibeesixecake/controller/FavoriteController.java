package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Favorite;
import com.poly.apibeesixecake.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<Favorite>> getAllFavorites() {
        List<Favorite> favorites = favoriteService.getAllFavorites();
        return ResponseEntity.ok(favorites);
    }

    @GetMapping("/{idfavorite}")
    public ResponseEntity<?> getFavoriteById(@PathVariable Integer idfavorite) {
        try {
            Favorite favorite = favoriteService.getFavoriteById(idfavorite);
            return ResponseEntity.ok(favorite);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @GetMapping("/account/{idaccount}")
    public ResponseEntity<?> getFavoritesByAccountId(@PathVariable String idaccount) {
        List<Favorite> favorites = favoriteService.getFavoritesByAccountId(idaccount);
        if (favorites.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Không có mục yêu thích nào cho tài khoản này.");
            }});
        }
        return ResponseEntity.ok(favorites);
    }

    @GetMapping("/product/{idproduct}") // Tìm theo idproduct
    public ResponseEntity<?> getFavoritesByProductId(@PathVariable Integer idproduct) {
        List<Favorite> favorites = favoriteService.getFavoritesByProductId(idproduct);
        if (favorites.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Không có mục yêu thích nào cho sản phẩm này.");
            }});
        }
        return ResponseEntity.ok(favorites);
    }

    @PostMapping
    public ResponseEntity<?> createFavorite(@RequestBody Favorite favorite) {
        try {
            Favorite createdFavorite = favoriteService.createFavorite(favorite);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFavorite);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idfavorite}")
    public ResponseEntity<?> updateFavorite(@PathVariable Integer idfavorite, @RequestBody Favorite favoriteDetails) {
        try {
            Favorite updatedFavorite = favoriteService.updateFavorite(idfavorite, favoriteDetails);
            return ResponseEntity.ok(updatedFavorite);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idfavorite}")
    public ResponseEntity<?> deleteFavorite(@PathVariable Integer idfavorite) {
        try {
            favoriteService.deleteFavorite(idfavorite);
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "Xóa mục yêu thích thành công.");
            }});
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }
}
