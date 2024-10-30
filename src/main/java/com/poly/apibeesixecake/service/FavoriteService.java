package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Favorite;
import com.poly.apibeesixecake.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    public Favorite getFavoriteById(Integer idfavorite) {
        return favoriteRepository.findById(idfavorite)
                .orElseThrow(() -> new IllegalArgumentException("Mục yêu thích không tồn tại."));
    }

    public List<Favorite> getFavoritesByAccountId(String idaccount) {
        return favoriteRepository.findByAccount_Idaccount(idaccount);
    }

    public List<Favorite> getFavoritesByProductId(Integer idproduct) {
        return favoriteRepository.findByProduct_Idproduct(idproduct);
    }

    public Favorite createFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public Favorite updateFavorite(Integer idfavorite, Favorite favoriteDetails) {
        Favorite favorite = getFavoriteById(idfavorite);
        favorite.setProduct(favoriteDetails.getProduct());
        return favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Integer idfavorite) {
        Favorite favorite = getFavoriteById(idfavorite);
        favoriteRepository.delete(favorite);
    }
}
