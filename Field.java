import java.util.*;

public class Field {
    public boolean[][] field;
    public LinkedList<int[]> marks = new LinkedList<>();
    // colors
    public static String RESET = "\u001B[0m";
    public static String RED = "\u001B[31m";
    public static String GREEN = "\u001B[32m";
    public static String BLUE = "\u001B[34m";

    public Field(boolean[][] field){
        this.field = field;
    }

    public void print(){
        // letters
        System.out.print("   ");
        for (int x = 0; x < field.length; x++){
            System.out.print(" " + "ABCDEFGHIJ".charAt(x) + " ");
        }
        System.out.println();

        // top line
        System.out.print("   ");
        for (int x = 0; x < field.length; x++){
            System.out.print(" --");
        }
        System.out.println();

        // grid
        for (int y = 0; y < field.length; y++){
            // numbers
            if (y < 9){
                System.out.print(" ");
            }
            System.out.print((y + 1) + " |");

            // boats
            for(int x = 0; x < field.length; x++){
                if (field[y][x]){
                    boolean printed = false;
                    for (int[] mark : marks){
                        if (mark[0] == x && mark[1] == y){
                            System.out.print(RED + "██" + RESET);
                            printed = true;
                            break;
                        }
                    }
                    if (!printed){
                        System.out.print("██");
                    }
                }
                else{
                    boolean printed = false;
                    for (int[] mark : marks){
                        if (mark[0] == x && mark[1] == y){
                            System.out.print(BLUE + "██" + RESET);
                            printed = true;
                            break;
                        }
                    }
                    if (!printed){
                        System.out.print("  ");
                    }
                }
                
                if (x != field.length - 1){
                    System.out.print(" ");
                }
            }
            System.out.println("|");
        }

        // bottom line
        System.out.print("   ");
        for (int x = 0; x < field.length; x++)
            System.out.print(" --");
        System.out.println();
    }

    public void print_marks(){
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
            if (y < 9){
                System.out.print(" ");
            }
            System.out.print((y + 1) + " |");

            // boats
            for(int x = 0; x < field.length; x++){
                boolean marked = false;
                for (int[] mark : marks){
                    if (x == mark[0] && y == mark[1]){
                        marked = true;
                    }
                }

                if (marked){
                    if (field[y][x]){
                        System.out.print(RED + "██" + RESET);
                    }
                    else{
                        System.out.print("██");
                    }
                }
                else{
                    System.out.print("  ");
                }

                if (x != field.length - 1){
                    System.out.print(" ");
                }
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