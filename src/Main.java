import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main extends JComponent {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final int basement = 50;
    private final BufferedImage buffer;
    public Main() {



        buffer = new BufferedImage(WIDTH, HEIGHT - basement, BufferedImage.TYPE_INT_RGB);

        Mandelbrot mandelbrot = new Mandelbrot(WIDTH, HEIGHT-basement, 255, buffer, 0f);
        //Blue - 0.5, Pink - 0.9f, Red -0.0f, Yellow - 0.1f, Green - 0.3f
        mandelbrot.render();

        JFrame frame = new JFrame("Fractals");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);


    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void addNotify() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, null);

    }
}
