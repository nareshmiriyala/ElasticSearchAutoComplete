package com.jemena.model;

/**
 * Created by nmiriyal on 27/07/2016.
 */
public final class BabyBuilder {
    private String name;
    private int year;
    private Character gender;
    private int count;
    private int id;
    private BabyBuilder() {
    }

    public static BabyBuilder aBaby() {
        return new BabyBuilder();
    }


    public BabyBuilder withId(int id) {
        this.id = id;
        return this;
    }
    public BabyBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public BabyBuilder withYear(int year) {
        this.year = year;
        return this;
    }

    public BabyBuilder withGender(Character gender) {
        this.gender = gender;
        return this;
    }

    public BabyBuilder withCount(int count) {
        this.count = count;
        return this;
    }

    public Baby build() {
        Baby baby = new Baby();
        baby.setId(id);
        baby.setName(name);
        baby.setYear(year);
        baby.setGender(gender);
        baby.setCount(count);
        return baby;
    }
}
