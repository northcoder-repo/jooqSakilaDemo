package org.northcoder.sakila;

import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.ResultQuery;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import org.jooq.exception.DataAccessException;

//
// A very basic "starter" set of generic CRUD methods for working with jOOQ:
//
public class Dao {

    private final DSLContext dsl;

    public Dao(DSLContext dsl) {
        this.dsl = dsl;
    }

    public <R extends Record> List<R> fetchAll(Table table, Class<R> record) {
        return dsl.fetch(table);
    }

    public <R extends Record> R fetchOne(ResultQuery<Record> query, Class<R> record) {
        return query.fetchOne().into(record);
    }

    public <R extends UpdatableRecord> DaoResult insert(R record) {
        try {
            int affectedRows = record.insert();
            return new DaoResult(record, affectedRows, null);
        } catch (DataAccessException ex) {
            return new DaoResult(record, 0, ex);
        }
    }

    public <R extends UpdatableRecord> DaoResult update(R record) {
        try {
            int affectedRows = record.update();
            return new DaoResult(record, affectedRows, null);
        } catch (DataAccessException ex) {
            return new DaoResult(record, 0, ex);
        }
    }

    public <R extends UpdatableRecord> DaoResult delete(Query query) {
        try {
            int affectedRows = query.execute();
            return new DaoResult(null, affectedRows, null);
        } catch (DataAccessException ex) {
            return new DaoResult(null, 0, ex);
        }
    }

}
