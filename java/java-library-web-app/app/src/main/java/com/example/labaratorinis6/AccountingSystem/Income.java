package com.example.labaratorinis6.AccountingSystem;

import java.io.Serializable;


public class Income implements Serializable {

    private int id;
    private String name;
    private double amount;

    private Category category;
    public Income(String name, double amount, Category category) {
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    public Income() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return  "\t Name: " + name  +
                "\t " + amount + "\n" ;

    }
}
