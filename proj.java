package Pack;
import java.util.*;

interface Placeable{
    default boolean canPlace(int y,int x,int n, int[][] arr){
        // Check row
        for (int i = 0; i < 9; i++) {
            if (arr[y][i] == n) {
                return false;
            }
        }
        // Check column
        for (int i = 0; i < 9; i++) {
            if (arr[i][x] == n) {
                return false;
            }
        }
        // Check same 3*3 square
        int x0 = (x / 3) * 3;
        int y0 = (y / 3) * 3;

        for (int i = y0; i < y0 + 3; i++) {
            for (int j = x0; j < x0 + 3; j++) {
                if (arr[i][j] == n) {
                    return false;
                }
            }
        }

        return true;
    }
}

class Generator implements Placeable{
    int[][] arr = new int[9][9];


    public  int[][] generate() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int a=(int)Math.floor(Math.random()*10);
                if(a==0){
                    arr[i][j]=0;
                }
                else{
                if(Placeable.super.canPlace(i, j, a, arr)){
                    arr[i][j]=a;
                }
            }
            }  
        }
        return arr;
    }
}

class Solve implements Placeable{
    private int[][] arr = new int[9][9];
    int numOfSolutions = 1;

    private boolean isEmpty(int y, int x) {
        if (arr[y][x] == 0) {
            return true;
        }
        return false;
    }

    public void setArr(int[][] arr) {
        this.arr = arr;
    }

    public void solve() {
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(isEmpty(i, j)){
                    for(int n = 1; n <= 9; n++){
                        if(Placeable.super.canPlace(i, j, n, arr)){
                            arr[i][j] = n;
                            solve();
                            arr[i][j] = 0;
                        }    
                    }
                    return;
                }
            }
        }

        
        display();
        numOfSolutions++;

    }

    public void display(){
        if(numOfSolutions > 1){
            System.out.println("Solution " + numOfSolutions + ":");
        }
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
        
    }
}

class Driver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println(
                "Do you want a random sudoku or a custom sudoku?\nPress 'r' for a random sudoku and 'c' for a custom input.");

        String randomOrCustom;
        Solve sl = new Solve();

        while (true) {
            randomOrCustom = in.nextLine();
            if (randomOrCustom.equals("c")) {
                // Enter code here
                int[][] arr = new int[9][9];

                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        int row = in.nextInt();
                        int col = in.nextInt();
                        int num = in.nextInt();

                        arr[row][col] = num;
                    }
                }

                System.out.println("-----------------------------------------------------------------------");

                sl.setArr(arr);
                sl.solve();

                break;
            } else if (randomOrCustom.equals("r")) {
                // Enter code here
                sl.setArr((new Generator()).generate());
                System.out.print("Here is the Random Sudoku Generated\n\n");
                sl.display();
                sl.solve();


                break;
            } else {
                System.out.println("Enter a valid input.");
            }

        }

        in.close();
    }
}