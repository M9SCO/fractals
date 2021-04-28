import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Main extends JComponent {

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final String[] fractals_strings = {"Mandelbrot Set", "Julia Set"};
    private static final double DEFAULT_ZOOM       = 250.0;
    private static final double DEFAULT_TOP_LEFT_X = -2.0;
    private static final double DEFAULT_TOP_LEFT_Y = +1.5;

    private double zoomFactor = DEFAULT_ZOOM;
    private double topLeftX   = DEFAULT_TOP_LEFT_X;
    private double topLeftY   = DEFAULT_TOP_LEFT_Y;

    private int fractal_selected;
    private float cr, ci;
    private double mX, mY;
    private int iterations;
    private String fractal_type;

    private Canvas canvas;

    public Color color = Color.red;
    public JColorChooser chooser;
    private final BufferedImage buffer;
    private final JFrame window;
    private final JPanel panel;
    private final JComboBox<String> chooseFractalComboBox;
    private final JTextField iterationsField;
    private final JTextField field1;
    private final JTextField field2;
    private final JLabel iterationsLabel;
    private final JLabel label1;
    private final JLabel label2;
    private final JButton colorButton;
    private final JButton drawButton;
    private final JButton resetButton;

    ActionListener changedFractalType;
    ActionListener changedColor;
    ActionListener buttonRenderFractal;
    ActionListener reset;
    MouseAdapter mouseListener;

    public Main() {
        window = new JFrame("Fractals");
        panel = new JPanel();
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        chooser = new JColorChooser();

        chooseFractalComboBox = new JComboBox<>(fractals_strings);
        iterationsField = new JTextField(10);
        colorButton = new JButton("Change color");
        iterationsLabel = new JLabel("Iterations:");
        drawButton = new JButton("Draw");
        resetButton = new JButton("Reset");
        field1 = new JTextField(10);
        field2 = new JTextField(10);
        label1 = new JLabel("cReal:");
        label2 = new JLabel("cImag:");

        changedColor = e -> color = JColorChooser.showDialog(chooser, "Choose color", chooser.getColor());
        buttonRenderFractal = e -> {
            try {
                iterations = Integer.parseInt(iterationsField.getText());
                fractal_type = (String) chooseFractalComboBox.getSelectedItem();
                if (Objects.equals(fractal_type, fractals_strings[0])) {
                    fractal_selected = 0;
                } else if (Objects.equals(fractal_type, fractals_strings[1])) {
                    fractal_selected = 1;
                    cr = Float.parseFloat(field1.getText());
                    ci = Float.parseFloat(field2.getText());
                }
                drawFractal();
                canvas.repaint();
            }
            catch (NumberFormatException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(window, "Enter iterations, cReal, cImag","Error", JOptionPane.ERROR_MESSAGE);
            }
        };
        reset = e->{
            zoomFactor = DEFAULT_ZOOM;
            topLeftX   = DEFAULT_TOP_LEFT_X;
            topLeftY   = DEFAULT_TOP_LEFT_Y;
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
            panel.add(resetButton);
            window.getContentPane().add(panel, BorderLayout.SOUTH);
            window.getContentPane().validate();
        };
        mouseListener = new MouseAdapter() {
            Thread x = new Thread(new loop());
            @Override
            public void mouseClicked(MouseEvent e) {
                mX = e.getX();
                mY = e.getY();
                fractal_type = (String) chooseFractalComboBox.getSelectedItem();
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1 -> {
                        adjustZoom(mX, mY, zoomFactor * 1);
                        if (x.isInterrupted()) {
                            x.start();
                        }
                        x.start();
                    }
                    case MouseEvent.BUTTON3 -> {
                        x.interrupt();
                        x = new Thread(new loop());
                    }
                }
            }
        };
        canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                g.drawImage(buffer, 0, 0, this);
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
        resetButton.addActionListener(reset);
        chooseFractalComboBox.addActionListener(changedFractalType);
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.addMouseListener(mouseListener);
        panel.add(chooseFractalComboBox);
        panel.add(iterationsLabel);
        panel.add(iterationsField);
        panel.add(colorButton);
        panel.add(drawButton);
        panel.add(resetButton);
        window.getContentPane().add(canvas);
        window.getContentPane().add(panel, BorderLayout.SOUTH);
        window.pack();
        window.setVisible(true);


    }
    public class loop implements Runnable{
        public void run(){
            while(!Thread.interrupted()) {
                adjustZoom( (float)WIDTH/2,(float)HEIGHT/2,zoomFactor*2 );
            }
        }
    }
    public void adjustZoom(double newX,double newY,double newZoomFactor ) {
        topLeftX += newX / zoomFactor;
        topLeftY -= newY / zoomFactor;
        zoomFactor = newZoomFactor;
        topLeftX -= (float)( WIDTH/2) / zoomFactor;
        topLeftY += (float)(HEIGHT/2) / zoomFactor;
        drawFractal();
        canvas.repaint();
    }


    private void drawFractal() {
        switch (fractal_selected) {
            case 0 -> new RenderFractal(WIDTH,HEIGHT,iterations, buffer, color,0,0,zoomFactor,topLeftX,topLeftY).render();
            case 1 -> new RenderFractal(WIDTH,HEIGHT,iterations, buffer, color,cr,ci,zoomFactor,topLeftX,topLeftY).render();
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
