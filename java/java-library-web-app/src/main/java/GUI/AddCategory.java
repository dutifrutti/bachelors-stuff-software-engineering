package GUI;

import AccountingSystem.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.CategoryHib;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AddCategory implements Initializable {
    public TextField categoryName;
    public TextField categoryDescription;
    public TextField parentCategory;
    public Button addCategoryButton;
    private AccountingSystem asis;

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("lab5_ign");
    CategoryHib categoryHib = new CategoryHib(factory);

    public AddCategory() {
    }

    public void setAccountingSystem(AccountingSystem asis) {
        this.asis = asis;

    }
    public void addingCategories (ActionEvent event) throws IOException {
    if (!checkIfCategoryAlreadyExists(categoryName.getText(),asis)) {
            if(checkIfCategoryAlreadyExists(parentCategory.getText(),asis) || parentCategory.getText().equals("")){

      ArrayList<User> resUser = new ArrayList<User>();
      resUser.add(asis.getSystemUsers().get(0));

      if (!parentCategory.getText().equals("")) {
        for (Category c : asis.getCategories()) {

          recursiveAddingCategories(
              c,
              categoryName.getText(),
              parentCategory.getText(),
              categoryDescription.getText(),
              resUser);
        }

      } else {
        // sukuria pirmo laipsnio kategoriją
        Category cat =
            new Category(
                categoryName.getText(),
                categoryDescription.getText(),
                LocalDate.now(),
                resUser,
                new ArrayList<Category>(),
                parentCategory.getText(),
                new ArrayList<Income>(),
                new ArrayList<Expense>(),
                asis);
        asis.getCategories().add(cat);
        // Pirmo laipsnio kategorija priklijavau prie AccountingSystem objekto
        categoryHib.create(cat);
      }
      }
    } else System.out.println("nu zodziu nieko nesukuriau, nes tokia kategorija jau yra");

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ManageCategories.fxml"));
        Parent root = loader.load();
        ManageCategories manageCategories = loader.getController();
        manageCategories.setAccountingSystem(asis);
        Stage stage = (Stage) addCategoryButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public Boolean recursiveAddingCategories(Category currentCategory, String newCategory, String parentCategory, String categoryDescription, ArrayList<User> resUser){

                if (currentCategory.getName().equals(parentCategory)){
                    Category cat = new Category(
                            newCategory, categoryDescription, LocalDate.now(), resUser, new ArrayList<Category>(),parentCategory ,
                            new ArrayList<Income>(), new ArrayList<Expense>(),currentCategory);
                    currentCategory.getSubCategory().add(cat);
                    // >1 laipsnio kategorijas klijuoju prie jų Parent kategorijos, gal reikėjo nurodyt ir AccountingSystem
                    categoryHib.create(cat);
                    return true;
                }
                else {
                    for (Category subc : currentCategory.getSubCategory())
                        if (recursiveAddingCategories(subc,newCategory,parentCategory,categoryDescription,resUser))
                            return true;
                }
                return false;

    }
    public boolean checkIfCategoryAlreadyExists (String categoryName, AccountingSystem asis){
        Category category;
        ManageCategories mng = new ManageCategories();
        category = mng.findCategoryByName(asis,categoryName);
        if (category == null){
      System.out.println("labai gerai, tokios kategorijos sioje zemeje nera");
            return false;}
        else{System.out.println("tokia kategorija jau yra");
            return true;}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }



}
