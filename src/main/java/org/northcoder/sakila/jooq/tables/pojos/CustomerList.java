/*
 * This file is generated by jOOQ.
 */
package org.northcoder.sakila.jooq.tables.pojos;


import java.io.Serializable;


/**
 * VIEW
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public record CustomerList(
    Integer id,
    String name,
    String address,
    String zipCode,
    String phone,
    String city,
    String country,
    String notes,
    Integer sid
) implements Serializable {

    private static final long serialVersionUID = 1L;

}
