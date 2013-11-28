package il.ac.shenkar.ronesn;

/**
 * Created by noam on 11/11/13.
 */
public class ItemDetails {

    private String name;
    private int quantity;
    private Long id;

    public ItemDetails(String name,int quantity,Long id){
        this.quantity=quantity;
        this.name=name;
        this.id=id;
    }
    void setName(String name){this.name=name;}
    void setQuantity(int quantity){this.quantity=quantity;}
    String getName (){return name;}
    int getQuantity() {return quantity;}
    public long getId(){
        return id;}

    public String toString(){
        return getName();
    }

}
