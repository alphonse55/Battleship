import java.util.Scanner;

public class Battle { //extends Application{
    public static void sleep(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException e){e.printStackTrace();}
    }

    public static void clear_console(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int[] ship_lengths = {2, 2, 3, 3, 4, 5};
        int num_ships = ship_lengths.length;

        boolean[][] field = new boolean[10][10];
        Ship[] ships = new Ship[num_ships];
        for (byte i = 0; i < num_ships; i++){
            ships[i] = new Ship(field, ship_lengths[i]);
        }
        Field computer_field = new Field(field);
        computer_field.print();

        // player field : 
        // boat 1 (length 2) : - vertical ? - starting point ?, if too low or too far right, ask again

        Field player_field = new Field(field);
        player_field.print();
    }
}