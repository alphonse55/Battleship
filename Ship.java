import java.util.*;

public class Ship {
    int x;
    int y;
    int orientation;
    int length;
    String name;
    int[][] ship_coordinates;
    LinkedList<int[]> safe_coordinates = new LinkedList<>();
    boolean sunk = false;

    public Ship(boolean[][] mat, int length, int x, int y, int orientation, String name){
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.length = length;
        this.name = name;

        ship_coordinates = new int[length][2];

        if (orientation == 0){
            for (int i = x; i < x + length; i++){
                mat[y][i] = true;
                int[] coordinates = {i, y};
                ship_coordinates[i - x] = coordinates;
            }
        }
        else if (orientation == 1){
            for (int i = y; i < y + length; i++){
                mat[i][x] = true;  
                int[] coordinates = {x, i};
                ship_coordinates[i - y] = coordinates;
            }
        }

        for (int i = 0; i < ship_coordinates.length; i++){
            safe_coordinates.add(ship_coordinates[i]);
        }
    }
}