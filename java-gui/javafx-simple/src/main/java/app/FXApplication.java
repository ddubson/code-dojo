package app;

import app.model.Person;
import app.view.PersonEditDialogController;
import app.view.PersonOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import static javafx.stage.Modality.WINDOW_MODAL;

@SpringBootApplication
public class FXApplication extends Application implements CommandLineRunner {
    private LayoutConfig layoutConfig;
    private Stage primaryStage;
    private SampleData data;

    public static void main(String[] args) {
        SpringApplication.run(FXApplication.class, args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.layoutConfig = new LayoutConfig();
        this.data = new SampleData();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        // Show the scene containing the root layout.
        try {
            Scene scene = new Scene(layoutConfig.rootLayout());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            layoutConfig.rootLayout().setCenter(layoutConfig.personOverview());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Give the controller access to the main app.
        PersonOverviewController controller = layoutConfig.personOverviewLoader().getController();
        controller.setFXApplication(this);
    }

    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = this.layoutConfig.personEditDialogLoader();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public SampleData getData() {
        return data;
    }

    @Override
    public void run(String... args) throws Exception {
        launch(args);
    }
}
