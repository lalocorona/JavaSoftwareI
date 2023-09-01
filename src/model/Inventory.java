package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class has functions for accessing the inventory parts/products.
 */
public class Inventory {

    //All Parts
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    //All Products
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();


    // This function lets you add parts to the inventory.
    public static void addPart(Part part){

        allParts.add(part);
    }

    //This function lets you add products to the inventory.
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    //This function looks up Parts by the parID
    public static int lookupPart(int partId){
        int partIdFound = Integer.parseInt(null);

        for(Part part : allParts) {
            if (part.getId() == partId) {
                partIdFound = partId;
            }
        }
        return partIdFound;
    }

    //This function looks up Products by productID
    public static Product lookupProduct(int productID){
        int productIdFound = Integer.parseInt(null);

        for(Product product : allProducts){
            if (product.getId() == productID){
                productIdFound = productID;
            }
        }
        return null;
    }

    //This function looks up parts using its name
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partNameFound = FXCollections.observableArrayList();

        for (Part part : allParts){
            if (part.getName().equals(partName)) {
                partNameFound.add(part);
            }
            return partNameFound;
        }
        return partNameFound;
    }

    //This function looks up products using its name
    public static ObservableList<Product> lookupProduct(String productName){
        return null;
    }

    //This function updates the Part that is selected
    public static Part updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
        return selectedPart;
    }

    //This function updates the product that is selected
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    //This function deletes parts
    public static boolean deletePart(Part selectedPart) {

        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else{
            return false;
        }
    }

    //This function deletes product
    public static boolean deleteProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
        }
        return true;
    }

    //The getter for Inventory. This also displays the Parts in the table that's observable.
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    //The getter for the Inventory. This also displays the Products in the table that's observable.
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
