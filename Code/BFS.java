import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS {

    boolean reachedMatch = false; //used for the match method (to see if we reach the solution)
    int[][] checkAns;
    int option1 = 9;
    int option2 = 16;
    int tempDepth = 0;
    //if size*size = 9 or 16 we go to a different checkAsn to check the answer
    Node newRoot;
    Node temp;
    int checkAnswer = 0;
    int depthLvl = 0;

    public BFS(int size, int[][] puzzleData, int row, int column) throws IOException {

        //will let us know the size of puzzle to know the correct solution
        if(size * size == option1){
            checkAns = new int[][]{{1, 2, 3,}, {4, 5, 6}, {7, 8, 0}};
        }
        if(size * size == option2){
            checkAns = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
        }
        System.out.print("Initial Puzzle: ");
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.print(puzzleData[i][j] + " ");
            }
        }
            newRoot = new Node(puzzleData, row, column, -1, -1, 0, 0, 0, null, " ", null, null, null, null);
            breadFirst(newRoot, size);
            temp = newRoot;
            printPath();
    }

    private void printPath(){
        LinkedList<String> direction = new LinkedList<>();
        System.out.println(" ");
        while(temp.parent != null){ //if there exists a parent add the string(direction) to the linked list
            direction.add(temp.pointer);
            temp = temp.parent;
        }
        System.out.print("BFS Path: ");
        for(int i = direction.size(); i > 0 ; i--){
            System.out.print(direction.get(i-1) + " ");
            temp.depthLvl++; //depth level increases once each direction
        }
        //print out depthLvl
        System.out.println(" ");
        System.out.println("BFS DepthLevel: " + temp.depthLvl);
    }


    private void breadFirst(Node root, int size) { //perform bread first search

        Queue<Node> queue = new LinkedList<>();

        if (root == null) {
            System.out.println("Empty Tree");
            return;
        }
        queue.add(root); //add the root node into the queue
            while (!queue.isEmpty() && reachedMatch == false) {

                newRoot = queue.remove();
                childrenNodes(newRoot, size);
                if (newRoot.up != null) {
                    queue.offer((newRoot.up));
                }
                if (newRoot.right != null) {
                    queue.offer((newRoot.right));
                }
                if (newRoot.down != null) {
                    queue.offer((newRoot.down));
                }
                if (newRoot.left != null) {
                    queue.offer((newRoot.left));
                }
                match(newRoot.mainList, size);
            }
    }

    private void match(int[][] mainList, int size) {
        //traverse through the 2D array to then check if current solution matches correct solution
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mainList[i][j] == checkAns[i][j]) {
                    checkAnswer++;
                }
            }
        }
        if(checkAnswer == size*size){
            System.out.println(" ");
            System.out.print("Solved Puzzle: ");
            for (int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    System.out.print(mainList[i][j] + " ");
                }
            }
            reachedMatch = true;
        }
        checkAnswer = 0;
    }

    private int[][] cloning(int[][] clonePuzzle, int size) {

        int[][] tempArray = new int[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                tempArray[i][j] = clonePuzzle[i][j];
            }
        }
        return tempArray;
    }

        private void childrenNodes(Node root, int size){ //wrapper class
        //call each method to see if a child exists in each of the 4 directions of the root
                checkUp(root, size);
                checkRight(root, size);
                checkDown(root, size);
                checkLeft(root, size);
        }

        private void checkUp(Node root, int size) {

            int nRow;
            int hold;

            int[][] clonedPuzzle = cloning(root.mainList, size);

            if (root.curRow != 0 && root.curRow - 1 != root.prevRow) {
                nRow = root.curRow - 1; //this will give us the value for the new row
                hold = clonedPuzzle[nRow][root.curColumn]; //put the value of the above value into a temporary to hold
                //swaps
                clonedPuzzle[nRow][root.curColumn] = 0;
                clonedPuzzle[root.curRow][root.curColumn] = hold;
                root.up = new Node(clonedPuzzle, nRow, root.curColumn, root.curRow, root.curColumn, 0,0,0, root, "U", null, null, null, null);
            }
        }
        private void checkRight(Node root, int size) {

            int nColumn;
            int hold;

            int[][] clonedPuzzle = cloning(root.mainList, size);

            if (root.curColumn + 1 != root.prevColumn && root.curColumn != size - 1) {
                nColumn = root.curColumn + 1;
                hold = clonedPuzzle[root.curRow][nColumn];
                //swaps
                clonedPuzzle[root.curRow][nColumn] = 0;
                clonedPuzzle[root.curRow][root.curColumn] = hold;
                root.right = new Node(clonedPuzzle, root.curRow, nColumn, root.curRow, root.curColumn, 0, 0, 0, root, "R", null, null, null, null);

            }
        }

        private void checkLeft(Node root, int size) {

            int nColumn;
            int hold;

            int[][] clonedPuzzle = cloning(root.mainList, size);

            if (root.curColumn - 1 != root.prevColumn && root.curColumn != 0) {
                nColumn = root.curColumn - 1;
                hold = clonedPuzzle[root.curRow][nColumn];
                //swaps
                clonedPuzzle[root.curRow][nColumn] = 0;
                clonedPuzzle[root.curRow][root.curColumn] = hold;
                root.left = new Node(clonedPuzzle, root.curRow, nColumn, root.curRow, root.curColumn, 0,0,0, root, "L",null, null, null, null);
            }
        }

        private void checkDown(Node root, int size) {

            int nRow;
            int hold;

            int[][] clonedPuzzle = cloning(root.mainList, size);

            if (root.curRow != size-1 && root.curRow + 1 != root.prevRow) {
                nRow = root.curRow + 1; //this will give us the value for the new row
                hold = clonedPuzzle[nRow][root.curColumn]; //put the value of the above value into a temporary to hold
                //swaps
                clonedPuzzle[nRow][root.curColumn] = 0;
                clonedPuzzle[root.curRow][root.curColumn] = hold;
                root.down = new Node(clonedPuzzle, nRow, root.curColumn, root.curRow, root.curColumn, 0,0,0, root, "D", null, null, null, null);
            }
        }
}
