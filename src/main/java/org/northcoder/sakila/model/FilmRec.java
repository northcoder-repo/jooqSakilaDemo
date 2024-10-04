package org.northcoder.sakila.model;

import java.util.List;

public record FilmRec(
        String title,
        List<ActorRec> actors,
        List<CategoryRec> categories) implements SakilaPojo {

}
