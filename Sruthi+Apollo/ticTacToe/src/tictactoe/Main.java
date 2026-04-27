package tictactoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    private static final Color BG = new Color(30, 33, 41);
    private static final Color PANEL_BG = new Color(40, 44, 54);
    private static final Color TEXT = new Color(230, 232, 238);
    private static final Color MUTED = new Color(160, 165, 178);
    private static final Color UI_ACCENT = new Color(94, 199, 255);
    private static final Color CONSOLE_ACCENT = new Color(255, 138, 128);

    public static void main(String[] args) {
        if (args.length > 0) {
            String mode = args[0].toLowerCase();
            if (mode.equals("console") || mode.equals("c")) {
                new TicTacToeConsole().run();
                return;
            }
            if (mode.equals("ui") || mode.equals("gui")) {
                launchUI();
                return;
            }
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(Main::showChooser);
    }

    private static void showChooser() {
        JFrame chooser = new JFrame("Tic Tac Toe - Choose Mode");
        chooser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chooser.setResizable(false);
        chooser.getContentPane().setBackground(BG);
        chooser.setLayout(new BorderLayout(0, 12));

        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(TEXT);
        title.setBorder(BorderFactory.createEmptyBorder(28, 24, 4, 24));
        chooser.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setBackground(BG);
        center.setBorder(BorderFactory.createEmptyBorder(0, 32, 0, 32));

        JLabel subtitle = new JLabel("How would you like to play?", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitle.setForeground(MUTED);
        center.add(subtitle, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(1, 2, 16, 0));
        buttons.setBackground(BG);
        buttons.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        JButton uiButton = buildModeButton("Play in UI",
                "A windowed game with clickable cells.",
                UI_ACCENT);
        JButton consoleButton = buildModeButton("Play in Console",
                "A text-based game in your terminal.",
                CONSOLE_ACCENT);

        uiButton.addActionListener(e -> {
            chooser.dispose();
            launchUI();
        });

        consoleButton.addActionListener(e -> {
            chooser.dispose();
            System.out.println();
            System.out.println("Switching to console mode...");
            new Thread(() -> new TicTacToeConsole().run(), "tictactoe-console").start();
        });

        buttons.add(uiButton);
        buttons.add(consoleButton);
        center.add(buttons, BorderLayout.CENTER);

        chooser.add(center, BorderLayout.CENTER);

        JLabel hint = new JLabel(
                "Tip: launch with 'java tictactoe.Main console' to skip this screen.",
                SwingConstants.CENTER);
        hint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        hint.setForeground(MUTED);
        hint.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));
        chooser.add(hint, BorderLayout.SOUTH);

        chooser.setPreferredSize(new Dimension(520, 320));
        chooser.pack();
        chooser.setLocationRelativeTo(null);
        chooser.setVisible(true);
    }

    private static JButton buildModeButton(String title, String description, Color accent) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout(0, 6));
        button.setBackground(PANEL_BG);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 2, true),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(accent);

        JLabel descLabel = new JLabel("<html><div style='text-align:center;'>"
                + description + "</div></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        descLabel.setForeground(TEXT);

        JPanel inner = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
        inner.setOpaque(false);
        inner.add(titleLabel);

        button.add(titleLabel, BorderLayout.NORTH);
        button.add(descLabel, BorderLayout.CENTER);

        return button;
    }

    private static void launchUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new TicTacToeGUI().setVisible(true));
    }
}
