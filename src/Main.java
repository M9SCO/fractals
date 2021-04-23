import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Main extends JComponent {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final String[] fractals_strings = {"Mandelbrot Set", "Julia Set"};
    private Canvas canvas;

    public Color color;
    public JColorChooser chooser;
    BufferedImage buffer;
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
    MouseAdapter mouseListener;
    private int scale, fractal_selected;

    float cr, ci;
    int iterations;
    int mX = 0, mY=0;



    public Main() {
        window = new JFrame("Fractals");
        panel = new JPanel();
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        chooser = new JColorChooser();

        chooseFractalComboBox = new JComboBox(fractals_strings);
        iterationsField = new JTextField(10);
        colorButton = new JButton("Choose color");
        iterationsLabel = new JLabel("Iterations:");
        drawButton = new JButton("OK");
        field1 = new JTextField(10);
        field2 = new JTextField(10);
        label1 = new JLabel("cReal:");
        label2 = new JLabel("cImag");



        changedColor = e -> color = JColorChooser.showDialog(chooser, "Choose color", chooser.getColor());
        buttonRenderFractal = e -> {
            iterations = Integer.parseInt(iterationsField.getText());
            String fractal_type = (String) chooseFractalComboBox.getSelectedItem();
            if (Objects.equals(fractal_type, fractals_strings[0])) {
                scale = 300;
                fractal_selected = 0;
            } else if (Objects.equals(fractal_type, fractals_strings[1])) {
                scale = 2;
                fractal_selected = 1;
                cr = Float.parseFloat(field1.getText());
                ci = Float.parseFloat(field2.getText());
            }
            drawFractal();
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

        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mX = e.getX();
                mY = e.getY();
                switch (e.getButton()){
                    case MouseEvent.BUTTON1:
                        scale *= 2;
                        break;
                    case MouseEvent.BUTTON3:
                        scale /= 2;
                        break;

                }
                drawFractal();
                canvas.repaint();

            }
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        };


        canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                g.drawImage(buffer, 0, 0, null);
            }

            @Override
            public void update(Graphics g) {
                paint(g);
            }
        };

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        panel.setSize(WIDTH, 50);

        colorButton.addActionListener(changedColor);
        drawButton.addActionListener(buttonRenderFractal);
        chooseFractalComboBox.addActionListener(changedFractalType);
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.addMouseListener(mouseListener);
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




    private void drawFractal() {
        switch (fractal_selected) {
            case 0 -> new Mandelbrot(WIDTH, HEIGHT, iterations, buffer, color, scale).render();
            case 1 -> new Julia(WIDTH, HEIGHT, iterations, buffer, color, cr, ci, scale).render();
            default -> throw new IllegalStateException("Unexpected value: " + fractal_selected);
        }
    }


    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void addNotify() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
}
