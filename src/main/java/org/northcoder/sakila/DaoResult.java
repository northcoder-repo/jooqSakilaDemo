package org.northcoder.sakila;

import org.jooq.UpdatableRecord;

public record DaoResult(
        UpdatableRecord record,
        int rowsAffected,
        Exception ex) {

}
