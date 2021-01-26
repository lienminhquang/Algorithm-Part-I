import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] _tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        _tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                _tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of thlengthrd
    public String toString() {
        String str = "";
        for (int i = 0; i < _tiles.length; i++) {
            for (int j = 0; j < _tiles[i].length; j++) {
                str += _tiles[i][j] + " ";
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
        int count = 0;
        int value = 1;
        for (int i = 0; i < _tiles.length; i++) {
            for (int j = 0; j < _tiles[i].length; j++) {
                if(_tiles[i][j] == value++)
                {
                    count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] titles = new int[10][10];
        Board board = new Board(titles);
        StdOut.print(board);
    }

}
