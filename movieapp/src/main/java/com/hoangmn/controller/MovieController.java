package com.hoangmn.controller;

import com.hoangmn.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.hoangmn.model.Movie;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@RequestMapping(value = "/movies", method = RequestMethod.GET)
	public List<Movie> getMovies() {
		List<Movie> movies = movieService.getAll();
		return movies;
	}

	@RequestMapping(value = "/movies", method = RequestMethod.POST)
	public Movie add(@RequestBody Movie movie)  {
		movieService.save(movie);
		return movie;
	}

	@RequestMapping(value = "/movies", method = RequestMethod.PUT)
	public Movie update(@RequestBody Movie movie)  {
		movieService.update(movie);
		Movie updatedMovie = movieService.getMovie(movie.getId());
		return updatedMovie;
	}

	@RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id)  {
		int result = movieService.delete(id);
		System.out.println(result);
		if (result < 1) {
			throw new RuntimeException("Error");
		}
	}



}
