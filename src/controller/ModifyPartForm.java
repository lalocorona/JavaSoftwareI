package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ResourceBundle;

/**
 * This class allows you to modify a part that already exists in the inventory
 * and updates it in the controller.
 */
public class ModifyPartForm implements Initializable {
    public Label changeMe;
    public TextField changeMeText;
    public RadioButton InHouse;
    public RadioButton Outsourced;
    public ToggleGroup tgroup;
    public TextField modifyPartName;
    public TextField modifyPartStock;
    public TextField modifyPartCost;
    public TextField modifyMaxPart;
    public TextField modifyMinPart;
    public TextField modifyPartId;

    private int index;
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private Part selectedPart;


    /**
     * This method brings you back to the main form.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void toMainForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
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

    /** This method saves a modified part selected from the main form and updates the inventory.
     * There is also test to make sure an inventory item is in between the max or min
     * It also tests that the min isn't greater than the max or vice versa.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void saveModifyPart(ActionEvent actionEvent) throws IOException {

        try {
            int id = Integer.parseInt(modifyPartId.getText());
        String name = modifyPartName.getText();
        int stock = Integer.parseInt(modifyPartStock.getText());
        double price = Double.parseDouble(modifyPartCost.getText());
        int min = Integer.parseInt(modifyMinPart.getText());
        int max = Integer.parseInt(modifyMaxPart.getText());

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
                int machineID = Integer.parseInt(changeMeText.getText());
                Part modifiedPart = new InHouse(id, name, price, stock, min, max, machineID);

                name = modifyPartName.getText();
                stock = Integer.parseInt(modifyPartStock.getText());
                price = Double.parseDouble(modifyPartCost.getText());
                min = Integer.parseInt(modifyMinPart.getText());
                max = Integer.parseInt(modifyMaxPart.getText());

                Inventory.addPart(modifiedPart);
                Inventory.deletePart(selectedPart);


            }

            if (Outsourced.isSelected()) {
                String companyName = changeMeText.getText();
                Part modifiedPart = new OutSourced(id, name, price, stock, min, max, companyName);


                id = Integer.parseInt(modifyPartId.getText());
                name = modifyPartName.getText();
                price = Double.parseDouble(modifyPartCost.getText());
                stock = Integer.parseInt(modifyPartStock.getText());
                min = Integer.parseInt(modifyMinPart.getText());
                max = Integer.parseInt(modifyMaxPart.getText());
                companyName = changeMeText.getText();

                Inventory.deletePart(selectedPart);
                Inventory.addPart(modifiedPart);


            }

            //This gets you back to the main screen.
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 400);
            stage.setTitle("Main Form");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You cannot leave something blank.");
            alert.showAndWait();
        }
    }


    /** This method initializer loads the selected part from
     * the Main Form to be accessed in this controller.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = MainForm.getModifyPart();
        if (selectedPart instanceof model.InHouse) {
            InHouse.setSelected(true);
            changeMe.setText("Machine ID");
            changeMeText.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));

        }

        if (selectedPart instanceof model.OutSourced) {
            Outsourced.setSelected(true);
            changeMe.setText("Company Name");
            changeMeText.setText(String.valueOf(((OutSourced) selectedPart).getCompanyName()));
        }

        modifyPartId.setText(String.valueOf(selectedPart.getId()));
        modifyPartName.setText(String.valueOf(selectedPart.getName()));
        modifyPartStock.setText(String.valueOf(selectedPart.getStock()));
        modifyPartCost.setText(String.valueOf(selectedPart.getPrice()));
        modifyMinPart.setText(String.valueOf(selectedPart.getMin()));
        modifyMaxPart.setText(String.valueOf(selectedPart.getMax()));


    }
}
