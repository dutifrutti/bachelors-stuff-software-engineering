 package GUI;

 import AccountingSystem.AccountingSystem;
 import javafx.application.Application;
 import javafx.application.Platform;
 import javafx.event.EventHandler;
 import javafx.fxml.FXMLLoader;
 import javafx.scene.Parent;
 import javafx.scene.Scene;
 import javafx.stage.Stage;
 import javafx.stage.WindowEvent;
 import persistence.AccountingSystemHib;

 import javax.persistence.EntityManagerFactory;
 import javax.persistence.Persistence;

 public class Main extends Application {
    AccountingSystem asis = new AccountingSystem();
    @Override
    public void start(Stage primaryStage) throws Exception{
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("lab5_ign");
        AccountingSystemHib accountingSystemHib = new AccountingSystemHib(factory);



         if (accountingSystemHib.getAccountingSystemList().size()== 0) {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/LoginPage.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Accounting System");
            LogInController login = loader.getController();
            login.setAccountingSystem(asis);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
          }
       else {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindow.fxml"));
           primaryStage.setTitle("Accounting System");
           Parent root = loader.load();
           asis = accountingSystemHib.getFirstAccountingSystem();
           MainWindow mainWindow = loader.getController();
           mainWindow.setAccountingSystem(asis);
           primaryStage.setScene(new Scene(root));
           primaryStage.show();
       }

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                //DataRW.writeLibraryToFile(asis);
                Platform.exit();

            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
