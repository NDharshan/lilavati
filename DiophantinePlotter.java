import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DiophantinePlotter extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int X_MIN = -10;
    private static final int X_MAX = 10;
    private static final int Y_MIN = -10;
    private static final int Y_MAX = 10;
    private static final Color GRAPH_COLOR = Color.RED;

    private int a = 1;
    private int b = 1;
    private int c = 0;

    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    private boolean isInteger(double n) {
        return Math.abs(n - (int) n) < 0.0001;
    }

    private boolean isSolution(int x, int y) {
        return a * x + b * y == c;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(GRAPH_COLOR);
        int x, y;
        int prevX = -1, prevY = -1;
        for (x = X_MIN; x <= X_MAX; x++) {
            for (y = Y_MIN; y <= Y_MAX; y++) {
                if (isSolution(x, y) && gcd(Math.abs(x), Math.abs(y)) == 1) {
                    int pixelX = (int) ((x - X_MIN) / (X_MAX - X_MIN) * (WIDTH - 1));
                    int pixelY = (int) ((1 - (y - Y_MIN) / (Y_MAX - Y_MIN)) * (HEIGHT - 1));
                    if (prevX != -1 && prevY != -1) {
                        g2d.drawLine(prevX, prevY, pixelX, pixelY);
                    }
                    prevX = pixelX;
                    prevY = pixelY;
                }
            }
        }
    }

    public void setCoefficients(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Diophantine Plotter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DiophantinePlotter plotter = new DiophantinePlotter();
        frame.add(plotter);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                plotter.setCoefficients(i, j, 1);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
