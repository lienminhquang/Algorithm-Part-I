//import java.util.LinkedList;

import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    // private Queue<Board> solution = new Queue<>();
    private LinkedList<Board> solution = new LinkedList<>();

    private class Node implements Comparable<Node>{
        public Node(Board board, Node parent) {
            _board = board;
            _parent = parent;
            if(parent != null) {
                _moves = parent._moves + 1;
            }
            else {
                _moves = 0;
            }
        }

        @Override
        public int compareTo(Solver.Node o) {
            int priorityDiff = (_board.manhattan() + this._moves) - (o._board.manhattan() + o._moves);
            return  priorityDiff == 0 ? _board.manhattan() - o._board.manhattan() : priorityDiff;
        }

        Board _board;
        Node _parent;
        int _moves;
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        //solution.enqueue(initial);
        
        if (initial.isGoal()) {
            solution.addFirst(initial);
            return;
        }
        Board tInitial = initial.twin();
       
        MinPQ<Node> minPQ = new MinPQ<>();
        minPQ.insert(new Node(initial, null));
        minPQ.insert(new Node(tInitial, null));
        
        while (!minPQ.min()._board.isGoal()) {
            Node node = minPQ.delMin();
            for (Board neigh : node._board.neighbors()) {
                if (node._parent == null || (node._parent != null && !neigh.equals(node._parent._board))) {
                    Node newNode = new Node(neigh, node);
                    minPQ.insert(newNode);
                }
            }
            //StdOut.println(node._board);
        }
        Node current = minPQ.min();
        while(current._parent != null){
            solution.addFirst(current._board);
            current = current._parent;
        }
        solution.addFirst(current._board);
        if(!current._board.equals(initial)) solution = null;
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