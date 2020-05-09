package com.hoangmn.controller;

import com.hoangmn.model.Movie;
import com.hoangmn.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FavoriteController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/user/{userId}/fav", method = RequestMethod.GET)
    public List<Movie> getFavs(@PathVariable int userId) {
        List<Movie> favs = movieService.getFavs(userId);
        return favs;
    }

    @RequestMapping(value = "/user/{userId}/nonfav", method = RequestMethod.GET)
    public List<Movie> getNonFavs(@PathVariable int userId) {
        List<Movie> favs = movieService.getNonFavs(userId);
        return favs;
    }

    @RequestMapping(value = "/user/{userId}/fav/{movieId}", method = RequestMethod.POST)
    public void addToFavorites(@PathVariable int userId, @PathVariable int movieId) {
        int result = movieService.addToFavorites(userId, movieId);
        if (result < 1) {
            throw new RuntimeException("Error");
        }
    }

    @RequestMapping(value = "/user/{userId}/fav/{movieId}", method = RequestMethod.DELETE)
    public void removeFromFavorites(@PathVariable int userId, @PathVariable int movieId) {
        int result = movieService.removeFromFavorites(userId, movieId);
        if (result < 1) {
            throw new RuntimeException("Error");
        }
    }


}
