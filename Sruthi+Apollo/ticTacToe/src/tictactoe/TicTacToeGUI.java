package tictactoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class TicTacToeGUI extends JFrame implements ActionListener {

    private final Board board;
    private final GameLogic logic;

    private final JButton[][] cellButtons = new JButton[3][3];
    private final JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
    private final JButton newGameButton = new JButton("New Game");
    private final JButton randomButton = new JButton("Random Board");
    private final JButton refreshButton = new JButton("Refresh");

    private static final Color BG = new Color(30, 33, 41);
    private static final Color PANEL_BG = new Color(40, 44, 54);
    private static final Color CELL_BG = new Color(55, 60, 72);
    private static final Color CELL_HOVER = new Color(70, 76, 92);
    private static final Color X_COLOR = new Color(94, 199, 255);
    private static final Color O_COLOR = new Color(255, 138, 128);
    private static final Color TEXT = new Color(230, 232, 238);
    private static final Color MUTED = new Color(160, 165, 178);

    public TicTacToeGUI() {
        super("Tic Tac Toe");

        this.board = new Board("board.csv");
        this.logic = new GameLogic();

        char[][] grid = board.getGrid();
        if (grid == null || grid[0][0] == '\u0000') {
            board.clearBoard();
        }

        buildUI();
        refreshBoard();
    }

    private void buildUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 12));

        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(TEXT);
        title.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 10));
        center.setBackground(BG);
        center.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));

        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        statusLabel.setForeground(MUTED);
        center.add(statusLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 8, 8));
        gridPanel.setBackground(PANEL_BG);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        gridPanel.setPreferredSize(new Dimension(360, 360));

        Font cellFont = new Font("SansSerif", Font.BOLD, 64);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton cell = new JButton("");
                cell.setFont(cellFont);
                cell.setFocusPainted(false);
                cell.setBackground(CELL_BG);
                cell.setForeground(TEXT);
                cell.setBorder(BorderFactory.createEmptyBorder());
                cell.setOpaque(true);
                cell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                cell.setActionCommand(row + "," + col);
                cell.addActionListener(this);
                cellButtons[row][col] = cell;
                gridPanel.add(cell);
            }
        }

        JPanel gridWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        gridWrap.setBackground(BG);
        gridWrap.add(gridPanel);
        center.add(gridWrap, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 16));
        buttons.setBackground(BG);

        styleActionButton(newGameButton, new Color(94, 199, 255));
        styleActionButton(randomButton, new Color(255, 184, 108));
        styleActionButton(refreshButton, new Color(166, 226, 162));

        newGameButton.addActionListener(e -> {
            board.clearBoard();
            refreshBoard();
        });
        randomButton.addActionListener(e -> {
            board.createRandomBoard();
            refreshBoard();
            announceIfGameOver();
        });
        refreshButton.addActionListener(e -> {
            board.clearBoard();
            refreshBoard();
        });

        buttons.add(newGameButton);
        buttons.add(randomButton);
        buttons.add(refreshButton);
        add(buttons, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void styleActionButton(JButton b, Color accent) {
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setBackground(accent);
        b.setForeground(new Color(250, 250, 250));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd == null || !cmd.contains(",")) {
            return;
        }
        String[] parts = cmd.split(",");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);

        if (logic.isGameOver(board)) {
            return;
        }
        if (board.getCell(row, col) != 'E') {
            return;
        }

        char player = logic.getCurrentPlayer(board);
        board.setCell(row, col, player);
        refreshBoard();
        announceIfGameOver();
    }

    private void refreshBoard() {
        char[][] grid = board.getGrid();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char c = grid[row][col];
                JButton b = cellButtons[row][col];
                if (c == 'X') {
                    b.setText("X");
                    b.setForeground(X_COLOR);
                } else if (c == 'O') {
                    b.setText("O");
                    b.setForeground(O_COLOR);
                } else {
                    b.setText("");
                }
            }
        }

        if (logic.checkWin(board, 'X')) {
            statusLabel.setText("X wins!");
            statusLabel.setForeground(X_COLOR);
        } else if (logic.checkWin(board, 'O')) {
            statusLabel.setText("O wins!");
            statusLabel.setForeground(O_COLOR);
        } else if (logic.isDraw(board)) {
            statusLabel.setText("It's a draw");
            statusLabel.setForeground(MUTED);
        } else {
            char next = logic.getCurrentPlayer(board);
            statusLabel.setText("Turn: " + next);
            statusLabel.setForeground(next == 'X' ? X_COLOR : O_COLOR);
        }
    }

    private void announceIfGameOver() {
        if (!logic.isGameOver(board)) {
            return;
        }
        String message;
        if (logic.checkWin(board, 'X')) {
            message = "X wins!";
        } else if (logic.checkWin(board, 'O')) {
            message = "O wins!";
        } else {
            message = "It's a draw.";
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                message + "\n\nPlay again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        board.clearBoard();
        refreshBoard();

        if (choice == JOptionPane.NO_OPTION) {
            dispose();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new TicTacToeGUI().setVisible(true));
    }
}