/*
 * This file is generated by jOOQ.
 */
package org.northcoder.sakila.jooq.tables.records;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.northcoder.sakila.jooq.tables.FilmTable;
import org.northcoder.sakila.jooq.tables.pojos.Film;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class FilmRecord extends UpdatableRecordImpl<FilmRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sakila.film.film_id</code>.
     */
    public void setFilmId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>sakila.film.film_id</code>.
     */
    public Integer getFilmId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>sakila.film.title</code>.
     */
    public void setTitle(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>sakila.film.title</code>.
     */
    public String getTitle() {
        return (String) get(1);
    }

    /**
     * Setter for <code>sakila.film.description</code>.
     */
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>sakila.film.description</code>.
     */
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>sakila.film.release_year</code>.
     */
    public void setReleaseYear(Year value) {
        set(3, value);
    }

    /**
     * Getter for <code>sakila.film.release_year</code>.
     */
    public Year getReleaseYear() {
        return (Year) get(3);
    }

    /**
     * Setter for <code>sakila.film.language_id</code>.
     */
    public void setLanguageId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>sakila.film.language_id</code>.
     */
    public Integer getLanguageId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>sakila.film.original_language_id</code>.
     */
    public void setOriginalLanguageId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>sakila.film.original_language_id</code>.
     */
    public Integer getOriginalLanguageId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>sakila.film.rental_duration</code>.
     */
    public void setRentalDuration(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>sakila.film.rental_duration</code>.
     */
    public Byte getRentalDuration() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>sakila.film.rental_rate</code>.
     */
    public void setRentalRate(BigDecimal value) {
        set(7, value);
    }

    /**
     * Getter for <code>sakila.film.rental_rate</code>.
     */
    public BigDecimal getRentalRate() {
        return (BigDecimal) get(7);
    }

    /**
     * Setter for <code>sakila.film.length</code>.
     */
    public void setLength(Short value) {
        set(8, value);
    }

    /**
     * Getter for <code>sakila.film.length</code>.
     */
    public Short getLength() {
        return (Short) get(8);
    }

    /**
     * Setter for <code>sakila.film.replacement_cost</code>.
     */
    public void setReplacementCost(BigDecimal value) {
        set(9, value);
    }

    /**
     * Getter for <code>sakila.film.replacement_cost</code>.
     */
    public BigDecimal getReplacementCost() {
        return (BigDecimal) get(9);
    }

    /**
     * Setter for <code>sakila.film.rating</code>.
     */
    public void setRating(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>sakila.film.rating</code>.
     */
    public String getRating() {
        return (String) get(10);
    }

    /**
     * Setter for <code>sakila.film.special_features</code>.
     */
    public void setSpecialFeatures(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>sakila.film.special_features</code>.
     */
    public String getSpecialFeatures() {
        return (String) get(11);
    }

    /**
     * Setter for <code>sakila.film.last_update</code>.
     */
    public void setLastUpdate(LocalDateTime value) {
        set(12, value);
    }

    /**
     * Getter for <code>sakila.film.last_update</code>.
     */
    public LocalDateTime getLastUpdate() {
        return (LocalDateTime) get(12);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached FilmRecord
     */
    public FilmRecord() {
        super(FilmTable.FILM);
    }

    /**
     * Create a detached, initialised FilmRecord
     */
    public FilmRecord(Integer filmId, String title, String description, Year releaseYear, Integer languageId, Integer originalLanguageId, Byte rentalDuration, BigDecimal rentalRate, Short length, BigDecimal replacementCost, String rating, String specialFeatures, LocalDateTime lastUpdate) {
        super(FilmTable.FILM);

        setFilmId(filmId);
        setTitle(title);
        setDescription(description);
        setReleaseYear(releaseYear);
        setLanguageId(languageId);
        setOriginalLanguageId(originalLanguageId);
        setRentalDuration(rentalDuration);
        setRentalRate(rentalRate);
        setLength(length);
        setReplacementCost(replacementCost);
        setRating(rating);
        setSpecialFeatures(specialFeatures);
        setLastUpdate(lastUpdate);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised FilmRecord
     */
    public FilmRecord(Film value) {
        super(FilmTable.FILM);

        if (value != null) {
            setFilmId(value.filmId());
            setTitle(value.title());
            setDescription(value.description());
            setReleaseYear(value.releaseYear());
            setLanguageId(value.languageId());
            setOriginalLanguageId(value.originalLanguageId());
            setRentalDuration(value.rentalDuration());
            setRentalRate(value.rentalRate());
            setLength(value.length());
            setReplacementCost(value.replacementCost());
            setRating(value.rating());
            setSpecialFeatures(value.specialFeatures());
            setLastUpdate(value.lastUpdate());
            resetChangedOnNotNull();
        }
    }
}
