import java.util.*;

public class Field {
    public boolean[][] field;
    public boolean[][] marks;
    public int ships_sunk = 0;
    
    // colors
    public static String RESET = "\u001B[0m";
    public static String RED = "\u001B[31m";
    public static String GREEN = "\u001B[32m";
    public static String BLUE = "\u001B[34m";

    public Field(boolean[][] field){
        this.field = field;
        marks = new boolean[10][10];
        for (int y = 0; y < 10; y++){
            for (int x = 0; x < 10; x++){
                marks[y][x] = false;
            }
        }
    }

    public void print(){
        // letters
        System.out.print("    ");
        for (int x = 0; x < field.length; x++){
            System.out.print(" " + "ABCDEFGHIJ".charAt(x) + " ");
        }
        System.out.println();

        // top line
        System.out.print("    ");
        for (int x = 0; x < field.length; x++){
            System.out.print("---");
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
                if (marks[y][x]){
                    if (field[y][x]){
                        System.out.print(RED + "███" + RESET);
                    }
                    else{
                        System.out.print(BLUE + "███" + RESET);
                    }
                }
                else{
                    if (field[y][x]){
                        System.out.print("███");
                    }
                    else{
                        System.out.print("   ");
                    }
                }
            }
            System.out.println("|");
        }

        // bottom line
        System.out.print("    ");
        for (int x = 0; x < field.length; x++)
            System.out.print("---");
        System.out.println();
    }

    public void print_marks(){
        // letters
        System.out.print("    ");
        for (int x = 0; x < field.length; x++){
            System.out.print(" " + "ABCDEFGHIJ".charAt(x) + " ");
        }
        System.out.println();

        // top line
        System.out.print("    ");
        for (int x = 0; x < field.length; x++){
            System.out.print("---");
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
                if (marks[y][x]){
                    if (field[y][x]){
                        System.out.print(RED + "███" + RESET);
                    }
                    else{
                        System.out.print(BLUE + "███" + RESET);
                    }
                }
                else{
                    System.out.print("   ");
                }
            }
            System.out.println("|");
        }

        // bottom line
        System.out.print("    ");
        for (int x = 0; x < field.length; x++)
            System.out.print("---");
        System.out.println();
    }
}