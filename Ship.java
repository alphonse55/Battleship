public class Ship {
    public Ship(boolean[][] mat, int len_ship){
        byte x;
        byte y;
        boolean skip;
        boolean ship = false;
        byte orientation;
        while (!ship){
            orientation = (byte) (Math.random()+ 0.5); //0 o 1; 0 - x, 1 - y
            x=-1;
            y=-1;
            skip = false;
            if (orientation == 0){
                while (x<0 || x>mat.length-len_ship-2)
                    x = (byte) (Math.random()*mat.length);
                while (y<0 || y>mat.length-1)
                    y = (byte) (Math.random()*mat.length);
                for (byte i = x;i < x+len_ship; i++)
                    if (ship_around(i, y, mat)){
                        skip = true;
                        break;
                    }
                if (!skip){
                    for (byte i = x;i < x+len_ship; i++)
                        mat[y][i] = true;
                    ship = true;
                }
            }
            else if (orientation == 1){
                while (y<0 || y>mat.length-len_ship-2)
                    y = (byte) (Math.random()*mat.length);
                while (x<0 || x>mat.length-1)
                    x = (byte) (Math.random()*mat.length);
                for (byte i = y;i < y+len_ship; i++)
                    if (ship_around(x, i, mat)){
                        skip = true;
                        break;
                    }
                if (!skip){
                    for (byte i = y;i < y+len_ship; i++)
                        mat[i][x] = true;  
                    ship = true;
                }
            }
            
        }
    }
    public static boolean ship_around(byte x, byte y, boolean[][] mat){
        if (y != 0)
            if (mat[y-1][x])
                return true;
        if (y != mat.length-1)
            if (mat[y+1][x])
                return true;
        if (x != 0)
            if (mat[y][x-1])
                return true;
        if (x != mat.length-1)
            if (mat[y][x+1])
                return true;
        if (mat[y][x])
            return true;
        return false;
    }
}