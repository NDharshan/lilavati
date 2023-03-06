import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FunctionPlotter extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double X_MIN = -5;
    private static final double X_MAX = 5;
    private static final double Y_MIN = -5;
    private static final double Y_MAX = 5;
    private static final double STEP = 0.01;
    private static final Color GRAPH_COLOR = Color.RED;

    private double f(double x) {
        // Define the mathematical function here
        return Math.sin(x);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(GRAPH_COLOR);
        double x, y;
        int prevX = -1, prevY = -1;
        for (x = X_MIN; x <= X_MAX; x += STEP) {
            y = f(x);
            int pixelX = (int) ((x - X_MIN) / (X_MAX - X_MIN) * (WIDTH - 1));
            int pixelY = (int) ((1 - (y - Y_MIN) / (Y_MAX - Y_MIN)) * (HEIGHT - 1));
            if (prevX != -1 && prevY != -1) {
                g2d.drawLine(prevX, prevY, pixelX, pixelY);
            }
            prevX = pixelX;
            prevY = pixelY;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Function Plotter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new FunctionPlotter());
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
