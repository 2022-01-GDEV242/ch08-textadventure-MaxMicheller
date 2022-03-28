
/**
 * An "item" is found in a room and is part of an interactable enviornment
 *
 * @author Max Micheller
 * @version 3/21/2022
 */
public class Item
{
    private String description;
    private float weight;
    
    /** 
     * initilizes the items, giving them descriptions and weights.
     */
    public Item(String description, float weight) 
    {
        this.description = description;
        this.weight = weight;
    }
    
    /**
     * @return the description of the item.
     */
    public String getItemDescription(){
        return description;
    }
    
    /**
     * @return that the item has been removed.
     */
    public String removeItem(){
        description = "nothing";
        return description;
    }
}
