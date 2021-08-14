public class Ship {

    int x;
    int y;
    int orientation;
    String name;
    int[][] ship_coordinates;

    public Ship(boolean[][] mat, int len_ship, int x, int y, int orientation, String name){
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.name = name;

        ship_coordinates = new int[len_ship][2];

        if (orientation == 0){
            for (int i = x; i < x + len_ship; i++){
                mat[y][i] = true;
                int[] coordinates = {i, y};
                ship_coordinates[i - x] = coordinates;
            }
        }
        else if (orientation == 1){
            for (int i = y; i < y + len_ship; i++){
                mat[i][x] = true;  
                int[] coordinates = {x, i};
                ship_coordinates[i - y] = coordinates;
            }
        }
    }
}