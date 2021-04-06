package com.rajan.movieinfoservice.movies;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @RequestMapping(value = "/movies/{movieId}")
    public Movie getMovieInfo(@PathVariable final Long movieId) {
        return new Movie(movieId, "Her", "A fictional scifi movie.");
    }
}
