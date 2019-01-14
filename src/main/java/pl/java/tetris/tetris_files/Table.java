package pl.java.tetris.tetris_files;

//import tetris_files.Shape.Tetrominoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.java.tetris.entities.Game_Results;
import pl.java.tetris.entities.User;
import pl.java.tetris.repositories.GameResultRepository;
import pl.java.tetris.service.AuthService;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Timer;
//import javax.swing.Timer;
import java.util.TimerTask;

@Component
public class Table extends JPanel {

  // @Autowired
   //public AuthService authService;

    // private GameResultsServiceImp gameResultsServiceImp;
    public static ArrayList<String> gameResults = new ArrayList<>();

   // private  EntityManager em;
//   Connection con=null;


    private final int Table_WIDTH = 10;
    private final int Table_HEIGHT = 22;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 300;

    private Timer timer;
    private boolean isFallingFinished = false;
    private boolean isStarted = false;
    private boolean isPaused = false;
    public static boolean isGameRun = true;
    private int numLinesRemoved = 0;
    private int curX = 0;
    private int curY = 0;
    private JLabel statusbar;
    private Shape curPiece;
    private Shape.Tetrominoe[] board;


    public Table(Tetris parent) {
        initTable(parent);
    }

    private void initTable(Tetris parent) {

        setFocusable(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);

        curPiece = new Shape();

        statusbar = parent.getStatusBar();
        board = new Shape.Tetrominoe[Table_WIDTH * Table_HEIGHT];
        addKeyListener(new TAdapter());
        clearTable();
    }

    private int squareWidth() {
        return (int) getSize().getWidth() / Table_WIDTH;
    }

    private int squareHeight() {
        return (int) getSize().getHeight() / Table_HEIGHT;
    }

    private Shape.Tetrominoe shapeAt(int x, int y) {
        return board[(y * Table_WIDTH) + x];
    }

    public void start() {

        isStarted = true;
        clearTable();
        newPiece();
    }

    private void pause() {

        if (!isStarted) {
            return;
        }

        isPaused = !isPaused;

        if (isPaused) {

            statusbar.setText("paused");
        } else {

            statusbar.setText(String.valueOf(numLinesRemoved));
        }
    }

