package com.example.labaratorinis6.AccountingSystem;


import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private int userid;
    public String name;
    public String surname;
    private String login;
    private String password;

    private List<Category> responsibleCategories;

    private AccountingSystem accountingSystem;

    public User(String name, String surname, AccountingSystem accountingSystem) {
        this.name = name;
        this.surname = surname;
        this.accountingSystem=accountingSystem;

    }
    public User(String name, AccountingSystem accountingSystem) {
        this.name = name;
        this.accountingSystem=accountingSystem;
    }

    public User() {

    }


    public List<Category> getResponsibleCategories() {
        return responsibleCategories;
    }

    public void setResponsibleCategories(List<Category> responsibleCategories) {
        this.responsibleCategories = responsibleCategories;
    }

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    public int getId() {
        return userid;
    }

    public void setId(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return  name +" "+ surname;
    }
}
