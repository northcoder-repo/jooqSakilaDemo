/*
 * This file is generated by jOOQ.
 */
package org.northcoder.sakila.jooq.tables;


import java.math.BigDecimal;
import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.northcoder.sakila.jooq.Sakila;
import org.northcoder.sakila.jooq.tables.records.SalesByFilmCategoryRecord;


/**
 * VIEW
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class SalesByFilmCategoryTable extends TableImpl<SalesByFilmCategoryRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>sakila.sales_by_film_category</code>
     */
    public static final SalesByFilmCategoryTable SALES_BY_FILM_CATEGORY = new SalesByFilmCategoryTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SalesByFilmCategoryRecord> getRecordType() {
        return SalesByFilmCategoryRecord.class;
    }

    /**
     * The column <code>sakila.sales_by_film_category.category</code>.
     */
    public final TableField<SalesByFilmCategoryRecord, String> CATEGORY = createField(DSL.name("category"), SQLDataType.VARCHAR(25).nullable(false), this, "");

    /**
     * The column <code>sakila.sales_by_film_category.total_sales</code>.
     */
    public final TableField<SalesByFilmCategoryRecord, BigDecimal> TOTAL_SALES = createField(DSL.name("total_sales"), SQLDataType.DECIMAL(27, 2), this, "");

    private SalesByFilmCategoryTable(Name alias, Table<SalesByFilmCategoryRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private SalesByFilmCategoryTable(Name alias, Table<SalesByFilmCategoryRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment("VIEW"), TableOptions.view("create view `sales_by_film_category` as select `c`.`name` AS `category`,sum(`p`.`amount`) AS `total_sales` from (((((`sakila`.`payment` `p` join `sakila`.`rental` `r` on((`p`.`rental_id` = `r`.`rental_id`))) join `sakila`.`inventory` `i` on((`r`.`inventory_id` = `i`.`inventory_id`))) join `sakila`.`film` `f` on((`i`.`film_id` = `f`.`film_id`))) join `sakila`.`film_category` `fc` on((`f`.`film_id` = `fc`.`film_id`))) join `sakila`.`category` `c` on((`fc`.`category_id` = `c`.`category_id`))) group by `c`.`name` order by `total_sales` desc"), where);
    }

    /**
     * Create an aliased <code>sakila.sales_by_film_category</code> table
     * reference
     */
    public SalesByFilmCategoryTable(String alias) {
        this(DSL.name(alias), SALES_BY_FILM_CATEGORY);
    }

    /**
     * Create an aliased <code>sakila.sales_by_film_category</code> table
     * reference
     */
    public SalesByFilmCategoryTable(Name alias) {
        this(alias, SALES_BY_FILM_CATEGORY);
    }

    /**
     * Create a <code>sakila.sales_by_film_category</code> table reference
     */
    public SalesByFilmCategoryTable() {
        this(DSL.name("sales_by_film_category"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Sakila.SAKILA;
    }

    @Override
    public SalesByFilmCategoryTable as(String alias) {
        return new SalesByFilmCategoryTable(DSL.name(alias), this);
    }

    @Override
    public SalesByFilmCategoryTable as(Name alias) {
        return new SalesByFilmCategoryTable(alias, this);
    }

    @Override
    public SalesByFilmCategoryTable as(Table<?> alias) {
        return new SalesByFilmCategoryTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public SalesByFilmCategoryTable rename(String name) {
        return new SalesByFilmCategoryTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SalesByFilmCategoryTable rename(Name name) {
        return new SalesByFilmCategoryTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public SalesByFilmCategoryTable rename(Table<?> name) {
        return new SalesByFilmCategoryTable(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SalesByFilmCategoryTable where(Condition condition) {
        return new SalesByFilmCategoryTable(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SalesByFilmCategoryTable where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SalesByFilmCategoryTable where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SalesByFilmCategoryTable where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public SalesByFilmCategoryTable where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public SalesByFilmCategoryTable where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public SalesByFilmCategoryTable where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public SalesByFilmCategoryTable where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SalesByFilmCategoryTable whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SalesByFilmCategoryTable whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
