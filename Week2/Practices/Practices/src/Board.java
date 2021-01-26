import java.util.LinkedList;
import java.util.Random;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] _tiles;
    private int _emptyi = 0;
    private int _emptyj = 0;
    private int _hamming = -1;
    private int _manhattan = -1;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        _tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                _tiles[i][j] = tiles[i][j] - 1;
                if (tiles[i][j] == 0) {
                    _tiles[i][j] = _tiles.length * _tiles.length - 1; // empty on the bottom right so convert empty to
                                                                      // this for convenion
                    _emptyi = i;
                    _emptyj = j;
                }
            }
        }
    }

    // string representation of thlengthrd
    public String toString() {
        String str = _tiles.length + "\n";
        for (int i = 0; i < _tiles.length; i++) {
            for (int j = 0; j < _tiles[i].length; j++) {
                if (_tiles[i][j] == _tiles.length * _tiles.length - 1) {
                    str += "0 ";
                } else {
                    str += (_tiles[i][j] + 1) + " ";
                }
            }
            str += "\n";
        }
        return str;
    }

    // board dimension n
    public int dimension() {
        return _tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        if (_hamming == -1) {
            int count = 0;
            for (int i = 0; i < _tiles.length; i++) {
                for (int j = 0; j < _tiles[i].length; j++) {
                    if (_tiles[i][j] != _tiles.length * _tiles.length - 1) { // if is not empty
                        if (_tiles[i][j] != i * _tiles.length + j) {
                            count++;
                        }
                    }
                }
            }
            _hamming = count;
        }
        return _hamming;
    }

    private int Abs(int x) {
        return x > 0 ? x : -x;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (_manhattan == -1) {
            int distance = 0;
            for (int i = 0; i < _tiles.length; i++) {
                for (int j = 0; j < _tiles.length; j++) {
                    if (_tiles[i][j] == _tiles.length * _tiles.length - 1) {
                        continue;
                    }
                    int currentValue = _tiles[i][j];
                    int goalPositionJOfCurrentValue = currentValue % _tiles.length;
                    int goalPositionIOfCurrentValue = currentValue / _tiles.length;
                    distance += Abs(goalPositionJOfCurrentValue - j) + Abs(goalPositionIOfCurrentValue - i);
                    // StdOut.print((currentValue + 1) + ":" + (Abs(goalPositionJOfCurrentValue - j)
                    // + Abs(goalPositionIOfCurrentValue - i)) + " + ");
                }
            }
            _manhattan = distance;
        }
        return _manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        var a = (Board) y;
        if (a.dimension() != dimension()) {
            return false;
        }
        for (int i = 0; i < _tiles.length; i++) {
            for (int j = 0; j < _tiles.length; j++) {
                if (a._tiles[i][j] != _tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void swap(int[][] tile, int i1, int j1, int i2, int j2) {
        int tmp = tile[i1][j1];
        tile[i1][j1] = tile[i2][j2];
        tile[i2][j2] = tmp;
    }

    private int[][] cloneTile() {
        int[][] a = new int[_tiles.length][_tiles.length];
        for (int i = 0; i < _tiles.length; i++) {
            for (int j = 0; j < _tiles[i].length; j++) {
                if (_tiles[i][j] == _tiles.length * _tiles.length - 1) {
                    a[i][j] = 0;
                } else {
                    a[i][j] = _tiles[i][j] + 1;
                }
            }
        }
        return a;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        LinkedList<Board> rs = new LinkedList<>();
        if (_emptyi - 1 >= 0) {
            int[][] tmp = cloneTile();
            swap(tmp, _emptyi - 1, _emptyj, _emptyi, _emptyj);
            rs.add(new Board(tmp));
        }
        if (_emptyi + 1 < _tiles.length) {
            int[][] tmp = cloneTile();
            swap(tmp, _emptyi + 1, _emptyj, _emptyi, _emptyj);
            rs.add(new Board(tmp));
        }
        if (_emptyj - 1 >= 0) {
            int[][] tmp = cloneTile();
            swap(tmp, _emptyi, _emptyj - 1, _emptyi, _emptyj);
            rs.add(new Board(tmp));
        }
        if (_emptyj + 1 < _tiles.length) {
            int[][] tmp = cloneTile();
            swap(tmp, _emptyi, _emptyj, _emptyi, _emptyj + 1);
            rs.add(new Board(tmp));
        }

        return rs;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Random rd = new Random();
        int[][] tmp = cloneTile();
        int i1 = rd.nextInt(_tiles.length), j1 = rd.nextInt(_tiles.length), i2 = rd.nextInt(_tiles.length), j2 = rd.nextInt(_tiles.length);
        while(i1 == _emptyi && j1 == _emptyj)
        {
            i1 = rd.nextInt(_tiles.length);
            j1 = rd.nextInt(_tiles.length);
        }
        while((i2 == _emptyi && j2 == _emptyj) || (i2 == i1 && j2 == j1))
        {
            i2 = rd.nextInt(_tiles.length);
            j2 = rd.nextInt(_tiles.length);
        }
        swap(tmp, i1, j1, i2, j2);
        return new Board(tmp);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println(initial);
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        for (Board is : initial.neighbors()) {
            StdOut.println(is);
        }

        // solve the puzzle
        // Solver solver = new Solver(initial);

        // print solution to standard output
        // if (!solver.isSolvable())
        // StdOut.println("No solution possible");
        // else {
        // StdOut.println("Minimum number of moves = " + solver.moves());
        // for (Board board : solver.solution())
        // StdOut.println(board);
        // }
    }

}
