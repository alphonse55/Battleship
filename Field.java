public class Field {
    public boolean[][] field;

    public Field(boolean[][] field){
        this.field = field;
        // Ship[] ships = new Ship[num_ships];
        // for (byte i = 0; i < num_ships; i++){
        //     ships[i] = new Ship(field, ship_lengths[i]);
        // }
    }
    public void print(){
        for (byte i = 0; i < this.field.length; i++){
            System.out.print(" --");
        }
        System.out.println();
        for (byte i=0; i<field.length; i++){
            System.out.print("|");
            for(byte j=0; j<field.length; j++){
                if (field[j][i])
                    System.out.print("██");
                else
                    System.out.print("  ");
                if (j!= field.length-1)
                    System.out.print(" ");
            }
            System.out.println("|");
        }
        for (byte i=0; i<field.length; i++)
            System.out.print(" --");
        System.out.println();
    }
}