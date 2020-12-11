class Solution
{
    public static void main(String[] args)
    {
        int[][] grid = {
                {3, 0, 6, 5, 0, 8, 4, 0, 0},
                {5, 2, 0, 0, 0, 0, 0, 0, 0},
                {0, 8, 7, 0, 0, 0, 0, 3, 1},
                {0, 0, 3, 0, 1, 0, 0, 8, 0},
                {9, 0, 0, 8, 6, 3, 0, 0, 5},
                {0, 5, 0, 0, 9, 0, 6, 0, 0},
                {1, 3, 0, 0, 0, 0, 2, 5, 0},
                {0, 0, 0, 0, 0, 0, 0, 7, 4},
                {0, 0, 5, 2, 0, 6, 3, 0, 0}};

        if (solveSudoku(grid))
        {
            System.out.println("The solved sudoku is:");
            for (int i = 0; i < 9; i++)
            {
                for (int j = 0; j < 9; j++)
                    System.out.print(grid[i][j] + " ");
                System.out.println("");
            }
        }
        else
            System.out.println("No solution exists");
    }

    private static boolean solveSudoku(int[][] grid)
    {
        int[] rowBits = new int[9];
        int[] colBits = new int[9];
        int[] subMatrixBits = new int[9];

        // get 3x3 submatrix, row and column digits
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (grid[i][j] > 0)
                {
                    int value = 1 << (grid[i][j] - 1);

                    rowBits[i] |= value;
                    colBits[j] |= value;
                    subMatrixBits[3 * (i / 3) + j / 3] |= value;
                }

        return solve(0, 0, rowBits, colBits, subMatrixBits, grid);
    }

    private static boolean solve(int r, int c, int[] rowBits, int[] colBits, int[] subMatrixBits, int[][] grid)
    {
        if (r == 9)
            return true;
        if (c == 9)
            return solve(r + 1, 0, rowBits, colBits, subMatrixBits, grid);

        if (grid[r][c] == 0)
        {
            for (int i = 1; i <= 9; i++)
            {
                int digitBit = 1 << (i - 1);

                if ((rowBits[r] & digitBit) == 0 &&
                        (colBits[c] & digitBit) == 0 &&
                        (subMatrixBits[3 * (r / 3) + c / 3] & digitBit) == 0)
                {
                    System.out.printf("Setting grid[%d][%d] = %d%n", r, c, i);

                    rowBits[r] |= digitBit;
                    colBits[c] |= digitBit;
                    subMatrixBits[3 * (r / 3) + c / 3] |= digitBit;

                    grid[r][c] = i;


                    if (solve(r, c + 1, rowBits, colBits, subMatrixBits, grid))
                        return true;
                    else
                    {
                        rowBits[r] &= ~digitBit;
                        colBits[c] &= ~digitBit;
                        subMatrixBits[3 * (r / 3) + c / 3] &= ~digitBit;

                        grid[r][c] = 0;
                        System.out.printf("Reverting grid[%d][%d] = 0%n", r, c);

                    }
                }
            }
            return false;
        }
        else
            return solve(r, c + 1, rowBits, colBits, subMatrixBits, grid);
    }
}