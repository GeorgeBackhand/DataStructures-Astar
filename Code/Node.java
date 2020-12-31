import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Node implements Comparable <Node> {

    int[][] mainList; //store the data
    int curRow; //stores the row where the root(x) is
    int curColumn; //stores the column where the root(x) is
    int prevRow; //stores where the previous row of x was located
    int prevColumn; //stores where the previous column of x was located
    int depthLvl;
    int h; //A* current node value (heuristic)
    int f; //A* g + h
    //4 possible pointers
    Node parent;
    String pointer;
    Node up;
    Node right;
    Node down;
    Node left;


    public Node(int[][] mainList, int curRow, int curColumn, int prevRow, int prevColumn, int depthLvl, int h, int f,  Node parent, String pointer, Node up, Node right, Node down, Node left) {

        this.mainList = mainList;
        this.curRow = curRow;
        this.curColumn = curColumn;
        this.prevRow = prevRow;
        this.prevColumn = prevColumn;
        this.depthLvl = depthLvl;
        this.h = h;
        this.f = (depthLvl + h);
        this.parent = parent;
        this.pointer = pointer;
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
    }

    @Override
    public int compareTo(Node A){
        if(this.f > A.f){
            return 1;
        }
        if(this.f < A.f){
            return -1;
        }
        else{
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        MainClass t = new MainClass();
    }

}

