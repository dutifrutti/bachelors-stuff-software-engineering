package GUI;

import AccountingSystem.AccountingSystem;
import AccountingSystem.Category;
import AccountingSystem.Expense;
import AccountingSystem.Income;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import persistence.CategoryHib;
import persistence.ExpenseHib;
import persistence.IncomeHib;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateCategory implements Initializable {

    public TextField nameField;
    public TextField descField;
    public Button changeButton;
    public Pane pane;
    public ListView incomeList;
    public ListView expenseList;
    private AccountingSystem asis;
    private Category category;

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("lab5_ign");
    CategoryHib categoryHib = new CategoryHib(factory);
    IncomeHib incomeHib = new IncomeHib(factory);
    ExpenseHib expenseHib = new ExpenseHib(factory);

    public void setAccountingSystem(AccountingSystem asis) {
        this.asis = asis;
    }

    public void setCategory(Category category) {
        this.category = category;
        nameField.setText(category.getName());
        descField.setText(category.getDescription());
       for (int i=0; i < category.getIncome().size(); i++){
           List<Income> income = category.getIncome();
           incomeList.getItems().add(income.get(i).getName());
       }

        for (int i=0; i < category.getExpense().size(); i++){
            List<Expense> expense = category.getExpense();
            expenseList.getItems().add(expense.get(i).getName());
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void applyChanges(ActionEvent actionEvent) throws IOException {
        AddCategory addCat = new AddCategory();
    if (!addCat.checkIfCategoryAlreadyExists(nameField.getText(),asis)) {
      category.setName(nameField.getText());
      category.setDescription(descField.getText());
      // fixed bug: atnaujinus kategorijos pavadinima, jos subkategorijose taip pat atsinaujina
      if (category.getSubCategory().size() > 0) {
        updateParentCatogeries();
      }
        categoryHib.edit(category);
    } else System.out.println("nop, tokia kategorija jau yra");

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ManageCategories.fxml"));
        Parent root = loader.load();
        ManageCategories manageCategories = loader.getController();
        manageCategories.setAccountingSystem(asis);
        Stage stage = (Stage) changeButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void editIncome(ActionEvent actionEvent) {
        Income targetIncome= new Income();
        int index=0;
        for (Income i: category.getIncome()){
            if (i.getName().equals(incomeList.getSelectionModel().getSelectedItem()))
            {targetIncome = i;
            index = incomeList.getSelectionModel().getSelectedIndex();
            break;}
        }

        Dialog<Income> dialog = new Dialog<>();
        dialog.setTitle("Income");
        dialog.setHeaderText("Edit income plz:");
        dialog.setResizable(true);

        Label label1 = new Label("Name: ");
        Label label2 = new Label("Amount: ");
        TextField text1 = new TextField(targetIncome.getName());
        TextField text2 = new TextField(String.valueOf(targetIncome.getAmount()));

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        Income finalTargetIncome = targetIncome;
        int finalIndex = index;
        dialog.setResultConverter(new Callback<ButtonType, Income>() {
            @Override
            public Income call(ButtonType b) {

                if (b == buttonTypeOk) {
                    finalTargetIncome.setName(text1.getText());
                    finalTargetIncome.setAmount(Double.parseDouble(text2.getText()));
                    incomeList.getItems().remove(finalIndex);
                    incomeList.getItems().add(finalIndex, finalTargetIncome.getName());
                    incomeHib.edit(finalTargetIncome);

                }

                return null;
            }
        });

        Optional<Income> result = dialog.showAndWait();

        if (result.isPresent()) {

            System.out.println("Result: " + result.get());

        }
    }
    public void updateParentCatogeries(){
        for (Category c: category.getSubCategory()){
            c.setParentCategory(category.getName());
        }
    }

    public void editExpense(ActionEvent actionEvent) {
        Expense targetExpense= new Expense();
        int index=0;
        for (Expense i: category.getExpense()){
            if (i.getName().equals(expenseList.getSelectionModel().getSelectedItem()))
            {targetExpense = i;
                index = expenseList.getSelectionModel().getSelectedIndex();
                break;}
        }

        Dialog<Income> dialog = new Dialog<>();
        dialog.setTitle("Expense");
        dialog.setHeaderText("You may know edit the expense:");
        dialog.setResizable(true);

        Label label1 = new Label("Name: ");
        Label label2 = new Label("Amount: ");
        TextField text1 = new TextField(targetExpense.getName());
        TextField text2 = new TextField(String.valueOf(targetExpense.getAmount()));

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        Expense finalTargetExpense = targetExpense;
        int finalIndex = index;
        dialog.setResultConverter(new Callback<ButtonType, Income>() {
            @Override
            public Income call(ButtonType b) {

                if (b == buttonTypeOk) {
                    finalTargetExpense.setName(text1.getText());
                    finalTargetExpense.setAmount(Double.parseDouble(text2.getText()));
                    expenseList.getItems().remove(finalIndex);
                    expenseList.getItems().add(finalIndex, finalTargetExpense.getName());
                   expenseHib.edit(finalTargetExpense);

                }

                return null;
            }
        });

        Optional<Income> result = dialog.showAndWait();

        if (result.isPresent()) {

            System.out.println("Result: " + result.get());

        }
    }

    public void deleteIncome(ActionEvent actionEvent) {
        for (Income i : category.getIncome()){
      if (i.getName().equals(incomeList.getSelectionModel().getSelectedItem())) {
        category.getIncome().remove(i);
        incomeList.getItems().remove(incomeList.getSelectionModel().getSelectedIndex());
        incomeHib.remove(i.getId());
        break;
      }
        }
    }

    public void deleteExpense(ActionEvent actionEvent) {
        for (Expense e : category.getExpense()){
            if (e.getName().equals(expenseList.getSelectionModel().getSelectedItem())) {
                category.getExpense().remove(e);
                expenseList.getItems().remove(expenseList.getSelectionModel().getSelectedIndex());
                expenseHib.remove(e.getId());
                break;
            }
        }
    }
}
