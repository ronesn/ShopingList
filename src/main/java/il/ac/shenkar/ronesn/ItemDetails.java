package il.ac.shenkar.ronesn;

/**
 * Created by noam on 11/11/13.
 */
public class ItemDetails implements Comparable<ItemDetails>{

    private String name;
    private int id;
    private int quantity;
    private String listName;
    public boolean cart;

    public ItemDetails(String name,int quantity,int id,String listName){
        this.quantity=quantity;
        this.name=name;
        this.id=id;
        this.listName=listName;
        cart=false;
    }

    public ItemDetails() {

    }


    void setName(String name){this.name=name;}
    void setQuantity(int quantity){this.quantity=quantity;}
    void setListName(String name){this.listName=name;}
    public void setID(int id){this.id = id;}
    String getName (){return name;}
    int getQuantity() {return quantity;}
    public  int getId(){
        return id;}
    String getListName(){return listName;}
    public String toString(){
        return getName();
    }


    @Override
    public int compareTo(ItemDetails another) {

       return this.id-another.id;
    }
}
