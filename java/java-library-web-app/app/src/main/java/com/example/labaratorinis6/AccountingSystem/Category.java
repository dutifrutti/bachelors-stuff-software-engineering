package com.example.labaratorinis6.AccountingSystem;




import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Category implements Serializable {

    private int id;
    private String name;
    private String description;
    private LocalDate dateCreated;

    private List<User> responsibleUser;


    private List<Category> subCategory ;


    private List<Income> income ;

    private List<Expense> expense ;

    private String parentCategory;

    private Category parentCategoryObj = null;


    private AccountingSystem accountingSystem;

    public Category() {

    }
    public Category(String name) {
this.name = name;
    }


    public Category(String name, String description,LocalDate dateCreated,
                    ArrayList<User> responsibleUser , ArrayList<Category> subCategory,
                    String parentCategory, ArrayList<Income> income, ArrayList <Expense> expense,
                    AccountingSystem accountingSystem) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.responsibleUser = responsibleUser;
        this.subCategory = subCategory;
        this.parentCategory = parentCategory;
        this.income = income;
        this.expense = expense;
        this.accountingSystem=accountingSystem;

    }
    public Category(String name, String description,LocalDate dateCreated,
                    ArrayList<User> responsibleUser , ArrayList<Category> subCategory,
                    String parentCategory, ArrayList<Income> income, ArrayList <Expense> expense,
                    Category parentCategoryObj) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.responsibleUser = responsibleUser;
        this.subCategory = subCategory;
        this.parentCategory = parentCategory;
        this.income = income;
        this.expense = expense;
        this.parentCategoryObj = parentCategoryObj;


    }







    public Category getParentCategoryObj() {
        return parentCategoryObj;
    }

    public void setParentCategoryObj(Category parentCategoryObj) {
        this.parentCategoryObj = parentCategoryObj;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<User> getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(ArrayList<User> responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public List<Category> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(ArrayList<Category> subCategory) {
        this.subCategory = subCategory;
    }

    public List<Income> getIncome() {
        return income;
    }

    public void setIncome(ArrayList<Income> income) {
        this.income = income;
    }

    public List<Expense> getExpense() {
        return expense;
    }

    public void setExpense(ArrayList<Expense> expense) {
        this.expense = expense;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public AccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public void setAccountingSystem(AccountingSystem accountingSystem) {
        this.accountingSystem = accountingSystem;
    }

    public double getTotalIncome (List<Income> income){
        double totalIncome = 0;
            for (Income i: income){
                 totalIncome = i.getAmount() + totalIncome;
            }
    return totalIncome;}

    public double getTotalExpense (List<Expense> expense){
        double totalExpense = 0;
        for (Expense e: expense){
            totalExpense = e.getAmount() + totalExpense;
        }
        return totalExpense;}
    public double getBalance(){
        double categoryBalance = getTotalIncome(this.income) - getTotalExpense(this.expense);
        return categoryBalance;
    }
    public List<String> getAllSubcategoryNames () {
        List<String> subCategoryNames = new ArrayList<>();
        if(getSubCategory().size()!=0) {
            for (Category subcat : getSubCategory()) {
                subCategoryNames.add(subcat.name);
            }

    return subCategoryNames;}
            else
                subCategoryNames.add("no subcategories");
                return subCategoryNames;
    }

    @Override
    public String toString() {
        return "Category:" +
                 name + '\'' +
                " description" + description + '\'' +
                " dateCreated:" + dateCreated +
                " Subcategories:" + subCategory +
                " responsibleUsers: " + responsibleUser +
                "income:  " + income + "  expese: " + expense +
                "\n";
    }
}
