/*
    @author: Simone Nicol <en0mia.dev@gmail.com>
    @created: 07/07/22
    @copyright: Check the repository license.
*/

import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    public final static char BLUE_IDENTIFIER = 'B';
    public final static char RED_IDENTIFIER = 'R';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Read the number of test cases.
        int t = scanner.nextInt();
        for (int caseIndex = 1; caseIndex <= t; caseIndex++) {
            // Read the board size.
            int n = scanner.nextInt();
            // Read each row of the board.
            char[][] board = new char[n][];
            for (int i = 0; i < n; i++) {
                board[i] = scanner.next().toCharArray();
            }
            // Determine the game status and display it.
            String status = determineStatus(board);
            System.out.println("Case #" + caseIndex + ": " + status);
        }
    }

    /** Returns a status string as specified by the Hex problem statement. */
    static String determineStatus(char[][] board) {
        int blue = 0, red = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == BLUE_IDENTIFIER) {
                    blue ++;
                } else if (board[i][j] == RED_IDENTIFIER) {
                    red ++;
                }
            }
        }

        if (Math.abs(blue - red) > 1) {
            return "Impossible";
        }

        boolean blueHasWon = hasWon(board, BLUE_IDENTIFIER);
        boolean redHasWon = hasWon(board, RED_IDENTIFIER);

        if (blueHasWon && redHasWon) {
            return "Impossible";
        }

        // Verify the rules
        if (blueHasWon) {
            if (blue < red || !hasOnlyOneWinningPath(board, BLUE_IDENTIFIER)) {
                return "Impossible";
            }

            return "Blue wins";
        }

        // Verify the rules
        if (redHasWon) {
            if (red < blue || !hasOnlyOneWinningPath(board, RED_IDENTIFIER)) {
                return "Impossible";
            }

            return "Red wins";
        }

        return "Nobody wins";
    }

    private static boolean hasWon(char[][] board, char who) {
        Stack<Cell> stack = new Stack<>();
        Set<Cell> visited = new HashSet<>();

        for (int i = 0; i < board.length; i++) {
            if (who == BLUE_IDENTIFIER && board[i][0] == BLUE_IDENTIFIER) {
                stack.push(new Cell(i, 0));
            } else if (who == RED_IDENTIFIER && board[0][i] == RED_IDENTIFIER) {
                stack.push(new Cell(0, i));
            }

            while (!stack.isEmpty()) {
                Cell n = stack.pop();
                visited.add(n);

                if (who == BLUE_IDENTIFIER && n.column == board.length - 1) {
                    // Blue won
                    return true;
                }

                if (who == RED_IDENTIFIER && n.row == board.length - 1) {
                    // Red won
                    return true;
                }

                List<Cell> cellList = Arrays.asList(
                        new Cell(n.row - 1, n.column),
                        new Cell(n.row - 1, n.column + 1),
                        new Cell(n.row, n.column - 1),
                        new Cell(n.row, n.column + 1),
                        new Cell(n.row + 1, n.column - 1),
                        new Cell(n.row + 1, n.column)
                );

                stack.addAll(
                    cellList.stream()
                        .filter(c -> (c.column >= 0 && c.column < board.length)
                                && (c.row >= 0 && c.row < board.length)
                                && getCell(c, board) == who
                                && !visited.contains(c))
                        .collect(Collectors.toList())
                );
            }
        }

        return false;
    }

    public static boolean hasOnlyOneWinningPath(char [][] board, char who) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c] == who) {
                    board[r][c] = '.';

                    if (!hasWon(board, who)) {
                        return true;
                    }

                    board[r][c] = who;
                }
            }
        }

        return false;
    }

    public static char getCell(Cell cell, char[][] board) {
        return board[cell.row][cell.column];
    }

    private static class Cell {
        public final int row;
        public final int column;
        public Cell(int l, int r) {
            this.row = l;
            this.column = r;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Cell cell = (Cell) o;

            if (row != cell.row) return false;
            return column == cell.column;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + column;
            return result;
        }
    }
}
