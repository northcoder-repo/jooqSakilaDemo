/*
 * This file is generated by jOOQ.
 */
package org.northcoder.sakila.jooq.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public record Inventory(
    Integer inventoryId,
    Integer filmId,
    Integer storeId,
    LocalDateTime lastUpdate
) implements Serializable {

    private static final long serialVersionUID = 1L;

}
