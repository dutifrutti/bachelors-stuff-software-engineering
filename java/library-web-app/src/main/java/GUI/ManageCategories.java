package GUI;

import AccountingSystem.AccountingSystem;
import AccountingSystem.Category;
import AccountingSystem.Expense;
import AccountingSystem.Income;
import AccountingSystem.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import persistence.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageCategories implements Initializable {
    public Button backButton;
    public Button viewCategoryInfoButt;
    public Button updateCategoryButton;
    public Button addCategoryButt;
    public Button incomeButton;
    public Button expenseButton;
    public Label companyBalance;

    public MenuBar menuBar;
    @FXML
    TreeView <String> tree;
    private AccountingSystem asis;
    public TreeItem<String> root;

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("lab5_ign");
    CategoryHib categoryHib = new CategoryHib(factory);
    AccountingSystemHib accountingSystemHib = new AccountingSystemHib(factory);
    IncomeHib incomeHib = new IncomeHib(factory);
    ExpenseHib expenseHib = new ExpenseHib(factory);


    public ManageCategories() {
    }

    public void setAccountingSystem(AccountingSystem asis) {

        this.asis = asis;
        accountingSystemHib.edit(asis);
        fillCategoryTree();
        companyBalance.setText(String.valueOf(asis.getCompanyBalance()));
    //System.out.println(asis.getCategories());
        }

        private void fillCategoryTree() {
            root = new TreeItem<>();
            root.setExpanded(true);


        for (Category category : asis.getCategories()) {

            recursiveFillCategoryTree(category, asis, root);
        }
            tree.setShowRoot(false);
            tree.setRoot(root);
    }

    public void recursiveFillCategoryTree (Category category, AccountingSystem asis, TreeItem<String> root){
        makeBranch(category.getName(),root);
        if (category.getSubCategory().size() > 0){
            for (Category subcategory : category.getSubCategory()) {

                if (!(subcategory.getParentCategory().equals(" "))){
                    TreeItem<String> parent = new TreeItem<>(subcategory.getParentCategory());
                    //System.out.println("parent kategorija:" + parent);
                    makeBranch(subcategory.getName(),parent);
                    recursiveFillCategoryTree(subcategory,asis,root);
                }

            }
        }
    }




    public void addCategory(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AddCategory.fxml"));
        Parent root = loader.load();
        AddCategory addCategory = loader.getController();
        addCategory.setAccountingSystem(asis);
        Stage stage = (Stage) addCategoryButt.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }
    public void deleteCategory(ActionEvent actionEvent) {
        Category category = findCategory(asis);
        if (category.getParentCategory().equals("")){
            asis.getCategories().remove(category);
        }
        else {

            Category parentCategory = findCategoryByName(asis, category.getParentCategory());
            parentCategory.getSubCategory().remove(category);
        }
        categoryHib.remove(category.getId());

        fillCategoryTree();
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindow.fxml"));
        Parent root = loader.load();
        MainWindow mainWindow = loader.getController();
        mainWindow.setAccountingSystem(asis);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    //todo sutvarkyt, kad graziai printintu subkategorijas                                                                                         one day...one day..
    public void viewCategoryInfo(ActionEvent actionEvent) {
        Category category  = (findCategory(asis));
    System.out.println(category.getName());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Category Info");
        alert.setHeaderText("Category: " + category.getName() + " details");
        alert.setContentText("Description: " + category.getDescription() + "\n" +
                            "Total balance: " + category.getBalance() + "\n" +
                             "Date created: " + category.getDateCreated() + "\n" +
                              "Responsible user: " + category.getResponsibleUser()+ "\n" +
                                "Subcategories: "  + category.getSubCategory() + "\n" +
                                 "Income: " + category.getIncome() + "\n" +
                                "Expense: " + category.getExpense() + "\n"
                                );
        alert.showAndWait();
    }

    public void updateCategory(ActionEvent actionEvent) throws IOException {
        Category category = findCategory(asis);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/UpdateCategory.fxml"));
        Parent root = loader.load();
        UpdateCategory updateCategory = loader.getController();
        updateCategory.setCategory(category);
        updateCategory.setAccountingSystem(asis);
        Stage stage = (Stage) updateCategoryButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public Category findCategory (AccountingSystem asis){
    Category foundcategory = new Category();
        for ( Category currentCat: asis.getCategories()){
            foundcategory = findCategoryRecursive(asis,currentCat);
            if (foundcategory != null && foundcategory.getName() == tree.getSelectionModel().getSelectedItem().getValue())
                return foundcategory;
        }

            System.out.println("nop");
            return null;
    }
    public Category findCategoryRecursive (AccountingSystem asis, Category currentCat){
        if (currentCat.getName().equals(tree.getSelectionModel().getSelectedItem().getValue()))
        {
            return currentCat;
        }
        else {

            for (Category subcategory : currentCat.getSubCategory()) {
                Category foundcategory = findCategoryRecursive(asis, subcategory);
                if (foundcategory!=null){
                if (foundcategory.getName().equals(tree.getSelectionModel().getSelectedItem().getValue()))
                        return foundcategory;
                }
                    else
                        findCategoryRecursive(asis, subcategory);

            }

        }
  return null;}

    public Category findCategoryByName (AccountingSystem asis, String targetCategory){
        Category foundcategory = new Category();
        for ( Category currentCat: asis.getCategories()){
            foundcategory = findCategoryRecursiveByName(asis,currentCat, targetCategory);
            if (foundcategory!=null && foundcategory.getName().equals(targetCategory))
                return foundcategory;
        }

            System.out.println("no");
        return null;
    }
    public Category findCategoryRecursiveByName (AccountingSystem asis, Category currentCat, String targetCategory){
        if (currentCat.getName().equals(targetCategory))
        {
            return currentCat;
        }
        else {

            for (Category subcategory : currentCat.getSubCategory()) {
                Category foundcategory = findCategoryRecursiveByName(asis, subcategory, targetCategory);
                if (foundcategory!=null){
                    if (foundcategory.getName().equals(targetCategory))
                        return foundcategory;
                }
                else
                    findCategoryRecursiveByName(asis, subcategory, targetCategory);

            }

        }
        return null;}




    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    public TreeItem<String> makeBranch(String title,TreeItem<String> parent){
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        //System.out.println("root vaikai" + root.getChildren());
        return item;
    }

    public void addIncome(ActionEvent actionEvent) {
        Dialog<Income> dialog = new Dialog<>();
        dialog.setTitle("Income");
        dialog.setHeaderText("Enter income plz:");
        dialog.setResizable(true);

        Label label1 = new Label("Name: ");
        Label label2 = new Label("Amount: ");
        TextField text1 = new TextField();
        TextField text2 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Income>() {
            @Override
            public Income call(ButtonType b) {

                if (b == buttonTypeOk) {
                    Category category =  findCategory(asis);
                    Income income = new Income(text1.getText(), Double.parseDouble(text2.getText()), category);
                   category.getIncome().add(income);
                    companyBalance.setText(String.valueOf(asis.getCompanyBalance()));
                    incomeHib.create(income);
                }

                return null;
            }
        });

        Optional<Income> result = dialog.showAndWait();

        if (result.isPresent()) {

            System.out.println("Result: " + result.get());

        }
    }

    public void addExpense(ActionEvent actionEvent) {
        Dialog<Expense> dialog = new Dialog<>();
        dialog.setTitle("Expense");
        dialog.setHeaderText("Enter expense plz:");
        dialog.setResizable(true);

        Label label1 = new Label("Name: ");
        Label label2 = new Label("Amount: ");
        TextField text1 = new TextField();
        TextField text2 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Expense>() {
            @Override
            public Expense call(ButtonType b) {

                if (b == buttonTypeOk) {
                    Category category =  findCategory(asis);
                    Expense expense = new Expense(text1.getText(), Double.parseDouble(text2.getText()), category);
                    category.getExpense().add(expense);
                    companyBalance.setText(String.valueOf(asis.getCompanyBalance()));
                    expenseHib.create(expense);
                }

                return null;
            }
        });

        Optional<Expense> result = dialog.showAndWait();

        if (result.isPresent()) {

            System.out.println("Result: " + result.get());

        }
    }
}
