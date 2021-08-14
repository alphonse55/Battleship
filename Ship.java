public class Ship {

    int x;
    int y;
    int orientation;
    String name;

    public Ship(boolean[][] mat, int len_ship, int x, int y, int orientation, String name){
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.name = name;

        if (orientation == 0){
            for (int i = x; i < x + len_ship; i++)
                mat[y][i] = true;
        }
        else if (orientation == 1){
            for (int i = y; i < y + len_ship; i++)
                mat[i][x] = true;  
        }
    }
}