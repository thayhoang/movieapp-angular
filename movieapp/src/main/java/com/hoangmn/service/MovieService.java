package com.hoangmn.service;

import com.hoangmn.repository.mapper.MovieMapper;
import com.hoangmn.model.Movie;
import com.hoangmn.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;

    public Movie getMovie(int id) {
        return movieMapper.get(id);
    }

    public List<Movie> getAll() {
        return movieMapper.getAll();
    }

    public int save(Movie movie) {
        return movieMapper.save(movie);
    }

    public int delete(int id) {
        return movieMapper.delete(id);
    }

    public List<Movie> getFavs(int userId) {
        return movieMapper.getFavs(userId);
    }

    public List<Movie> getNonFavs(int userId) {
        return movieMapper.getNonFavs(userId);
    }

  /*  public boolean isFavorite(User user, int movieId) {
        return movieMapper.getCountFromFav(user.getId(), movieId) > 0;
    }*/

    public int removeFromFavorites(int userId, int movieId) {
        return movieMapper.removeFromFavorites(userId, movieId);
    }

    public int addToFavorites(int userId, int movieId) {
        return movieMapper.addToFavorites(userId, movieId);
    }

    public User getUser(String username, String password) {
        return movieMapper.getUser(username, password);
    }

    public int update(Movie movie) {
        return movieMapper.update(movie);
    }
}
