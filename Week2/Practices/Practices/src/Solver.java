import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private LinkedList<Board> solution = new LinkedList<>();

    private class Node implements Comparable<Node> {
        public Node(Board board) {
            _board = board;
        }

        @Override
        public int compareTo(Solver.Node o) {
            return _board.manhattan() - o._board.manhattan();
        }

        Board _board;
        Board _parent;
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        solution.add(initial);
        
        if (initial.isGoal()) {
            return;
        }
        Board tInitial = initial.twin();
        StdOut.print(tInitial);
       
        MinPQ<Node> minPQ = new MinPQ<>();
        MinPQ<Node> tminPQ = new MinPQ<>();

        for (Board board : initial.neighbors()) {
            Node node = new Node(board);
            node._parent = initial;
            minPQ.insert(node);
        }
        for (Board board : tInitial.neighbors()) {
            Node node = new Node(board);
            node._parent = tInitial;
            tminPQ.insert(node);
        }
        while (true) {
            Node node = minPQ.delMin();
            Node tnode = tminPQ.delMin();
            //StdOut.println(node._board);

            if(tnode._board.isGoal()){
                solution = null;
                return;
            }
            solution.add(node._board);
            if (node._board.isGoal()) {
                return;
            }

            for (Board neigh : node._board.neighbors()) {
                if (!neigh.equals(node._parent)) {
                    Node newNode = new Node(neigh);
                    newNode._parent = node._board;
                    minPQ.insert(newNode);
                }
            }
            for (Board neigh : tnode._board.neighbors()) {
                if (!neigh.equals(tnode._parent)) {
                    Node newNode = new Node(neigh);
                    newNode._parent = tnode._board;
                    tminPQ.insert(newNode);
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if(solution == null){
            return -1;
        }
        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}