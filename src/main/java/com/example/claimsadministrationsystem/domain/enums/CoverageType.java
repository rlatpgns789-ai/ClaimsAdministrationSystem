package com.example.claimsadministrationsystem.domain.enums;

import java.util.function.Function;

public enum CoverageType {

    GENERAL_PREPAID(Category.GENERAL, Timing.PREPAID, value -> value * 15 / 100),
    GENERAL_POSTPAID(Category.GENERAL, Timing.POSTPAID, value -> value * 20 / 100),
    EXPRESS_PREPAID(Category.EXPRESS, Timing.PREPAID, value -> value * 25 / 100),
    EXPRESS_POSTPAID(Category.EXPRESS, Timing.POSTPAID, value -> value * 30 / 100);

    private final Category category;
    private final Timing timing;
    private final Function<Long, Long> commissionRate;

    CoverageType(Category category, Timing timing, Function<Long, Long> commissionRate) {
        this.category = category;
        this.timing = timing;
        this.commissionRate = commissionRate;
    }

    public Long calculateCommission(Long value) {
        return commissionRate.apply(value);
    }

    public boolean isGeneral(){
        return this.category == Category.GENERAL;
    }

    public boolean isPrepaid(){return this.timing == Timing.PREPAID;}


    public enum Category { GENERAL, EXPRESS }
    public enum Timing { PREPAID, POSTPAID }
}
