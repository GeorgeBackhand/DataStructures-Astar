import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Astar {

    boolean reachedMatch = false; //used for the match method (to see if we reach the solution)
    int[][] checkAns;
    int option1 = 9;
    int option2 = 16;
    //if size*size = 9 or 16 we go to a different checkAsn to check the answer
    Node newRoot;
    Node temp;
    int checkAnswer = 0;

    public Astar(int size, int[][] puzzleData, int row, int column) {

        //will let us know the size of puzzle to know the correct solution
        if(size * size == option1){
            checkAns = new int[][]{{1, 2, 3,}, {4, 5, 6}, {7, 8, 0}};
        }
        if(size * size == option2){
            checkAns = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
        }
        System.out.print("A*(Completed): ");
        System.out.println(" ");
        System.out.print("Initial Puzzle: ");
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.print(puzzleData[i][j] + " ");
            }
        }
        newRoot = new Node(puzzleData, row, column, - 1, -1, 0,0,0, null," ", null, null, null, null);
        Astar(newRoot, size, puzzleData); //perform Astar having a open list and close list
        temp = newRoot;
        printPath();
    }

    private void Astar(Node root, int size, int[][] puzzleData) { //perform bread first search

        PriorityQueue<Node> queue = new PriorityQueue<>();

        if (root == null) {
            System.out.println("Empty Tree");
            return;
        }
        queue.add(root); //add the root node into the queue
        while (!queue.isEmpty() && reachedMatch == false) {
            LinkedList<Node> List = new LinkedList<>();
            newRoot = queue.remove();
            childrenNodes(newRoot, size);
            if (newRoot.up != null) {
                queue.add(newRoot.up);
            }
            if (newRoot.right != null) {
                queue.add(newRoot.right);
            }
            if (newRoot.down != null) {
                queue.add(newRoot.down);
            }
            if (newRoot.left != null) {
                queue.add(newRoot.left);
            }
            match(newRoot.mainList, size);
        }
    }

    private int generateManhattan(Node root, int size){ //generates the manhattan distance

        int manhattanDistanceTotal = 0;
        int goalX;
        int goalY;
        int directionX;
        int directionY;

        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                int currentVal = root.mainList[i][j]; //current node value we are on
                if(currentVal != 0){ //we dont calculate the 0 value
                    goalX = (currentVal - 1) / size; //goal row
                    goalY = (currentVal - 1) % size; //goal column
                    directionX = i - goalX; // X distance to the goal spot
                    directionY = j - goalY; // Y distance to the goal spot
                    manhattanDistanceTotal += Math.abs(directionX) + Math.abs(directionY);
                }
            }
        }
        return manhattanDistanceTotal;
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
            System.out.println(" ");
            reachedMatch = true;
        }
        checkAnswer = 0;
    }

    private void printPath(){
        LinkedList<String> direction = new LinkedList<>();
        while(temp.parent != null){ //if there exists a parent add the string(direction) to the linked list
            direction.add(temp.pointer);
            temp = temp.parent;
        }
        System.out.print("Path: ");
        for(int i = direction.size(); i > 0 ; i--){
            System.out.print(direction.get(i-1) + " ");
        }
        //print out depthLvl
        System.out.println(" ");
        System.out.println("DepthLevel: " + newRoot.depthLvl);
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
            int h = generateManhattan(newRoot, size);
            int f = root.depthLvl + h;
            root.up = new Node(clonedPuzzle, nRow, root.curColumn, root.curRow, root.curColumn, newRoot.depthLvl + 1, h ,f , root, "U", null, null, null, null);
        }
    }
    private void checkRight(Node root, int size){

        int nColumn;
        int hold;

        int[][] clonedPuzzle = cloning(root.mainList, size);

        if (root.curColumn + 1 != root.prevColumn && root.curColumn != size-1) {
            nColumn = root.curColumn + 1;
            hold = clonedPuzzle[root.curRow][nColumn];
            //swaps
            clonedPuzzle[root.curRow][nColumn] = 0;
            clonedPuzzle[root.curRow][root.curColumn] = hold;
            int h = generateManhattan(newRoot, size);
            int f = root.depthLvl + h;
            root.right = new Node(clonedPuzzle, root.curRow, nColumn, root.curRow, root.curColumn, newRoot.depthLvl + 1, h,f, root, "R",null, null, null, null);
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
            int h = generateManhattan(newRoot, size);
            int f = root.depthLvl + h;
            root.left = new Node(clonedPuzzle, root.curRow, nColumn, root.curRow, root.curColumn, newRoot.depthLvl + 1,h,f, root, "L",null, null, null, null);
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
            int h = generateManhattan(newRoot, size);
            int f = root.depthLvl + h;
            root.down = new Node(clonedPuzzle, nRow, root.curColumn, root.curRow, root.curColumn, newRoot.depthLvl + 1, h,f, root, "D", null, null, null, null);
        }
    }
}
