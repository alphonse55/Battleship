import java.util.Scanner;

public class Battle {
    public static void print_field(boolean[][] matrix){
        for (byte i = 0; i < matrix.length; i++){
            System.out.print(" --");
        }
        System.out.println();
        for (byte i=0; i<matrix.length; i++){
            System.out.print("|");
            for(byte j=0; j<matrix.length; j++){
                if (matrix[j][i])
                    System.out.print("██");
                else
                    System.out.print("  ");
                if (j!= matrix.length-1)
                    System.out.print(" ");
            }
            System.out.println("|");
        }
        for (byte i=0; i<matrix.length; i++)
            System.out.print(" --");
        System.out.println();
    }
    public static void main(String[] a) {
        Scanner input = new Scanner(System.in);
        System.out.print("Grid size: ");
        int len = input.nextInt();
        boolean[][] field = new boolean[len][len];
        for (byte i = 0; i<len; i++)
            for (byte j = 0; j<len; j++)
                field[j][i] = false;
        int num_ships = (int) (len/2);
        for (byte i = 0; i<num_ships; i++){
            int num = (int) (Math.random()*5+1);
            Ship n = new Ship(field, num);
        }
        print_field(field);
    }
}