package com.jemena.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by nmiriyal on 4/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Baby {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int year;

    private Character gender;


    private int count;

    public Baby() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Baby{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", gender=" + gender +
                ", count=" + count +
                '}';
    }
}
