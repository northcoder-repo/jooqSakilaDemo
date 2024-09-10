/*
 * This file is generated by jOOQ.
 */
package org.northcoder.sakila.jooq.tables.records;


import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.northcoder.sakila.jooq.tables.LanguageTable;
import org.northcoder.sakila.jooq.tables.pojos.Language;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class LanguageRecord extends UpdatableRecordImpl<LanguageRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sakila.language.language_id</code>.
     */
    public void setLanguageId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>sakila.language.language_id</code>.
     */
    public Integer getLanguageId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>sakila.language.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>sakila.language.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>sakila.language.last_update</code>.
     */
    public void setLastUpdate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>sakila.language.last_update</code>.
     */
    public LocalDateTime getLastUpdate() {
        return (LocalDateTime) get(2);
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
     * Create a detached LanguageRecord
     */
    public LanguageRecord() {
        super(LanguageTable.LANGUAGE);
    }

    /**
     * Create a detached, initialised LanguageRecord
     */
    public LanguageRecord(Integer languageId, String name, LocalDateTime lastUpdate) {
        super(LanguageTable.LANGUAGE);

        setLanguageId(languageId);
        setName(name);
        setLastUpdate(lastUpdate);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised LanguageRecord
     */
    public LanguageRecord(Language value) {
        super(LanguageTable.LANGUAGE);

        if (value != null) {
            setLanguageId(value.languageId());
            setName(value.name());
            setLastUpdate(value.lastUpdate());
            resetChangedOnNotNull();
        }
    }
}
