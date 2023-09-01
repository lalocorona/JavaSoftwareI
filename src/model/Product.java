package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    //This is the constructor for the products
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //This is the setter for the id
    public void setId(int id){
        this.id = id;
    }

   //This is the setter for the id
   public void setName(String name){
        this.name = name;
   }
   //This is the setter for the price
   public void setPrice(double price){
        this.price = price;
   }

   //This is the setter for the stock
   public void setStock(int stock){
        this.stock = stock;
   }

   //This is the setter for the min
    public void setMin(int min){
        this.min = min;
    }

    //This is the setter for the max
    public void setMax(int max){
        this.max = max;
    }

    //This is the getter for the id
    public int getId(){
        return id;
    }

   //This is the getter for the String
   public String getName(){
        return name;
   }

   //This is the getter for the price
    public double getPrice(){
        return price;
    }

    //This is the getter for the Stock
    public int getStock(){
        return stock;
    }

   //This is the getter for the Minimum
    public int getMin(){
        return min;
    }

    //This is the getter for the Maximum
    public int getMax(){
        return max;
    }

    public void addAssociatedPart(ObservableList<Part> part){

        this.associatedParts.addAll(part);
    }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else{
            return false;
        }
    }

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
