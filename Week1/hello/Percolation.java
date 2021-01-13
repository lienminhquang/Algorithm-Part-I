import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(String[] args) {
        Percolation per = new Percolation(10);

        while (!per.percolates()) {
            per.open(StdRandom.uniform(1, 11), StdRandom.uniform(1, 11));
            per.Print();
            System.out.println(
                    ANSI_RED_BACKGROUND + "Number of open sites: " + per.numberOfOpenSites());
            System.out.println(ANSI_BLACK_BACKGROUND);
            String a = per.percolates() ? "Percolated" : "Not Percolated";
            System.out.println(a);
            System.out.println(ANSI_BLACK_BACKGROUND);
        }
    }

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        m_UF = new WeightedQuickUnionUF(n * n + 2); // + 2 for 2 fake top and bot
        m_sites = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            m_sites[i] = 0; //close
        }
        m_size = n;
        m_numOpenSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!(row >= 1 && row <= m_size && col >= 1 && row <= m_size))
            throw new IllegalArgumentException();

        if (m_sites[toID(row, col)] == 1) {
            return;
        }
        m_sites[toID(row, col)] = 1;
        m_numOpenSites++;
        if (row == 1) {
            // connect to fake top
            m_UF.union(toID(row, col), m_size * m_size);
        }
        else if (row == m_size) {
            // connect to fake bot
            m_UF.union(toID(row, col), m_size * m_size + 1);
        }

        // connect to left top right bot
        if (row > 1) {
            if (m_sites[toID(row - 1, col)] == 1) {
                m_UF.union(toID(row, col), toID(row - 1, col));
            }
        }
        if (col > 1) {
            if (m_sites[toID(row, col - 1)] == 1) {
                m_UF.union(toID(row, col), toID(row, col - 1));
            }
        }
        if (row < m_size - 1) {
            if (m_sites[toID(row + 1, col)] == 1) {
                m_UF.union(toID(row, col), toID(row + 1, col));
            }
        }
        if (col < m_size - 1) {
            if (m_sites[toID(row, col + 1)] == 1) {
                m_UF.union(toID(row, col), toID(row, col + 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!(row >= 1 && row <= m_size && col >= 1 && row <= m_size))
            throw new IllegalArgumentException();

        return m_sites[toID(row, col + 1)] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!(row >= 1 && row <= m_size && col >= 1 && row <= m_size))
            throw new IllegalArgumentException();

        if (row > 1) {
            if (!isOpen(row - 1, col)) {
                return false;
            }
        }
        if (col > 1) {
            if (!isOpen(row, col - 1)) {
                return false;
            }
        }
        if (row < m_size - 1) {
            if (!isOpen(row + 1, col)) {
                return false;
            }
        }
        if (col < m_size - 1) {
            if (!isOpen(row, col + 1)) {
                return false;
            }
        }
        return true;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return m_numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return m_UF.find(m_size * m_size) == m_UF.find(m_size * m_size + 1);
    }

    private int toID(int row, int col) {
        return ((row - 1) * m_size) + col - 1;
    }

    private void Print() {
        for (int i = 0; i < m_size; i++) {
            for (int j = 0; j < m_size; j++) {
                if (m_sites[toID(i + 1, j + 1)] == 0) {
                    System.out.print(ANSI_WHITE_BACKGROUND + "0 ");
                    System.out.print(ANSI_BLACK_BACKGROUND);
                }
                else {
                    System.out.print(ANSI_BLUE_BACKGROUND + "1 ");
                    System.out.print(ANSI_BLACK_BACKGROUND);
                }
            }
            System.out.println();
        }
    }

    private int[] m_sites;
    private int m_numOpenSites = 0;
    private int m_size;
    private WeightedQuickUnionUF m_UF;
}
