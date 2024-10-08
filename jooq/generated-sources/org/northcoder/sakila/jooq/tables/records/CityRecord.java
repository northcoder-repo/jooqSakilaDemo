/*
 * This file is generated by jOOQ.
 */
package org.northcoder.sakila.jooq.tables.records;


import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.northcoder.sakila.jooq.tables.CityTable;
import org.northcoder.sakila.jooq.tables.pojos.City;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class CityRecord extends UpdatableRecordImpl<CityRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sakila.city.city_id</code>.
     */
    public void setCityId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>sakila.city.city_id</code>.
     */
    public Integer getCityId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>sakila.city.city</code>.
     */
    public void setCity(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>sakila.city.city</code>.
     */
    public String getCity() {
        return (String) get(1);
    }

    /**
     * Setter for <code>sakila.city.country_id</code>.
     */
    public void setCountryId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>sakila.city.country_id</code>.
     */
    public Integer getCountryId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>sakila.city.last_update</code>.
     */
    public void setLastUpdate(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>sakila.city.last_update</code>.
     */
    public LocalDateTime getLastUpdate() {
        return (LocalDateTime) get(3);
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
     * Create a detached CityRecord
     */
    public CityRecord() {
        super(CityTable.CITY);
    }

    /**
     * Create a detached, initialised CityRecord
     */
    public CityRecord(Integer cityId, String city, Integer countryId, LocalDateTime lastUpdate) {
        super(CityTable.CITY);

        setCityId(cityId);
        setCity(city);
        setCountryId(countryId);
        setLastUpdate(lastUpdate);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised CityRecord
     */
    public CityRecord(City value) {
        super(CityTable.CITY);

        if (value != null) {
            setCityId(value.cityId());
            setCity(value.city());
            setCountryId(value.countryId());
            setLastUpdate(value.lastUpdate());
            resetChangedOnNotNull();
        }
    }
}
