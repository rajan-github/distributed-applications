package com.rajan.moviecatalogservice.catalog;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MovieCatalogController {


    @RequestMapping(value = "/catalog/{userId}")
    public List<CatalogInfo> getMovieCatalog(@PathVariable final Long userId) {
        return Arrays.asList(
                new CatalogInfo("Imitation Game", "Biography of Alan Turing", 8.0),
                new CatalogInfo("Her", "An OS scifi movie", 7.5),
                new CatalogInfo("Borat", "A fictional comedy", 7.0)
        );
    }
}
