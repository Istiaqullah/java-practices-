package TicTecToe;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeController {

    @FXML private Button btn00, btn01, btn02;
    @FXML private Button btn10, btn11, btn12;
    @FXML private Button btn20, btn21, btn22;

    private Button[][] buttons;
    private char[][] board = new char[3][3];
    private boolean playerTurn = true;
    private int playerPoints = 0;

    private final int AI_DEPTH = 3; // Depth limit for medium difficulty

    @FXML
    public void initialize() {
        buttons = new Button[][]{
                {btn00, btn01, btn02},
                {btn10, btn11, btn12},
                {btn20, btn21, btn22}
        };

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = ' ';
                int row = r;
                int col = c;
                buttons[r][c].setOnAction(e -> handleMove(row, col));
            }
        }
    }

    private void handleMove(int row, int col) {
        if (board[row][col] == ' ' && playerTurn) {
            board[row][col] = 'X';
            buttons[row][col].setText("X");

            if (checkWin('X')) {
                playerPoints += 5;
                showWinner("You Win! 🎉\nYour Points: " + playerPoints);
                disableBoard();
                return;
            }

            if (isDraw()) {
                showWinner("Draw!");
                return;
            }

            playerTurn = false;
            aiMoveMedium();
        }
    }

    // Medium AI using depth-limited minimax
    private void aiMoveMedium() {
        int[] bestMove = minimax(AI_DEPTH, 'O');
        int r = bestMove[0];
        int c = bestMove[1];

        board[r][c] = 'O';
        buttons[r][c].setText("O");

        if (checkWin('O')) {
            showWinner("AI Wins! 😢");
            disableBoard();
            return;
        }

        if (isDraw()) {
            showWinner("Draw!");
            return;
        }

        playerTurn = true;
    }

    // Minimax returns [row, col, score]
    private int[] minimax(int depth, char currentPlayer) {
        List<int[]> available = getAvailableCells();
        int bestScore = (currentPlayer == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestRow = -1, bestCol = -1;

        if (checkWin('X')) return new int[]{-1, -1, -10};
        if (checkWin('O')) return new int[]{-1, -1, 10};
        if (available.isEmpty() || depth == 0) return new int[]{-1, -1, 0};

        for (int[] cell : available) {
            int r = cell[0], c = cell[1];
            board[r][c] = currentPlayer;

            int score = minimax(depth - 1, (currentPlayer == 'O') ? 'X' : 'O')[2];

            board[r][c] = ' ';

            if (currentPlayer == 'O') { // Maximize AI
                if (score > bestScore) {
                    bestScore = score;
                    bestRow = r;
                    bestCol = c;
                }
            } else { // Minimize player
                if (score < bestScore) {
                    bestScore = score;
                    bestRow = r;
                    bestCol = c;
                }
            }
        }

        return new int[]{bestRow, bestCol, bestScore};
    }

    private List<int[]> getAvailableCells() {
        List<int[]> list = new ArrayList<>();
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[r][c] == ' ') list.add(new int[]{r, c});
        return list;
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    private boolean isDraw() {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[r][c] == ' ') return false;
        return true;
    }

    private void disableBoard() {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                buttons[r][c].setDisable(true);
    }

    private void showWinner(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        alert.showAndWait();
    }
}
