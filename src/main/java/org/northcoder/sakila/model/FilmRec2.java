package org.northcoder.sakila.model;

import java.util.List;

public record FilmRec2(
        String title,
        List<ActorRec> actors,
        List<String> categories) {

}
