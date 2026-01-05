package GUI;


import AccountingSystem.AccountingSystem;
import AccountingSystem.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.AccountingSystemHib;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    public TextField nameBar;
    public TextField surnameBar;
    public Button logInButton;
    public TextField CompanyName;
    public TextField CompanyEmail;

    private AccountingSystem asis;

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("lab5_ign");
    AccountingSystemHib accountingSystemHib = new AccountingSystemHib(factory);
    public void setAccountingSystem (AccountingSystem asis){
        this.asis = asis;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void logIn(ActionEvent actionEvent) throws IOException {

        asis.getSystemUsers().add(new User(nameBar.getText(),surnameBar.getText(),asis));
        asis.setCompanyName(CompanyName.getText());
        asis.setEmail(CompanyEmail.getText());
        //Buhalterines sistemos sukūrimas
        accountingSystemHib.create(asis);
        loadMainWindow();
    }
    public void loadMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindow.fxml"));
        Parent root = loader.load();
        MainWindow mainWindow = loader.getController();
        mainWindow.setAccountingSystem(asis);
        Stage stage = (Stage) logInButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
