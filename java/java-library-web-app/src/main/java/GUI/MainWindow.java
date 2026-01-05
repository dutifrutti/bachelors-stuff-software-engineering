package GUI;

import AccountingSystem.AccountingSystem;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import persistence.AccountingSystemHib;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

public class MainWindow implements Serializable {
    public Button ManageCategoriesButt;
    public Button UpCompInfoButt;
    public Button ExitButt;
    public Label companyNameLabel;
    public Label companyEmailLabel;
    private AccountingSystem asis;
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("lab5_ign");
    AccountingSystemHib accountingSystemHib = new AccountingSystemHib(factory);



    public void setAccountingSystem (AccountingSystem asis){
            companyNameLabel.setText(asis.getCompanyName());
            companyEmailLabel.setText(asis.getEmail());
            this.asis = asis;
    }

    public void manageCategories(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ManageCategories.fxml"));
        Parent root = loader.load();
        ManageCategories manageCategories = loader.getController();
        manageCategories.setAccountingSystem(asis);
        Stage stage = (Stage) ManageCategoriesButt.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void upCompanyInfo(ActionEvent actionEvent) {
        Dialog <String> dialog = new Dialog<>();
        dialog.setTitle("Update company info");
        dialog.setHeaderText("Edit the following:");
        dialog.setResizable(true);

        Label label1 = new Label("Company name: ");
        Label label2 = new Label("Company email: ");
        TextField text1 = new TextField(asis.getCompanyName());
        TextField text2 = new TextField(asis.getEmail());

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType b) {

                if (b == buttonTypeOk) {
                    asis.setCompanyName(text1.getText());
                    asis.setEmail(text2.getText());
                    setAccountingSystem(asis);
                    accountingSystemHib.edit(asis);
                }

                return null;
            }
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(s -> System.out.println("Result: " + s));
    }

    public void exitApp(ActionEvent actionEvent) {
        //DataRW.writeLibraryToFile(asis);
        Platform.exit();
    }
}
