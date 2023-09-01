package controller;

import com.sun.scenario.effect.InvertMask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This class loads the main the form of the program with button functionality.
 * <p>FUTURE-ENHANCEMENT - Having a drop down menu for the products to see what associated
 * parts are attatched. <p/>
 */
public class MainForm implements Initializable {

    public TableView partsTable;

    public TableColumn partsPartID;
    public TableColumn partsPartName;
    public TableColumn partsInventoryLevel;
    public TableColumn partsPrice;

    public TableView productsTable;
    public TableColumn productsID;
    public TableColumn productsName;
    public TableColumn productsInventoryLevel;
    public TableColumn productsPrice;

    private final ObservableList<Part> allParts = FXCollections.observableArrayList();

    private final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private final ObservableList<Part> foundPart = FXCollections.observableArrayList();


    private static boolean firstTime = true;
    public TextField searchPart;
    public Button partSearchButton;
    public TextField searchProduct;

    private static Product productModify;

    public static Product getSelectedProductModify(){
        return productModify;
    }

    private static Part modifyPart;

    public static Part getModifyPart(){
     return modifyPart;
    }

    Product selectedProduct;

    private Parent scene;



    /** This method adds test data for the application.
     * <p>FUTURE_ENHANCEMENT - The test data for products
     * can include associated parts once intialized.<p/>
     */
    public void addTestData() {
        if (!firstTime) {
            return;
        }
        firstTime = false;

        InHouse tire = new InHouse(1, "Tire", 50.55, 2, 1, 10, 11);
        Inventory.addPart(tire);

        OutSourced wheel = new OutSourced(2, "Wheel", 11.00, 16, 1, 20, "Super Bikes");
        Inventory.addPart(wheel);

        InHouse seat = new InHouse(3,"Seat", 10.00, 4, 1,15, 12);
        Inventory.addPart(seat);

        Product bike = new Product(1000, "Adventure Ebike", 299.99, 5, 1, 20);
        Inventory.addProduct(bike);

        Product tricycle = new Product(1001, "Radio Flyer", 58.99, 3, 1, 20);
        Inventory.addProduct(tricycle);

    }


