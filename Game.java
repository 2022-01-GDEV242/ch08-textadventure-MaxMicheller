import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes (original)
 * @version 2016.02.29 (original)
 * 
 * @author Max Micheler
 * @version 3/14/2022
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Item heldItem;
    private Stack <Room> history;
    private float health;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        history = new Stack<>();
        health = 10;
        createWorld();
        parser = new Parser();        
    }
    
    /**
     * Invokes game within BlueJ by instantiating the Game class
     * and invoking play() method
     */
    public static void main (String[] args){
        Game game = new Game();
        game.play();
    }
    
     /**
     * Create all the rooms and link their exits together.
     */
    private void createWorld()
    {
        Room gate, courtYard, garden, shed, lobby, guestArea, 
        servantKitchen, ballRoom, staircase, leftWing, 
        rightWing, library, office, bedroomB, bedroomA, balkony;
        
        Item banana, apple, coconut, grapefruit, potato, tomato, watermelon,
        lettuce, mango, onion, lemon, cauliflower, pumpkin, cucumber, papaya, durian;
      
        
        // create the rooms and items. giving items designated rooms and weights
        gate = new Room("at the front gate");
        banana = new Item("a banana", 120);
        gate.setItem(banana);
         
        courtYard = new Room("in the courtyard, shadowed by the towering mansion");
        apple = new Item("a apple", 195);
        courtYard.setItem(apple);
        
        garden = new Room("in the garden where giant dead trees loom over you");
        coconut = new Item("a coconut", 680);
        garden.setItem(coconut);
        
        shed = new Room("in the shed of the gardener");
        grapefruit = new Item("a grapefruit", 246);
        shed.setItem(grapefruit);

        lobby = new Room("in the lobby");
        potato = new Item("a potato", 184);
        lobby.setItem(potato);

        guestArea = new Room("in the guest area");
        tomato = new Item("a tomato", 170);
        guestArea.setItem(tomato);

        servantKitchen = new Room("in the servant kitchen");
        watermelon = new Item("a watermelon", 9000);
        servantKitchen.setItem(watermelon);

        ballRoom = new Room("in the ball room, where you can hear faint music");
        lettuce = new Item("a lettuce", 800);
        ballRoom.setItem(lettuce);

        staircase = new Room("on the staircase");
        mango = new Item("a mango", 200);
        staircase.setItem(mango);

        leftWing = new Room("in the left wing with giant torn portraits on either side");
        onion = new Item("a onion", 160);
        leftWing.setItem(onion);

        rightWing = new Room("in the right wing with a balkony at the end");
        lemon = new Item("a lemon", 100);
        rightWing.setItem(lemon);

        library = new Room("in the library, where you cant help but sneeze");
        cauliflower = new Item("a cauliflower", 500);
        library.setItem(cauliflower);

        office = new Room("in the office, and for some reason its very tidy");
        pumpkin = new Item("a pumpkin", 4500);
        office.setItem(pumpkin);

        bedroomB = new Room("in bedroom B where there seems to be a lump under the sheets");
        cucumber = new Item("a cucumber", 250);
        bedroomB.setItem(cucumber);

        bedroomA = new Room("in bedroom A");
        papaya = new Item("a papaya", 450);
        bedroomA.setItem(papaya);

        balkony = new Room("on the balkony which doesn't look safe");
        durian = new Item("a durian", 6800);
        balkony.setItem(durian);
        
        // initialise room exits
        gate.setExit("north", courtYard);
        
        courtYard.setExit("south", gate);
        courtYard.setExit("west", garden);
        courtYard.setExit("east", shed);
        courtYard.setExit("north", lobby);
        
        garden.setExit("east", courtYard);
        
        shed.setExit("west", courtYard);
        
        lobby.setExit("south", courtYard);
        lobby.setExit("west", guestArea);
        lobby.setExit("east", ballRoom);
        lobby.setExit("north", staircase);
        
        guestArea.setExit("west", servantKitchen);
        guestArea.setExit("east", lobby);
        
        servantKitchen.setExit("east", guestArea);
        
        ballRoom.setExit("west", lobby);
        
        staircase.setExit("south", lobby);
        staircase.setExit("west", leftWing);
        staircase.setExit("east", rightWing);

        leftWing.setExit("south", library);
        leftWing.setExit("east", staircase);
        leftWing.setExit("north", bedroomB);
        
        library.setExit("north", leftWing);
        
        bedroomB.setExit("south", leftWing);
        
        rightWing.setExit("south", office);
        rightWing.setExit("west", staircase);
        rightWing.setExit("east", balkony);
        rightWing.setExit("north", bedroomA);
        
        bedroomA.setExit("south", rightWing);
        
        office.setExit("north", rightWing);
        
        balkony.setExit("west", rightWing);
        balkony.setExit("down", shed);
                       
        currentRoom = gate;  // start game gate
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.\n\n\n\n");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Virtual Mansion!");
        System.out.println("The Virtual Mansion awaits.\n");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case LOOK:
                look(command);
                break;
                            
            case BACK:
                back(command);
                break;
                
            case HAT:
                hat(command);
                break;
                
            case EAT:
                eat(command);
                break;
                
            case HEALTH:
                health(command);
                break;                

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are wandering around this mansion alone, ");
        System.out.println("careful not to fall. \n");
        System.out.print("Your command words are:   ");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            history.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * look around the room
     */
    private void look(Command command)
    {
        if (currentRoom.getShortDescription().equals("on the balkony which doesn't look safe"))
        {
            System.out.println("You look over the edge of of the balcony," +
                " and before you knew it, you fell. ");
            System.out.println(".\n.\n.\n.");
            System.out.println("You look around, seeing tools scattered around and a freshly made " + 
                "hole in the roof.\nYou are lucky to be alive.\n");
            
            
            String direction = "down";
            // Try to leave current room.
            Room nextRoom = currentRoom.getExit(direction);   
            while(history.empty() != true)
            {
                history.pop();
            }
            //history.push(currentRoom);
            currentRoom = nextRoom;
            
            System.out.println(currentRoom.getLongDescription());
        }
        else{
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
     /**
     * goes back one room at a time.
     */
    private void back(Command command){
        if (history.empty()) {
            System.out.println("There is no door!");
        }
        else {
            Room nextRoom = history.pop();
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
        
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * looks if you have a hat.
     */
    private void hat(Command command){
        System.out.println("Sadly, you dont have a hat.");
    }
    
    /**
     * eat the item in the room
     * removes the item from the game
     */
    private void eat(Command command){
        System.out.println(currentRoom.eatenItem());
        currentRoom.removeItem(); 
    }
    
    /**
     * shows the amount of health you have.
     */
    private void health(Command command){
        System.out.println("You have " + health + " health.");
    }
}
