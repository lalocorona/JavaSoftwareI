package model;

/**This Class extends Part's Class. Outsourced Inherits Part's Class
 *
 */
public class OutSourced extends Part{
    private String companyName;

    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    //This is the setter for the Company Name
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    //This is the getter for the Company Name
    public String getCompanyName(){
        return companyName;
    }

}
