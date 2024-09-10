package org.northcoder.sakila.model;

public record CityRec(
        String cityName,
        CountryRec country) {

}
