package model;

/**This Class extends from Part Class. The InHouse Class Inherits Part <<Abstract>>
 *
 */
public class InHouse extends Part {

    private int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    //This is the  setter for the machine ID
    public void setMachineID(int machineID){
        this.machineID = machineID;
    }

    //This is the getter function for the machineID
    public int getMachineID(){

        return machineID;
    }
}
