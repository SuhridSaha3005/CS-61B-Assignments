package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private final TERenderer ter = new TERenderer();
    private final TETile[][] tiles = new TETile[WIDTH][HEIGHT];

    public HexWorld() {
        ter.initialize(WIDTH, HEIGHT);
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void addHexagon(int xPos, int yPos, TETile tile, int size) {
        int maxLength = 3*size - 2;
        int initialSize = size;
        while (size <= maxLength) {
            drawLine(xPos, yPos, tile, maxLength, size);
            yPos += 1;
            size += 2;
        }
        size -= 2;
        while (size >= initialSize) {
            drawLine(xPos, yPos, tile, maxLength, size);
            yPos += 1;
            size -= 2;
        }
    }

    private void drawLine(int xPos, int yPos, TETile tile, int length, int size) {
        int spaces = (length - size)/2;
        int x = xPos + spaces;
        while (x < xPos + spaces + size) {
            tiles[x][yPos] = tile;
            x += 1;
        }
    }

    public void draw() {
        ter.renderFrame(tiles);
    }

    public static void main(String[] args) {
        HexWorld h = new HexWorld();
        h.addHexagon(5, 5, Tileset.WALL, 2);
        h.draw();
    }
}
