package code;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SidePanel extends JPanel{

    private static final int TILE_SIZE = BoardPanel.TILE_SIZE / 2;
    private static final int SHADE_WIDTH = BoardPanel.SHADE_WIDTH / 2;
    public static final int PANEL_WIDTH = 200;
    public static final int PANEL_HIGHT = BoardPanel.PANEL_HEIGHT;
    private static final int TILE_COUNT = 5;
    private static final int SQUARE_CENTER_X = 90;
    private static final int SQUARE_CENTER_Y = 65;
    private static final int SQUARE_SIZE = (TILE_SIZE * TILE_COUNT / 2);
    private static final int INSET = 50;
    private static final int TEXT_INSET = 140;
    private static final int TEXT_STRIDE = 25;
    private static final int TETRIS_STRADE= 370;
    private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 11);
    private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 13);
    private static final Color DRAW_COLOR = new Color(128, 192, 128);

    private Tetris tetris;
    private Timer timer;
    private BufferedImage bufferedImage;
    private int frame;

    public SidePanel(Tetris tetris) {
        this.tetris = tetris;
        setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HIGHT));
        setBackground(Color.BLACK);
        loadPicture();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bufferedImage.getSubimage(frame * 1100, 0, 1100, 176), INSET-30, TETRIS_STRADE, 160, 50, null);
        g.setColor(DRAW_COLOR);
        int offset;
        g.setFont(LARGE_FONT);
        g.drawString("  Wynik: " + tetris.getScore(), INSET, offset = TEXT_INSET);
        g.drawString("Sterowanie", INSET, offset += TEXT_STRIDE);
        g.setFont(SMALL_FONT);
        g.drawString("← - w lewo", INSET, offset += TEXT_STRIDE);
        g.drawString("→ - w prawo", INSET, offset += TEXT_STRIDE);
        g.drawString("↑ -  obróc", INSET, offset += TEXT_STRIDE);
        g.drawString("↓ -  przyśpiesz", INSET, offset += TEXT_STRIDE);
        g.drawString("P -  pauza", INSET, offset + TEXT_STRIDE);

        g.drawRect(SQUARE_CENTER_X - SQUARE_SIZE, SQUARE_CENTER_Y - SQUARE_SIZE, SQUARE_SIZE * 2, SQUARE_SIZE * 2);

        TileType type = tetris.getNextPieceType();

        if (!tetris.isGameOver() && type != null) {
            int cols = type.getCols();
            int rows = type.getRows();
            int dimension = type.getDimension();

            int startX = (SQUARE_CENTER_X - (cols * TILE_SIZE / 2));
            int startY = (SQUARE_CENTER_Y - (rows * TILE_SIZE / 2));

            int top = type.getTopInset(0);
            int left = type.getLeftInset(0);

            for (int row = 0; row < dimension; row++) {
                for (int col = 0; col < dimension; col++) {
                    if (type.isTile(col, row, 0)) {
                        drawTile(type, startX + ((col - left) * TILE_SIZE), startY + ((row - top) * TILE_SIZE), g);
                    }
                }
            }
        }
    }

    private void drawTile(TileType type, int x, int y, Graphics g) {

        g.setColor(type.getBaseColor());
        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        g.setColor(type.getDarkColor());
        g.fillRect(x, y + TILE_SIZE - SHADE_WIDTH, TILE_SIZE, SHADE_WIDTH);
        g.fillRect(x + TILE_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, TILE_SIZE);

        g.setColor(type.getLightColor());
        for (int i = 0; i < SHADE_WIDTH; i++) {
            g.drawLine(x, y + i, x + TILE_SIZE - i - 1, y + i);
            g.drawLine(x + i, y, x + i, y + TILE_SIZE - i - 1);
        }
    }


    private void loadPicture(){
        try {
            bufferedImage = ImageIO.read(getClass().getResource("/resources/tetris_name.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}