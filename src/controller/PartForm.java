package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class adds parts to the inventory and includes a toggle button
 * for either in-house or outsourced.
 */
public class PartForm implements Initializable {

    public Label changeMe;
    public RadioButton InHouse;
    public RadioButton Outsourced;
    public ToggleGroup tgroup;
    public TextField addPartName;
    public TextField addPartStock;
    public TextField addPartCost;
    public TextField addMaxPart;
    public TextField addMinPart;
    public TextField changeMeText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
    }

    /**
     * This method brings you back to the main form.
     */
    public void toMainForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method changes the text label at the bottom to display "Machine ID" .
     */
    public void onInHouse(ActionEvent actionEvent) {
        changeMe.setText("Machine ID");
    }

    /**
     * This method changes the text label at the bottom to display "Company Name"
     */
    public void onOutsourced(ActionEvent actionEvent) {
        changeMe.setText("Company Name");
    }

    /**
     * This method adds parts to the Inventory and saves them, so they are displayed back in the main menu and used throughout the program.
     * There are also exceptions if a user inputs an incorrect value into the text-fields. A try and catch method is used to display an error message to the UI.
     * <p>RUNTIME ERROR - Fixed by correcting the scene exception throw. <p/>
     *
     * @param actionEvent
     * @throws IOException
     */
    public void savePart(ActionEvent actionEvent) throws IOException {

        int i = 0;
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() > i) {
                i = part.getId();
                System.out.println("The current id is " + part.getId());
            }
        }

        try {
            int stock = Integer.parseInt(addPartStock.getText());
            double price;
            int min = Integer.parseInt(addMinPart.getText());
            int max = Integer.parseInt(addMaxPart.getText());


            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The maximum must be greater than then minimum");
                alert.showAndWait();
                return;
            }

            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You must enter a valid stock that's between the minimum and Maximum.");
                alert.showAndWait();
                return;
            }

            if (InHouse.isSelected()) {
                String partNameText = addPartName.getText();
                stock = Integer.parseInt(addPartStock.getText());
                price = Double.parseDouble(addPartCost.getText());
                min = Integer.parseInt(addMinPart.getText());
                max = Integer.parseInt(addMaxPart.getText());
                Integer machineId = Integer.parseInt(changeMeText.getText());

                Inventory.addPart(new InHouse(i + 1, partNameText, price, stock, min, max, machineId));

            }

            if (Outsourced.isSelected()) {
                String partNameText = addPartName.getText();
                stock = Integer.parseInt((addPartStock.getText()));
                price = Double.parseDouble(addPartCost.getText());
                min = Integer.parseInt(addMinPart.getText());
                max = Integer.parseInt(addMaxPart.getText());
                String companyName = changeMeText.getText();

                Inventory.addPart(new OutSourced(i + 1, partNameText, price, stock, min, max, companyName));

            }


            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 400);
            stage.setTitle("Main Form");
            stage.setScene(scene);
            stage.show();

        } catch (Exception error) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setContentText("Values are incorrect");
            alert.showAndWait();
        }

    }
}
