public class Ship {

    int x;
    int y;
    int orientation;
    String name;
    int[][] ship_positions;

    public Ship(boolean[][] mat, int len_ship, int x, int y, int orientation, String name){
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.name = name;

        ship_positions = new int[len_ship][2];

        if (orientation == 0){
            for (int i = x; i < x + len_ship; i++){
                mat[y][i] = true;
                int[] position = {i, y};
                ship_positions[i - x] = position;
            }
        }
        else if (orientation == 1){
            for (int i = y; i < y + len_ship; i++){
                mat[i][x] = true;  
                int[] position = {x, i};
                ship_positions[i - y] = position;
            }
        }
    }
}