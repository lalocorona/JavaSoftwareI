package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the controller for adding products to inventory.
 */
public class AddProductForm implements Initializable {
    public TableView<Part> associatedPartsTable;
    public TableView<model.Part> partsTable;
    public TextField addProductName;
    public TextField addProductStock;
    public TextField addProductPrice;
    public TextField addProductMax;
    public TextField addProductMin;
    public TextField addProductID;
    public TextField searchPart;
    public TableColumn<Object, Object> partsPartID;
    public TableColumn<Object, Object> partsPartName;
    public TableColumn<Object, Object> partsInventoryLevel;
    public TableColumn<Object, Object> partsPrice;

    private final ObservableList<Part>associatedPart = FXCollections.observableArrayList();

    public TableColumn associatedPartCost;
    public TableColumn associatedPartStock;
    public TableColumn associatedPartName;
    public TableColumn associatedPartID;

    /** This method initializes the tables in the product contoller.
     *
     * <p>RUNTIME ERROR - When adding an associated part to the product,
     * all the products duplicated those associated parts. This bug was
     * fixed by separating the functions by updating the table
     * and associating the part to a product. <p/>
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());

        partsPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(associatedPart);

        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        updateAssociatedPart();

    }

    /**
     * This method updates the associated parts' table in the form.
     * <p>RUNTIME ERROR - The associated parts was not properly loading
     * to the correct table. This was debugged by using the correct
     * Table fx:id.<p/>
     */
    private void updateAssociatedPart() {
        associatedPartsTable.setItems(associatedPart);

    }


    /**
     *This is the method for the searching part(s) in the text box field
     * @param actionEvent
     * <p>RUNTIME ERROR - An error message appears if there is no part found and returns a dialogue box
     * Returns that found part(s) based on ID or partial name in the Observable list. <p/>
     */
    public void searchPart(ActionEvent actionEvent) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();

        String searchPartName = searchPart.getText();


        for (Part pa : allParts) {
            if (pa.getName().contains(searchPartName) || String.valueOf(pa.getId()).contains(searchPartName)) {
                foundPart.add(pa);
                partsTable.setItems(foundPart);
            }
        }
        if(foundPart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("There is no part selected!");
            alert.showAndWait();
        }

    }

    /**This method allows you to add an associated part from the parts table
     * and associates it to the product. It also updates the associated table.
     *
     * <p>RUNTIME ERROR the associated part was being added to all the products in the inventory.<p/>
     * @param actionEvent
     */
    public void addAssociated(ActionEvent actionEvent) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        /*
         * Adding a separate function that updates the table fixed the issue of the associated
         * part being added to all the products in inventory.
         */
        if(selectedPart != null) {
            associatedPart.add(selectedPart);
            updateAssociatedPart();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"You must add an associated part.");
            alert.showAndWait();
        }
    }

    /**
     * This method deletes the associated part from the product and the associated part table.
     */
    public void removeAssociated(ActionEvent actionEvent) {
        Part selectedAssociatedPart = associatedPartsTable.getSelectionModel().getSelectedItem();

        if(selectedAssociatedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There isn't an associated part selected.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure tou want to delete this associated part?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            System.out.println("The part" + associatedPart + " was deleted.");
            associatedPart.remove(selectedAssociatedPart);
        }
    }

    /** This method saves the product that you are adding to the inventory
     * <p>RUNTIME ERROR - The code added the product but an error occurred with the id.
     * This was fixed by using the correct getter.<p/>
     * @param actionEvent
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        int id;
        int stock;
        int max;
        int min;

        id = 0;
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() > id) {
                id = product.getId();
                System.out.println("The current id is " + product.getId());
            }
        }

        try {
            stock = Integer.parseInt(addProductStock.getText());
            max = Integer.parseInt(addProductMax.getText());
            min = Integer.parseInt(addProductMin.getText());

            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The minimum cannot be less than the maximum");
                alert.showAndWait();
                return;
            }

            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The inventory level must be in between the min and the max.");
                alert.showAndWait();
                return;
            }

            Product product = new Product(0, null, 0, 0, 0, 0);
            product.setId(++id);
            product.setName(this.addProductName.getText());
            product.setStock(Integer.parseInt(this.addProductStock.getText()));
            product.setMin(Integer.parseInt(this.addProductMin.getText()));
            product.setMax(Integer.parseInt(this.addProductMax.getText()));
            product.setPrice(Double.parseDouble(this.addProductPrice.getText()));

            product.addAssociatedPart(associatedPart);
            Inventory.addProduct(product);


            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 400);
            stage.setTitle("Main Form");
            stage.setScene(scene);
            stage.show();

        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setContentText("Values are incorrect");
            alert.showAndWait();
        }
    }



    /**This method brings you back to the Main Form.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void toMainForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }
}
