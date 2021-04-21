import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Main extends JComponent {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final String[] fractals_strings = {"Mandelbrot Set", "Julia Set"};

    public Color color;
    public JColorChooser chooser;
    BufferedImage buffer;
    private final Canvas canvas = new Canvas() {
        @Override
        public void paint(Graphics g) {
            g.drawImage(buffer, 0, 0, null);
        }

        @Override
        public void update(Graphics g) {
            paint(g);
        }
    };
    JFrame window;
    JPanel panel;
    JComboBox chooseFractalComboBox;
    JTextField iterationsField;
    JTextField field1;
    JTextField field2;
    JLabel iterationsLabel;
    JLabel label1;
    JLabel label2;
    JButton colorButton;
    JButton drawButton;
    ActionListener changedFractalType;
    ActionListener changedColor;
    ActionListener buttonRenderFractal;


    public Main() {
        window = new JFrame("Fractals");
        panel = new JPanel();
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        chooseFractalComboBox = new JComboBox(fractals_strings);
        colorButton = new JButton("Choose color");
        chooser = new JColorChooser();

        iterationsField = new JTextField(10);
        iterationsLabel = new JLabel("Iterations:");
        drawButton = new JButton("OK");
        field1 = new JTextField(10);
        field2 = new JTextField(10);
        label1 = new JLabel("cReal:");
        label2 = new JLabel("cImag");

        changedColor = e -> color = JColorChooser.showDialog(chooser, "Choose color", chooser.getColor());
        buttonRenderFractal = e -> {
            int iterations = Integer.parseInt(iterationsField.getText());
            String fractal_type = (String) chooseFractalComboBox.getSelectedItem();

            if (Objects.equals(fractal_type, fractals_strings[0])) {
                new Mandelbrot(WIDTH, HEIGHT, iterations, buffer, color).render();
            } else if (Objects.equals(fractal_type, fractals_strings[1])) {
                float cr = Float.parseFloat(field1.getText()), ci = Float.parseFloat(field1.getText());
                new Julia(WIDTH, HEIGHT, iterations, buffer, color, cr, ci).render();
            }
            canvas.repaint();

        };
        changedFractalType = e -> {
            panel.removeAll();
            panel.add(chooseFractalComboBox);
            panel.add(iterationsLabel);
            panel.add(iterationsField);
            panel.add(colorButton);
            if (fractals_strings[1].equals(chooseFractalComboBox.getSelectedItem())) {
                panel.add(label1);
                panel.add(field1);
                panel.add(label2);
                panel.add(field2);
            }
            panel.add(drawButton);
            window.getContentPane().add(panel, BorderLayout.SOUTH);
            window.getContentPane().validate();
        };


        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        panel.setSize(800, 50);

        colorButton.addActionListener(changedColor);
        drawButton.addActionListener(buttonRenderFractal);
        chooseFractalComboBox.addActionListener(changedFractalType);
        panel.add(chooseFractalComboBox);
        panel.add(iterationsLabel);
        panel.add(iterationsField);
        panel.add(colorButton);
        panel.add(drawButton);
        window.getContentPane().add(canvas);
        window.getContentPane().add(panel, BorderLayout.SOUTH);
        window.pack();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void addNotify() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
}
