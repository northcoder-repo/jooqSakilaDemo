/*
 * This file is generated by jOOQ.
 */
package org.northcoder.sakila.jooq.tables.records;


import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.northcoder.sakila.jooq.tables.InventoryTable;
import org.northcoder.sakila.jooq.tables.pojos.Inventory;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class InventoryRecord extends UpdatableRecordImpl<InventoryRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sakila.inventory.inventory_id</code>.
     */
    public void setInventoryId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>sakila.inventory.inventory_id</code>.
     */
    public Integer getInventoryId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>sakila.inventory.film_id</code>.
     */
    public void setFilmId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>sakila.inventory.film_id</code>.
     */
    public Integer getFilmId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>sakila.inventory.store_id</code>.
     */
    public void setStoreId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>sakila.inventory.store_id</code>.
     */
    public Integer getStoreId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>sakila.inventory.last_update</code>.
     */
    public void setLastUpdate(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>sakila.inventory.last_update</code>.
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
     * Create a detached InventoryRecord
     */
    public InventoryRecord() {
        super(InventoryTable.INVENTORY);
    }

    /**
     * Create a detached, initialised InventoryRecord
     */
    public InventoryRecord(Integer inventoryId, Integer filmId, Integer storeId, LocalDateTime lastUpdate) {
        super(InventoryTable.INVENTORY);

        setInventoryId(inventoryId);
        setFilmId(filmId);
        setStoreId(storeId);
        setLastUpdate(lastUpdate);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised InventoryRecord
     */
    public InventoryRecord(Inventory value) {
        super(InventoryTable.INVENTORY);

        if (value != null) {
            setInventoryId(value.inventoryId());
            setFilmId(value.filmId());
            setStoreId(value.storeId());
            setLastUpdate(value.lastUpdate());
            resetChangedOnNotNull();
        }
    }
}
