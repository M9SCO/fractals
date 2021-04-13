import javax.swing.*;
import java.awt.*;

public class Main extends JComponent {
    public static void main(String[] args) {
        new Main();
    }

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;


    public Main(){
        JFrame frame = new JFrame("Fractals");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setMinimumSize(frame.getSize());
    }

    @Override
    public void addNotify() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
}
