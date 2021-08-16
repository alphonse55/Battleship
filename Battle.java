import java.util.*;

public class Battle {
    public static Scanner scanner = new Scanner(System.in);

    // colors
    public static String RESET = "\u001B[0m";
    public static String RED = "\u001B[31m";
    public static String GREEN = "\u001B[32m";
    public static String BLUE = "\u001B[34m";

    // constants
    public static int[] ship_lengths = {2, 3, 3, 4, 5};
    public static String[] ship_names = {"Patrol Boat", "Submarine", "Destroyer", "Battleship", "Carrier"};
    public static int num_ships = ship_lengths.length;
    public static Ship[] computer_ships = new Ship[num_ships];
    public static Ship[] player_ships = new Ship[num_ships];

    public static void sleep(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void clear_console(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // "C3" -> [2, 2] 
    public static int[] convert_position(String position) throws IllegalArgumentException{
        boolean valid = false;

        if (position.length() > 1){
            if (("ABCDEFGHIJ".indexOf(position.charAt(0)) > -1) && ("123456789".indexOf(position.charAt(1)) > -1)){
                if (position.length() == 2){
                    valid = true;
                }
                else if (position.length() == 3){
                    if (("123456789".indexOf(position.charAt(1)) == 0) && (String.valueOf(position.charAt(2)).equals("0"))){
                        valid = true;
                    }
                }
            }
        }

        if (!valid){
            throw new IllegalArgumentException("Exception thrown");
        }

        int x = "ABCDEFGHIJ".indexOf(position.charAt(0));
        int y = Integer.parseInt(String.valueOf(position.charAt(1))) - 1;
        if (position.length() == 3){
            y = 9;
        }
        int[] a = {x, y};
        return a;
    }

    // [2, 2] -> "C3"
    public static String convert_position(int[] position){
        String letter = String.valueOf("ABCDEFGHIJ".charAt(position[0]));
        String number = String.valueOf(position[1] + 1);
        return letter + number;
    }

    // generate computer field
    public static Field computer_field(){
        boolean[][] computer_grid = new boolean[10][10];
        for (int y = 0; y < 10; y++){
            for (int x = 0; x < 10; x++){
                computer_grid[y][x] = false;
            }
        }

        for (int s = 0; s < num_ships; s++){
            int x = -1;
            int y = -1;
            int orientation = 0;

            boolean ship_done = false;
            while (!ship_done){
                orientation = (int) (Math.random() + 0.5); // 0 or 1; 0 -> x, 1 -> y
                if (orientation == 0){
                    x = (int) (Math.random() * (10 - ship_lengths[s]));
                    y = (int) (Math.random() * (10));
                    
                    ship_done = true;
                    for (int i = x; i < x + ship_lengths[s]; i++){
                        if (computer_grid[y][i]){
                            ship_done = false;
                        }
                    }
                }
                else if (orientation == 1){
                    x = (int) (Math.random() * (10));
                    y = (int) (Math.random() * (10 - ship_lengths[s]));

                    ship_done = true;
                    for (int i = y; i < y + ship_lengths[s]; i++){
                        if (computer_grid[i][x]){
                            ship_done = false;
                        }
                    }
                }
            }

            computer_ships[s] = new Ship(computer_grid, ship_lengths[s], x, y, orientation, ship_names[s]);
        }
        return new Field(computer_grid);
    }

    // generate player field
    public static Field player_field(){
        boolean[][] player_grid = new boolean[10][10];
        for (int j = 0; j < 10; j++){
            for (int i = 0; i < 10; i++){
                player_grid[j][i] = false;
            }
        }
        clear_console();
        System.out.println();
        Field player_field = new Field(player_grid);
        player_field.print(true);
        System.out.println();
        
        for (int s = 0; s < num_ships; s++){
            System.out.println(ship_names[s] + " (length = " + ship_lengths[s] + ") :\n");

            int x = -1;
            int y = -1;
            int orientation = -1;

            boolean ship_done = false;
            while (!ship_done){

                // get orientation
                orientation = -1;
                while (true){
                    System.out.print("Orientation (0 -> x / 1 -> y) : ");
                    try{
                        orientation = scanner.nextInt();
                        if (orientation == 0 || orientation == 1){
                            break;
                        }
                        else{
                            System.out.println("\nInvalid input, try again.\n");
                        }
                    }
                    catch (InputMismatchException e){
                        System.out.println("\nInvalid input, try again.\n");
                        scanner.nextLine();
                    }
                }

                // get position
                String position = "";
                scanner.nextLine();
                int[] position_array = {};

                boolean valid = false;
                while (!valid){
                    System.out.print("Position (column letter - row number) ex. A1 : ");
                    position = scanner.nextLine();
                    try{
                        position_array = convert_position(position);
                        valid = true;
                    }
                    catch(IllegalArgumentException e){
                        System.out.println("\nInvalid position, try again.\n");
                    }
                }
                x = position_array[0];
                y = position_array[1];

                ship_done = true;
                if (orientation == 0){
                    if (x > 10 - ship_lengths[s]){
                        ship_done = false;
                        System.out.println("\nToo far right.\n");
                    }
                    else{
                        for (int i = x; i < x + ship_lengths[s]; i++){
                            if (player_grid[y][i]){
                                String ship_name = "ship";
                                ship_done = false;
                                int[] coordinates = {i, y};
                                for (int ship = 0; ship < s; ship++){
                                    for (int n_coord = 0; n_coord < player_ships[ship].length; n_coord++){
                                        if (player_ships[ship].ship_coordinates[n_coord][0] == coordinates[0] && player_ships[ship].ship_coordinates[n_coord][1] == coordinates[1]){
                                            ship_name = player_ships[ship].name;
                                        }
                                    }
                                }
                                System.out.println("\nOverlapping " + ship_name + " in " + convert_position(coordinates) + "\n");
                            }
                        }
                    }
                }
                else if (orientation == 1){
                    if (y > 10 - ship_lengths[s]){
                        ship_done = false;
                        System.out.println("\nToo low.\n");
                    }
                    else{
                        for (int i = y; i < y + ship_lengths[s]; i++){
                            if (player_grid[i][x]){
                                String ship_name = "ship";
                                ship_done = false;
                                int[] coordinates = {x, i};
                                for (int ship = 0; ship < s; ship++){
                                    for (int n_coord = 0; n_coord < player_ships[ship].length; n_coord++){
                                        if (player_ships[ship].ship_coordinates[n_coord][0] == coordinates[0] && player_ships[ship].ship_coordinates[n_coord][1] == coordinates[1]){
                                            ship_name = player_ships[ship].name;
                                        }
                                    }
                                }
                                System.out.println("\nOverlapping " + ship_name + " in " + convert_position(coordinates) + "\n");
                            }
                        }
                    }
                }
            }
            player_ships[s] = new Ship(player_grid, ship_lengths[s], x, y, orientation, ship_names[s]);

            clear_console();
            System.out.println();
            player_field.field = player_grid;
            player_field.print(true);
            System.out.println();
        }
        return new Field(player_grid);
    }

    public static void main(String[] args) {
        boolean run = true;
        while (run){
            // initializing fields
            Field computer_field = computer_field();
            Field player_field = player_field();

            System.out.print(GREEN + "All boats placed." + RESET + " Press ENTER to start playing.");
            scanner.nextLine();
            clear_console();

            // GAME
            boolean game = true;
            while (game){
                // player shot
                System.out.println("\nComputer's grid :\n");
                computer_field.print(false);

                int[] shot = {};

                boolean valid = false;
                while (!valid){
                    System.out.print("\nTake a shot : ");
                    String position = scanner.nextLine();
                    try{
                        shot = convert_position(position);
                        valid = true;
                    }
                    catch(IllegalArgumentException e){
                        System.out.println("\nInvalid position, try again.");
                    }
                }

                clear_console();
                System.out.println("\nComputer's grid :\n");
                computer_field.marks[shot[1]][shot[0]] = true;
                computer_field.print(false);

                if (computer_field.field[shot[1]][shot[0]]){
                    System.out.println("\nThat's a hit !\n");
                }
                else{
                    System.out.println("\nYou missed, maybe next time.\n");
                }

                for (Ship ship : computer_ships){
                    for (int[] pos : ship.ship_coordinates){
                        if (shot[0] == pos[0] && shot[1] == pos[1]){
                            ship.safe_coordinates.remove(pos);
                            if (ship.safe_coordinates.size() == 0){
                                System.out.println("You sank the computer's " + ship.name + "!\n");
                                ship.sunk = true;
                                computer_field.ships_sunk++;
                            }
                        }
                    }
                }

                if (computer_field.ships_sunk == num_ships){
                    game = false;
                    System.out.println(GREEN + "You won !\n" + RESET);
                    sleep(3000);
                    System.out.print("Do you want to play again ? (y/n) : ");
                    String answer = "";
                    while (!answer.equals("y") && !answer.equals("n")){
                        answer = scanner.nextLine();
                    }
                    if (answer.equals("n")){
                        run = false;
                    }
                    clear_console();
                    break;
                }

                System.out.print("Press ENTER to continue.");
                scanner.nextLine();
                clear_console();
                
                // computer shot
                int[] computer_shot = {-1, -1};
                System.out.print("\nComputer is taking a shot ...");
                sleep(1000);
                clear_console();
                do {
                    computer_shot[0] = (int) (Math.random() * 10);
                    computer_shot[1] = (int) (Math.random() * 10);
                }
                while (player_field.marks[computer_shot[1]][computer_shot[0]]);

                System.out.println("\nYour grid :\n");
                player_field.marks[computer_shot[1]][computer_shot[0]] = true;;
                player_field.print(true);

                if (player_field.field[computer_shot[1]][computer_shot[0]]){
                    System.out.println("\nYou have been hit at " + convert_position(computer_shot) + "!\n");
                }
                else{
                    System.out.println("\nComputer missed at " + convert_position(computer_shot) + ", you are safe for now.\n");
                }

                for (Ship ship : player_ships){
                    for (int[] pos : ship.ship_coordinates){
                        if (computer_shot[0] == pos[0] && computer_shot[1] == pos[1]){
                            ship.safe_coordinates.remove(pos);
                            if (ship.safe_coordinates.size() == 0){
                                System.out.println("Your " + ship.name + " has been sunk.\n");
                                ship.sunk = true;
                                player_field.ships_sunk++;
                            }
                        }
                    }
                }

                if (player_field.ships_sunk == num_ships){
                    game = false;
                    System.out.println(RED + "You lost !\n" + RESET);
                    sleep(3000);
                    System.out.print("Do you want to play again ? (y/n) : ");
                    String answer = "";
                    while (answer.equals("y") && answer.equals("n")){
                        answer = scanner.nextLine();
                    }
                    if (answer.equals("n")){
                        run = false;
                    }
                    clear_console();
                    break;
                }

                System.out.print("Press ENTER to continue.");
                scanner.nextLine();
                clear_console();
            }
        }
        scanner.close();
    }
}