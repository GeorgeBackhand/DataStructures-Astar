import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MainClass {

    Scanner read;

    int size; //size of the puzzle;
    String readVal; //use to read each item in the text file
    int newVal; //convert the string(we read) to int
    int[][] puzzleData; //store the data we read from the text file

    int row; //tell us the row 'x' is currently at
    int column; //tell us the column 'x' is currently at


    MainClass() throws IOException {
        File file = new File("src/Maze.txt");
        read = new Scanner(file);

        readPuzzle(); //store everything in a 2D array
        try {
            BFS first = new BFS(size, puzzleData, row, column); //call BFS class to perform BFS
        }
        catch (OutOfMemoryError e){
        }
        //NOTE: IF BFS DOESNT WORK DUE TO OUT OF MEMORY IT WILL NOT MAKE IT TO THE IDDFS
        try {
            IterativeDeepeningDFS second = new IterativeDeepeningDFS(size, puzzleData, row, column); //call IterativeDeepening DFS
            throw new IOException();
        }
        catch (Exception e){
        }
        System.out.println(" ");
        Astar third = new Astar(size, puzzleData, row, column); //call Astar
    }

    public void readPuzzle() {

        size = read.nextInt(); //size of puzzle
        puzzleData = new  int[size][size];

        for(int i = 0; i < size; i++){
            for (int j = 0; j < size ; j++) {
                readVal = read.next(); //read each element in the text file

                if(!readVal.equals("x")){
                    newVal = Integer.parseInt(readVal); //string is converted to an int
                }
                if(readVal.equals("x")){
                    newVal = 0; //set the string x to int 0
                    //get the co-ordinates for where x is located
                    row = i;
                    column = j;
                }
                puzzleData[i][j] = newVal; // store the int value into the 2D array
            }
        }
    }


    public static void main(String[] args) throws IOException {
        MainClass t = new MainClass();
    }

}
