public class Field {
    public boolean[][] field;

    public Field(boolean[][] field){
        this.field = field;
    }

    public void print(){
        // letters
        System.out.print("   ");
        for (int x = 0; x < this.field.length; x++){
            System.out.print(" " + "ABCDEFGHIJ".charAt(x) + " ");
        }
        System.out.println();
        // top line
        System.out.print("   ");
        for (int x = 0; x < this.field.length; x++){
            System.out.print(" --");
        }
        System.out.println();
        // grid
        for (int y = 0; y < field.length; y++){
            // numbers
            if (y < 9)
                System.out.print(" ");
            System.out.print((y + 1) + " |");
            // boats
            for(int x = 0; x < field.length; x++){
                if (field[y][x])
                    System.out.print("██");
                else
                    System.out.print("  ");
                if (x != field.length - 1)
                    System.out.print(" ");
            }
            System.out.println("|");
        }
        // bottom line
        System.out.print("   ");
        for (int x = 0; x < field.length; x++)
            System.out.print(" --");
        System.out.println();
    }
}