    /**This method initializes and populates the tables to the MainForm.fxml scene.
     * <p>RUNTIME ERROR - Test data was not loaded properly to the tableviews. I learned
     * the bug was cause by not having the properly attaching the correct fx:id. <p/>
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //The Parts Table on the main form.
        addTestData();

        partsTable.setItems(Inventory.getAllParts());

        partsPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());

        productsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        System.out.println("I am initialized!");
    }

    /**This method exits out of the entire program.
     *
     * @param actionEvent
     */
    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**This method switches to 'Add Parts Form'.
     * <p>FUTURE ENHANCEMENT - A method to load the correct controller. I noticed a lot
     * redundent code for switched FXML views. <p/>
     * @param actionEvent
     * @throws IOException
     */
    public void toPartForm(ActionEvent actionEvent) throws IOException {
        System.out.println("Add Button Was Clicked");

        Parent root = FXMLLoader.load(getClass().getResource("/view/PartForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle("Add Parts Form");
        stage.setScene(scene);
        stage.show();

    }


    /**This method controls the Button to get the 'Modify Part Form'
     *
     * <p>RUNTIME ERROR - The error was occuring because I was loading the contorller
     * incorrectly and passing the data incorrectly. It was fixed by watching
     * the "passing the football" Webaniar. I loaded the controller incorrectly.<p/>
     * @param actionEvent
     * @throws IOException
     */
    public void toModifyPartForm(ActionEvent actionEvent) throws IOException {


        modifyPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if(modifyPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a part first before you can modify it.");
            alert.showAndWait();

            return;
        }



        System.out.println("Modify Part Button Was Clicked");
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPartForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        }


    /**This method controls the Button to get to the 'Add Product Form'
     *
     * @param actionEvent
     * @throws IOException
     */
    public void toAddProductForm(ActionEvent actionEvent) throws IOException {
        System.out.println("Add Product Button Was Clicked!");


        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 550);
        stage.setTitle("Add Product Form");
        stage.setScene(scene);
        stage.show();
    }

    /**This method controls the Button to get to the 'Modify Product Form'
     * <p>RUNTIME ERROR - I kept getting a  when attempting to pass the selected product's
     * associated parts to the next scene. I watched the passing the football Webinar.<p/>
     * @param actionEvent
     * @throws IOException
     */
    public void toModifyProductForm(ActionEvent actionEvent) throws IOException {
        Product productModify = (Product) productsTable.getSelectionModel().getSelectedItem();
        System.out.println("Modify Product Button Was Clicked");


        //Set the stage
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProductForm.fxml"));
        scene = loader.load();


       // Get controller from scene 2
        ModifyProductForm controller = loader.getController();
        controller.modifySelectedProduct(productModify);
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method deletes the selected part in the main screen.
     *
     * @param actionEvent
     */
    public void deletePart(ActionEvent actionEvent) {

        System.out.println("The delete button for parts was pressed.");
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Warning! You have to select a part first!");
            alert.showAndWait();

            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete the selected part?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            System.out.println("The part" + selectedPart + " was deleted.");
            Inventory.deletePart(selectedPart);
        } else {
            return;
        }
    }

    /** This is method deletes the selected Product in the main screen.
     * <p>RUNTIME ERROR - When a selected product is null a runtime error occured.
     * This was debugged by adding an if statement. <p/>
     * @param actionEvent
     */
    public void deleteProduct(ActionEvent actionEvent) {
        System.out.println("The delete product was selected.");
        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null){
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("You must select a product first.");
             alert.showAndWait();
             return;
        }

        /*
         * ObservableList was still accesed regarless of a product not being selected.
         * This was fixed by adding a return in the if statement above.
         */

        ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
        if(associatedParts.size() >= 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Cannot delete product it has an associated part");
            alert.showAndWait();
        }

        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> optional = alert.showAndWait();

            Inventory.deleteProduct(selectedProduct);
            System.out.println("The product" + selectedProduct + " was deleted from Inventory.");
        }


    }

    /**
     *This method searches for a part(s) using the text box field
     * @param actionEvent
     * An error message appears if there is no part found and returns a dialogue box
     * Returns that found part(s) based on ID or partial name in the Observable list
     */
    public void searchPartField(ActionEvent actionEvent) {
     System.out.println("The search field was typed in!");
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
                alert.setContentText("The part you are looking for does not exist!");
                alert.showAndWait();
            }

        return;
    }

    /**This the search functionally when the user pushes a button to search for a part(s).
     * @param actionEvent
     * An error message appears if there is no part found and returns a dialogue box
     * Returns that found part(s) based on ID or partial name in the Observable list
     */
    public void searchPartButton(ActionEvent actionEvent) {
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
                alert.setContentText("The part you are looking for does not exist!");
                alert.showAndWait();
        }

    }


    /**This function allows the user to search a product(s) by its name and product ID
     *
     * @param actionEvent
     * An error message appears if there is no prodcut found and returns a dialogue box
     * Returns that found product(s) based on ID or partial name in the Observable list
     */
    public void searchProductField(ActionEvent actionEvent) {
        System.out.println("The search field was typed in.");

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> foundProduct = FXCollections.observableArrayList();

        String searchProductName = searchProduct.getText();

        for(Product pr : allProducts){
            if(pr.getName().contains(searchProductName)|| String.valueOf(pr.getId()).contains(searchProductName)){
                foundProduct.add(pr);
                productsTable.setItems(foundProduct);
            }
        }

        if (foundProduct.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("The product you are looking for cannot be found.");
            alert.showAndWait();

        }

    }

    /**This function allows the user to search a product name or id using the button.
     *
     * @param actionEvent
     * An error message appears if there is no prodcut found and returns a dialogue box
     * Returns that found product(s) based on ID or partial name in the Observable list
     */
    public void searchProductButton(ActionEvent actionEvent) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> foundProduct = FXCollections.observableArrayList();

        String searchProductName = searchProduct.getText();

        for (Product pr : allProducts) {
            if (pr.getName().contains(searchProductName) || String.valueOf(pr.getId()).contains(searchProductName)) {
                foundProduct.add(pr);
                productsTable.setItems(foundProduct);
            }
        }

        if (foundProduct.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("The product you are looking for cannot be found.");
            alert.showAndWait();

        }
    }
}
