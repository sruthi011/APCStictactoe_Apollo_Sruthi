package tictactoe;

public class GameLogic
{

    // Step 1: Win Checking
    public boolean checkWin(Board board, char player) 
    {
        char[][] grid = board.getGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        // Row-major win check
        for (int row = 0; row < rows; row++) 
        {
            boolean rowWin = true;

            for (int col = 0; col < cols; col++) 
            {
                if (board.getCell(row, col) != player) 
                {
                    rowWin = false;
                    break;
                }
            }

            if (rowWin) {
                return true;
            }
        }

        // Column-major win check
        for (int col = 0; col < cols; col++)
        {
            boolean colWin = true;

            for (int row = 0; row < rows; row++)
            {
                if (board.getCell(row, col) != player)
                {
                    colWin = false;
                    break;
                }
            }

            if (colWin)
            {
                return true;
            }
        }

        // Main diagonal check
        boolean diag1Win = true;
        for (int i = 0; i < rows; i++)
        {
            if (board.getCell(i, i) != player) 
            {
                diag1Win = false;
                break;
            }
        }

        if (diag1Win)
        {
            return true;
        }

        // Other diagonal check
        boolean diag2Win = true;
        for (int i = 0; i < rows; i++) 
        {
            if (board.getCell(i, cols - 1 - i) != player) 
            {
                diag2Win = false;
                break;
            }
        }

        if (diag2Win)
        {
            return true;
        }

        return false;
    }

    // Step 2: Draw Detection
    public boolean isDraw(Board board) 
    {
        char[][] grid = board.getGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        // If there is still an empty spot, not a draw
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                if (board.getCell(row, col) == 'E') 
                {
                    return false;
                }
            }
        }

        // No empty spots and no winner
        if (!checkWin(board, 'X') && !checkWin(board, 'O'))
        {
            return true;
        }

        return false;
    }

    // Step 3: Game Over Logic
    public boolean isGameOver(Board board) 
    {
        if (checkWin(board, 'X'))
        {
            return true;
        }

        if (checkWin(board, 'O')) 
        {
            return true;
        }

        if (isDraw(board))
        {
            return true;
        }

        return false;
    }
    
    
    public char getCurrentPlayer(Board board) 
    {
        int xCount = 0;
        int oCount = 0;

        char[][] grid = board.getGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++) 
            {
                if (board.getCell(row, col) == 'X')
                {
                    xCount++;
                } else if (board.getCell(row, col) == 'O') 
                {
                    oCount++;
                }
            }
        }

        if (xCount == oCount)
        {
            return 'X';
        } else 
        {
            return 'O';
        }//jkjkjklj
        //hello hello hello
        /////dfjkdfjdlkfkd
        
    }
}