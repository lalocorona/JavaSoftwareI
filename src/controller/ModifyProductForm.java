package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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

import static javafx.collections.FXCollections.observableArrayList;

/**
 * This class allows you to modify a product and save it to the inventory
 * through the controller.
 */
public class ModifyProductForm implements Initializable {
    Product selectedProduct;
    private int productID;
    private Product modifiedProduct;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    public TableView partsTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInv;
    public TableColumn partCost;
    public TableView modifyProductParts;
    public TableColumn associatedPartID;
    public TableColumn associatedPartName;
    public TableColumn associatedPartInv;
    public TableColumn associatedPartCost;
    public TextField searchPart;
    public TextField modifyProductID;
    public TextField modifyProductName;
    public TextField modifyProductInv;
    public TextField modifyProductCost;
    public TextField modifyProductMax;
    public TextField modifyProductMin;


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
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductParts.setItems(associatedParts);

        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        updateAssociatedPart();
    }

    /**This method passes the data from the main page to this controller;
     * <p>RUNTIME ERROR - when choosing to modify a product, the associated part from the previous
     * product loaded to a selected product. This issue was resolved by separating the associated
     * part and associated parts table.<p/>
     *
     * @param selectedProduct
     */
    public void modifySelectedProduct(Product selectedProduct){
        productID = Inventory.getAllProducts().indexOf(selectedProduct);
        modifyProductID.setText(Integer.toString(selectedProduct.getId()));
        modifyProductName.setText(selectedProduct.getName());
        modifyProductInv.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductCost.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMax.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMin.setText(String.valueOf(selectedProduct.getMin()));
        associatedParts.addAll(selectedProduct.getAllAssociatedParts());

    }

    /**This method brings you back to the main Form
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

    /**This method allows you to add an associated part from the parts table
     * and associates it to the product. It also updates the associated table.
     *
     * <p>RUNTIME ERROR - the associated part was being added to all the products in the inventory. <p/>
     * @param actionEvent
     */
    public void addPart(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            associatedParts.add(selectedPart);
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
    public void removeAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart = (Part) modifyProductParts.getSelectionModel().getSelectedItem();

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
            System.out.println("The part" + associatedParts + " was deleted.");
            associatedParts.remove(selectedAssociatedPart);
        }
    }

    /** This method saves the product that you are modifying to the inventory
     * <p>RUNTIME ERROR - This error occurred when saving the products price as an int.
     * I resolved the error by fixing the parse method to double instead of integer. <p/>
     * @param actionEvent
     */
    public void saveModifiedProduct(ActionEvent actionEvent) throws IOException {
       try{
            String name = modifyProductName.getText();
            int stock = Integer.parseInt(modifyProductInv.getText());
            double price = Double.parseDouble(modifyProductCost.getText());
            int min = Integer.parseInt(modifyProductMin.getText());
            int max = Integer.parseInt(modifyProductMax.getText());

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

            Product modifiedProduct = new Product(0,null,0,0,0,0);
            modifiedProduct.setId(Integer.parseInt(this.modifyProductID.getText()));
            modifiedProduct.setName(this.modifyProductName.getText());
            modifiedProduct.setPrice(Double.parseDouble(this.modifyProductCost.getText()));
            modifiedProduct.setStock(Integer.parseInt(this.modifyProductInv.getText()));
            modifiedProduct.setMin(Integer.parseInt(this.modifyProductMin.getText()));
            modifiedProduct.setMax(Integer.parseInt(this.modifyProductMax.getText()));

            modifiedProduct.getAllAssociatedParts().clear();
            modifiedProduct.addAssociatedPart(associatedParts);

            Inventory.updateProduct(productID,modifiedProduct);
 ;

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

    /**
     *This is the method for the searching part(s) in the text box field
     * @param actionEvent
     * An error message appears if there is no part found and returns a dialogue box
     * Returns that found part(s) based on ID or partial name in the Observable list
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

    /**
     * This method updates the associated parts' table in the form.
     */
    private void updateAssociatedPart() {
        modifyProductParts.setItems(associatedParts);

    }
}

