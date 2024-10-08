/*
 * This file is generated by jOOQ.
 */
package org.northcoder.sakila.jooq.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.northcoder.sakila.jooq.Indexes;
import org.northcoder.sakila.jooq.Keys;
import org.northcoder.sakila.jooq.Sakila;
import org.northcoder.sakila.jooq.tables.CustomerTable.CustomerPath;
import org.northcoder.sakila.jooq.tables.InventoryTable.InventoryPath;
import org.northcoder.sakila.jooq.tables.PaymentTable.PaymentPath;
import org.northcoder.sakila.jooq.tables.StaffTable.StaffPath;
import org.northcoder.sakila.jooq.tables.records.RentalRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class RentalTable extends TableImpl<RentalRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>sakila.rental</code>
     */
    public static final RentalTable RENTAL = new RentalTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RentalRecord> getRecordType() {
        return RentalRecord.class;
    }

    /**
     * The column <code>sakila.rental.rental_id</code>.
     */
    public final TableField<RentalRecord, Integer> RENTAL_ID = createField(DSL.name("rental_id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>sakila.rental.rental_date</code>.
     */
    public final TableField<RentalRecord, LocalDateTime> RENTAL_DATE = createField(DSL.name("rental_date"), SQLDataType.LOCALDATETIME(0).nullable(false), this, "");

    /**
     * The column <code>sakila.rental.inventory_id</code>.
     */
    public final TableField<RentalRecord, Integer> INVENTORY_ID = createField(DSL.name("inventory_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>sakila.rental.customer_id</code>.
     */
    public final TableField<RentalRecord, Integer> CUSTOMER_ID = createField(DSL.name("customer_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>sakila.rental.return_date</code>.
     */
    public final TableField<RentalRecord, LocalDateTime> RETURN_DATE = createField(DSL.name("return_date"), SQLDataType.LOCALDATETIME(0), this, "");

    /**
     * The column <code>sakila.rental.staff_id</code>.
     */
    public final TableField<RentalRecord, Integer> STAFF_ID = createField(DSL.name("staff_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>sakila.rental.last_update</code>.
     */
    public final TableField<RentalRecord, LocalDateTime> LAST_UPDATE = createField(DSL.name("last_update"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "");

    private RentalTable(Name alias, Table<RentalRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private RentalTable(Name alias, Table<RentalRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>sakila.rental</code> table reference
     */
    public RentalTable(String alias) {
        this(DSL.name(alias), RENTAL);
    }

    /**
     * Create an aliased <code>sakila.rental</code> table reference
     */
    public RentalTable(Name alias) {
        this(alias, RENTAL);
    }

    /**
     * Create a <code>sakila.rental</code> table reference
     */
    public RentalTable() {
        this(DSL.name("rental"), null);
    }

    public <O extends Record> RentalTable(Table<O> path, ForeignKey<O, RentalRecord> childPath, InverseForeignKey<O, RentalRecord> parentPath) {
        super(path, childPath, parentPath, RENTAL);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class RentalPath extends RentalTable implements Path<RentalRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> RentalPath(Table<O> path, ForeignKey<O, RentalRecord> childPath, InverseForeignKey<O, RentalRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private RentalPath(Name alias, Table<RentalRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public RentalPath as(String alias) {
            return new RentalPath(DSL.name(alias), this);
        }

        @Override
        public RentalPath as(Name alias) {
            return new RentalPath(alias, this);
        }

        @Override
        public RentalPath as(Table<?> alias) {
            return new RentalPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Sakila.SAKILA;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.RENTAL_IDX_FK_CUSTOMER_ID, Indexes.RENTAL_IDX_FK_INVENTORY_ID, Indexes.RENTAL_IDX_FK_STAFF_ID);
    }

    @Override
    public Identity<RentalRecord, Integer> getIdentity() {
        return (Identity<RentalRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<RentalRecord> getPrimaryKey() {
        return Keys.KEY_RENTAL_PRIMARY;
    }

    @Override
    public List<UniqueKey<RentalRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_RENTAL_RENTAL_DATE);
    }

    @Override
    public List<ForeignKey<RentalRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FK_RENTAL_INVENTORY, Keys.FK_RENTAL_CUSTOMER, Keys.FK_RENTAL_STAFF);
    }

    private transient InventoryPath _inventory;

    /**
     * Get the implicit join path to the <code>sakila.inventory</code> table.
     */
    public InventoryPath inventory() {
        if (_inventory == null)
            _inventory = new InventoryPath(this, Keys.FK_RENTAL_INVENTORY, null);

        return _inventory;
    }

    private transient CustomerPath _customer;

    /**
     * Get the implicit join path to the <code>sakila.customer</code> table.
     */
    public CustomerPath customer() {
        if (_customer == null)
            _customer = new CustomerPath(this, Keys.FK_RENTAL_CUSTOMER, null);

        return _customer;
    }

    private transient StaffPath _staff;

    /**
     * Get the implicit join path to the <code>sakila.staff</code> table.
     */
    public StaffPath staff() {
        if (_staff == null)
            _staff = new StaffPath(this, Keys.FK_RENTAL_STAFF, null);

        return _staff;
    }

    private transient PaymentPath _payment;

    /**
     * Get the implicit to-many join path to the <code>sakila.payment</code>
     * table
     */
    public PaymentPath payment() {
        if (_payment == null)
            _payment = new PaymentPath(this, null, Keys.FK_PAYMENT_RENTAL.getInverseKey());

        return _payment;
    }

    @Override
    public TableField<RentalRecord, LocalDateTime> getRecordTimestamp() {
        return LAST_UPDATE;
    }

    @Override
    public RentalTable as(String alias) {
        return new RentalTable(DSL.name(alias), this);
    }

    @Override
    public RentalTable as(Name alias) {
        return new RentalTable(alias, this);
    }

    @Override
    public RentalTable as(Table<?> alias) {
        return new RentalTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public RentalTable rename(String name) {
        return new RentalTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public RentalTable rename(Name name) {
        return new RentalTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public RentalTable rename(Table<?> name) {
        return new RentalTable(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public RentalTable where(Condition condition) {
        return new RentalTable(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public RentalTable where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public RentalTable where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public RentalTable where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public RentalTable where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public RentalTable where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public RentalTable where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public RentalTable where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public RentalTable whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public RentalTable whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
