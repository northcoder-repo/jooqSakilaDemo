/*
 * This file is generated by jOOQ.
 */
package org.northcoder.sakila.jooq.tables.records;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.northcoder.sakila.jooq.tables.PaymentTable;
import org.northcoder.sakila.jooq.tables.pojos.Payment;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class PaymentRecord extends UpdatableRecordImpl<PaymentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sakila.payment.payment_id</code>.
     */
    public void setPaymentId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>sakila.payment.payment_id</code>.
     */
    public Integer getPaymentId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>sakila.payment.customer_id</code>.
     */
    public void setCustomerId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>sakila.payment.customer_id</code>.
     */
    public Integer getCustomerId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>sakila.payment.staff_id</code>.
     */
    public void setStaffId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>sakila.payment.staff_id</code>.
     */
    public Integer getStaffId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>sakila.payment.rental_id</code>.
     */
    public void setRentalId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>sakila.payment.rental_id</code>.
     */
    public Integer getRentalId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>sakila.payment.amount</code>.
     */
    public void setAmount(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>sakila.payment.amount</code>.
     */
    public BigDecimal getAmount() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>sakila.payment.payment_date</code>.
     */
    public void setPaymentDate(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>sakila.payment.payment_date</code>.
     */
    public LocalDateTime getPaymentDate() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>sakila.payment.last_update</code>.
     */
    public void setLastUpdate(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>sakila.payment.last_update</code>.
     */
    public LocalDateTime getLastUpdate() {
        return (LocalDateTime) get(6);
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
     * Create a detached PaymentRecord
     */
    public PaymentRecord() {
        super(PaymentTable.PAYMENT);
    }

    /**
     * Create a detached, initialised PaymentRecord
     */
    public PaymentRecord(Integer paymentId, Integer customerId, Integer staffId, Integer rentalId, BigDecimal amount, LocalDateTime paymentDate, LocalDateTime lastUpdate) {
        super(PaymentTable.PAYMENT);

        setPaymentId(paymentId);
        setCustomerId(customerId);
        setStaffId(staffId);
        setRentalId(rentalId);
        setAmount(amount);
        setPaymentDate(paymentDate);
        setLastUpdate(lastUpdate);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised PaymentRecord
     */
    public PaymentRecord(Payment value) {
        super(PaymentTable.PAYMENT);

        if (value != null) {
            setPaymentId(value.paymentId());
            setCustomerId(value.customerId());
            setStaffId(value.staffId());
            setRentalId(value.rentalId());
            setAmount(value.amount());
            setPaymentDate(value.paymentDate());
            setLastUpdate(value.lastUpdate());
            resetChangedOnNotNull();
        }
    }
}
