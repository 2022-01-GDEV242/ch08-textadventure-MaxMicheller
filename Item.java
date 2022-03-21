
/**
 * An "item" is found in a room and is part of an interactable enviornment
 *
 * @author Max Micheller
 * @version 3/21/2022
 */
public class Item
{
    private String description;
    private int weight;
    
    public Item(String description, int weight) 
    {
        this.description = description;
        this.weight = weight;
    }
    
    public String getItemDescription(){
        return description;
    }
}
