import java.util.*;

public class Battle {
    public static Scanner scanner = new Scanner(System.in);

    // constants
    public static int[] ship_lengths = {2, 3, 3, 4, 5};
    public static String[] ship_names = {"Patrol Boat", "Submarine", "Destroyer", "Battleship", "Carrier"};
    public static int num_ships = ship_lengths.length;
    public static int grid_size = 10;
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
        boolean[][] computer_grid = new boolean[grid_size][grid_size];
        for (int j = 0; j < grid_size; j++){
            for (int i = 0; i < grid_size; i++){
                computer_grid[j][i] = false;
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
                    x = (int) (Math.random() * (grid_size - ship_lengths[s]));
                    y = (int) (Math.random() * (grid_size));
                    
                    ship_done = true;
                    for (int i = x; i < x + ship_lengths[s]; i++){
                        if (computer_grid[y][i]){
                            ship_done = false;
                        }
                    }
                }
                else if (orientation == 1){
                    x = (int) (Math.random() * (grid_size));
                    y = (int) (Math.random() * (grid_size - ship_lengths[s]));

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
        boolean[][] player_grid = new boolean[grid_size][grid_size];
        for (int j = 0; j < grid_size; j++){
            for (int i = 0; i < grid_size; i++){
                player_grid[j][i] = false;
            }
        }
        

        for (int s = 0; s < num_ships; s++){
            System.out.println();
            new Field(player_grid).print();

            System.out.println("\n" + ship_names[s] + " (length = " + ship_lengths[s] + ") : ");

            int x = -1;
            int y = -1;
            int orientation = -1;

            boolean ship_done = false;
            while (!ship_done){
                // get orientation
                orientation = -1;
                while (true){
                    System.out.print("Orientation (x -> 0 / y -> 1) : ");
                    try{
                        orientation = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        System.out.println("Invalid input, try again.");
                        scanner.nextLine();
                    }
                    if (orientation == 0 || orientation == 1){
                        break;
                    }
                    else{
                        System.out.println("Invalid input, try again.");
                    }
                }
                // get position
                String position = "";
                scanner.nextLine();
                int[] position_array = {};

                boolean valid = false;
                while (!valid){
                    System.out.print("Position (row letter - column number) ex.: A1 : ");
                    position = scanner.nextLine();
                    try{
                        position_array = convert_position(position);
                        valid = true;
                    }
                    catch(IllegalArgumentException e){
                        System.out.println("Invalid position, try again.");
                    }
                }
                x = position_array[0];
                y = position_array[1];

                ship_done = true;
                if (orientation == 0){
                    if (x > grid_size - ship_lengths[s]){
                        ship_done = false;
                        System.out.println("Too far right.");
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
                                System.out.println("Overlapping " + ship_name + " in " + convert_position(coordinates));
                            }
                        }
                    }
                }
                else if (orientation == 1){
                    if (y > grid_size - ship_lengths[s]){
                        ship_done = false;
                        System.out.println("Too low.");
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
                                System.out.println("Overlapping " + ship_name + " in " + convert_position(coordinates));
                            }
                        }
                    }
                }
            }
            player_ships[s] = new Ship(player_grid, ship_lengths[s], x, y, orientation, ship_names[s]);
        }
        return new Field(player_grid);
    }

    public static void main(String[] args) {
        clear_console();

        Field computer_field = computer_field();
        computer_field.print();

        Field player_field = player_field();
        player_field.print();
        System.out.println();

        System.out.println("\nAll boats placed. Let's start playing !");
        sleep(2000);

        // GAME
        boolean game = true;
        while (game){
            // player shot
            System.out.println("\nComputer's grid : \n");
            computer_field.print_marks();

            int[] shot = {};

            boolean valid = false;
            while (!valid){
                System.out.print("Take a shot : ");
                String position = scanner.nextLine();
                try{
                    shot = convert_position(position);
                    valid = true;
                }
                catch(IllegalArgumentException e){
                    System.out.println("Invalid position, try again.");
                }
            }
            
            computer_field.marks.add(shot);
            computer_field.print_marks();

            for (Ship ship : computer_ships){
                for (int[] pos : ship.ship_coordinates){
                    if (shot[0] == pos[0] && shot[1] == pos[1]){
                        ship.safe_coordinates.remove(pos);
                        if (ship.safe_coordinates.size() == 0){
                            System.out.println("You sank the computer's " + ship.name + "!");
                            sleep(1000);
                            ship.sunk = true;
                        }
                    }
                }
            }

            sleep(1000);
            clear_console();
            
            // computer shot
            int[] computer_shot = {-1, -1};
            boolean shot_in_marks = true;
            System.out.println("Computer taking a shot ...\n");
            sleep(1000);
            do{
                computer_shot[0] = (int) (Math.random() * grid_size);
                computer_shot[1] = (int) (Math.random() * grid_size);
                shot_in_marks = false;
                for (int[] mark : player_field.marks){
                    if (mark[0] == computer_shot[0] && mark[1] == computer_shot[1]){
                        shot_in_marks = true;
                    }
                }
            }
            while (shot_in_marks);

            for (Ship ship : player_ships){
                for (int[] pos : ship.ship_coordinates){
                    if (computer_shot[0] == pos[0] && computer_shot[1] == pos[1]){
                        ship.safe_coordinates.remove(pos);
                        if (ship.safe_coordinates.size() == 0){
                            System.out.println("Your " + ship.name + " has been sunk.");
                            sleep(1000);
                            ship.sunk = true;
                        }
                    }
                }
            }

            player_field.marks.add(computer_shot);
            player_field.print();
        }

        scanner.close();
    }
}