    private void doDrawing(Graphics g) {

        Dimension size = getSize();
        int TableTop = (int) size.getHeight() - Table_HEIGHT * squareHeight();

        for (int i = 0; i < Table_HEIGHT; ++i) {

            for (int j = 0; j < Table_WIDTH; ++j) {

                Shape.Tetrominoe shape = shapeAt(j, Table_HEIGHT - i - 1);

                if (shape != Shape.Tetrominoe.NoShape) {

                    drawSquare(g, 0 + j * squareWidth(),
                            TableTop + i * squareHeight(), shape);
                }
            }
        }

        if (curPiece.getShape() != Shape.Tetrominoe.NoShape) {

            for (int i = 0; i < 4; ++i) {

                int x = curX + curPiece.x(i);
                int y = curY - curPiece.y(i);
                drawSquare(g, 0 + x * squareWidth(),
                        TableTop + (Table_HEIGHT - y - 1) * squareHeight(),
                        curPiece.getShape());
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    private void dropDown() {

        int newY = curY;

        while (newY > 0) {

            if (!tryMove(curPiece, curX, newY - 1)) {

                break;
            }
            --newY;
        }

        pieceDropped();
    }

    private void oneLineDown() {

        if (!tryMove(curPiece, curX, curY - 1)) {

            pieceDropped();
        }
    }

    private void clearTable() {

        for (int i = 0; i < Table_HEIGHT * Table_WIDTH; ++i) {
            board[i] = Shape.Tetrominoe.NoShape;
        }
    }

    private void pieceDropped() {

        for (int i = 0; i < 4; ++i) {

            int x = curX + curPiece.x(i);
            int y = curY - curPiece.y(i);
            board[(y * Table_WIDTH) + x] = curPiece.getShape();
        }

        removeFullLines();

        if (!isFallingFinished) {
            newPiece();
        }
    }

    private void newPiece() {

        curPiece.setRandomShape();
        curX = Table_WIDTH / 2 + 1;
        curY = Table_HEIGHT - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {
            curPiece.setShape(Shape.Tetrominoe.NoShape);
            timer.cancel();
            isStarted = false;
            try (Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/tetris","root","coderslab")) {
                Statement stat = con.createStatement();
                stat.executeUpdate("INSERT INTO tetris.game_results (result) VALUES ('"+gameResults+"')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statusbar.setText("Game over");
        }
    }

    public boolean tryMove(Shape newPiece, int newX, int newY) {

        for (int i = 0; i < 4; ++i) {

            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);

            if (x < 0 || x >= Table_WIDTH || y < 0 || y >= Table_HEIGHT) {
                return false;
            }

            if (shapeAt(x, y) != Shape.Tetrominoe.NoShape) {
                return false;
            }
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;

        repaint();

        return true;
    }

    private void removeFullLines() {

        int numFullLines = 0;

        for (int i = Table_HEIGHT - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < Table_WIDTH; ++j) {

                if (shapeAt(j, i) == Shape.Tetrominoe.NoShape) {

                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {

                ++numFullLines;

                for (int k = i; k < Table_HEIGHT - 1; ++k) {
                    for (int j = 0; j < Table_WIDTH; ++j) {

                        board[(k * Table_WIDTH) + j] = shapeAt(j, k + 1);
                    }
                }
            }
        }

        if (numFullLines > 0) {

            numLinesRemoved += numFullLines;
            gameResults.add(String.valueOf(numLinesRemoved));
            System.out.println(gameResults.toString());
            statusbar.setText(String.valueOf(numLinesRemoved));
            isFallingFinished = true;
            curPiece.setShape(Shape.Tetrominoe.NoShape);


            repaint();
        }
    }

    private void drawSquare(Graphics g, int x, int y,
                            Shape.Tetrominoe shape) {

        Color colors[] = {
                new Color(0, 0, 0), new Color(204, 102, 102),
                new Color(102, 204, 102), new Color(102, 102, 204),
                new Color(204, 204, 102), new Color(204, 102, 204),
                new Color(102, 204, 204), new Color(218, 170, 0)
        };

        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);

    }

    private void doGameCycle() {

        update();
        repaint();
      //  makeupdate();
//        Game_Results game_results = new Game_Results();
//        game_results.setResult(gameResults.toString());
//        gameResultsServiceImp.save(game_results);
    }

//    private void makeupdate(){
//        Game_Results gameRe = new Game_Results();
//        gameRe.setResult(String.valueOf(gameResults));
//        gameResultRepository.save(gameRe);
//
//    }

    private void update() {

        if (isPaused) {
            return;
        }

        if (isFallingFinished) {

            isFallingFinished = false;
            newPiece();
        } else {

            oneLineDown();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            System.out.println("key pressed");

            if (!isStarted || curPiece.getShape() == Shape.Tetrominoe.NoShape) {
                return;
            }

            int keycode = e.getKeyCode();

            if (keycode == KeyEvent.VK_P) {
                pause();
                return;
            }

            if (isPaused) {
                return;
            }

            switch (keycode) {

                case KeyEvent.VK_LEFT:
                    tryMove(curPiece, curX - 1, curY);
                    break;

                case KeyEvent.VK_RIGHT:
                    tryMove(curPiece, curX + 1, curY);
                    break;

                case KeyEvent.VK_DOWN:
                    tryMove(curPiece.rotateRight(), curX, curY);
                    break;

                case KeyEvent.VK_UP:
                    tryMove(curPiece.rotateLeft(), curX, curY);
                    break;

                case KeyEvent.VK_SPACE:
                    dropDown();
                    break;

                case KeyEvent.VK_D:
                    oneLineDown();
                    break;
            }
        }
    }

    @Transactional
    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {

            doGameCycle();
        }
    }
}
