package org.northcoder.sakila;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.ResultQuery;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import org.jooq.exception.DataAccessException;
import org.northcoder.sakila.model.SakilaPojo;

//
// A very basic "starter" set of generic CRUD methods for working with jOOQ:
//
public class Dao {

    private final DSLContext dsl;

    public Dao(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Configuration getConfiguration() {
        return dsl.configuration();
    }

    public <R extends Record> List<R> fetchList(Table table, Condition cndtn,
            Class<R> record) {
        return dsl.fetch(table, cndtn);
    }

    public <P extends SakilaPojo, R extends Record> List<P> fetchPojoList(Select<R> query,
            Class<P> pojo) {
        return dsl.fetch(query).into(pojo);
    }

    public <R extends Record> R fetchSingle(Table<R> tbl, Condition cndtn) {
        return dsl.fetchSingle(tbl, cndtn);
    }

    public <R extends Record> R fetchZeroOrOne(Table<R> tbl, Condition cndtn) {
        Optional<R> optional = dsl.fetchOptional(tbl, cndtn);
        return optional.isPresent() ? optional.get() : null;
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

    public <R extends UpdatableRecord, T> DaoResult update(Table<R> tbl,
            Map<?, ?> valuesToSet, Condition whereCond) {
        try {
            int affectedRows = dsl.update(tbl).set(valuesToSet)
                    .where(whereCond).execute();
            return new DaoResult(null, affectedRows, null);
        } catch (DataAccessException ex) {
            return new DaoResult(null, 0, ex);
        }
    }

    public <R extends UpdatableRecord> DaoResult delete(Table<R> tbl, Condition cndtn) {
        try {
            int affectedRows = dsl.delete(tbl).where(cndtn).execute();
            return new DaoResult(null, affectedRows, null);
        } catch (DataAccessException ex) {
            return new DaoResult(null, 0, ex);
        }
    }

    public int countTable(Table table) {
        return dsl.selectCount().from(table).fetchOne(0, int.class);
    }

    //
    //
    // -------------------------------------------------------------------------
    //
    // Not needed unless you have specific sorting needs not available using
    // your database collation:
    //
    public <R extends Record> List<R> fetchAllSorted(Table table, Class<R> record,
            Comparator<R> cmprtr) {
        return dsl.fetch(table).sortAsc(cmprtr);
    }

    public <R extends Record> List<R> fetchListSorted(Table table, Condition cndtn,
            Class<R> record, Comparator<R> cmprtr) {
        return dsl.fetch(table, cndtn).sortAsc(cmprtr);
    }

    //
    //
    // -------------------------------------------------------------------------
    //
    // Probably not needed:
    //
    public <R extends Record> R fetchOne(ResultQuery<Record> query, Class<R> record) {
        return dsl.fetchOne(query).into(record);
    }

    public <R extends Record> R fetchAny(Table table, Class<R> record) {
        return dsl.fetchAny(table).into(record);
    }

    public <R extends Record> List<R> fetchList(ResultQuery<Record> query, Class<R> record) {
        return dsl.fetch(query).into(record);
    }

    public <R extends Record> List<R> fetchAll(Table table, Class<R> record) {
        return dsl.fetch(table);
    }

}
