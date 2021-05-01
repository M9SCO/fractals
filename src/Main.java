import Complex.ComplexNumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import static Complex.ComplexNumber.parseComplex;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Main extends JComponent {

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final String[] fractals_strings = {"Mandelbrot Set", "Julia Set", "Newton Fractal"};
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
    private final JTextField x0FormattedField,x1FormattedField,x2FormattedField,x3FormattedField,x4FormattedField,x5FormattedField;
    private final JLabel iterationsLabel;
    private final JLabel label1;
    private final JLabel label2,label3,label4,label5,label6,label7;
    private final JButton colorButton;
    private final JButton drawButton;
    private final JButton resetButton;

    ActionListener changedFractalType;
    ActionListener changedColor;
    ActionListener buttonRenderFractal;
    ActionListener reset;
    MouseAdapter mouseListener;
    ComplexNumber[] polynomialCoeffs;
    public Main() {
        window = new JFrame("Fractals");
        panel = new JPanel();
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        chooser = new JColorChooser();
        chooseFractalComboBox = new JComboBox<>(fractals_strings);
        chooseFractalComboBox.setToolTipText("Change type of fractal");
        iterationsField = new JTextField("200",4);
        colorButton = new JButton("Color");
        colorButton.setToolTipText("<HTML>Change main color for fractal<br>Default color - Red");
        iterationsLabel = new JLabel("Iterations:");
        iterationsLabel.setToolTipText("Number of repeat for mathematical function");
        drawButton = new JButton("Draw");
        drawButton.setToolTipText("Render fractal by entered parameters");
        resetButton = new JButton("Reset");
        resetButton.setEnabled(false);
        resetButton.setToolTipText("Change scale and position by default");
        field1 = new JTextField("-0.4",3);
        field2 = new JTextField("0.6",3);
        label1 = new JLabel("cReal:");
        label1.setToolTipText("Real part (a) of complex number (a+bi)");
        label2 = new JLabel("cImag:");
        label2.setToolTipText("Imaginary part (bi) of complex number (a+bi)");
        x0FormattedField = new JTextField("-1", 3);
        x1FormattedField = new JTextField("0", 3);
        x2FormattedField = new JTextField("0", 3);
        x3FormattedField = new JTextField("1", 3);
        x4FormattedField = new JTextField("5", 3);
        x5FormattedField = new JTextField("5", 3);
        ImageIcon icon = new ImageIcon("fractals/src/images/info.svg");
        JLabel image = new JLabel(icon);
        image.setToolTipText("<HTML>The Mandelbrot set is the set obtained from the quadratic recurrence equation<br>Zn+1=Zn^2+C, where Z0=0");
        label3 = new JLabel("*x^5 +");
        label3.setToolTipText("5th order polynomial coefficient");
        label4 = new JLabel("*x^4 +");
        label4.setToolTipText("4th order polynomial coefficient");
        label5 = new JLabel("*x^3 +");
        label5.setToolTipText("3th order polynomial coefficient");
        label6 = new JLabel("*x^2 +");
        label6.setToolTipText("2th order polynomial coefficient");
        label7 = new JLabel("*x +");
        label7.setToolTipText("1th order polynomial coefficient");
        polynomialCoeffs = new ComplexNumber[6];
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
                } else if (Objects.equals(fractal_type,fractals_strings[2])){
                    fractal_selected = 2;
                    polynomialCoeffs[0] = parseComplex(x0FormattedField.getText());
                    polynomialCoeffs[1] = parseComplex(x1FormattedField.getText());
                    polynomialCoeffs[2] = parseComplex(x2FormattedField.getText());
                    polynomialCoeffs[3] = parseComplex(x3FormattedField.getText());
                    polynomialCoeffs[4] = parseComplex(x4FormattedField.getText());
                    polynomialCoeffs[5] = parseComplex(x5FormattedField.getText());
                }
                drawFractal();
                canvas.repaint();
                canvas.addMouseListener(mouseListener);
                resetButton.setEnabled(true);
            }
            catch (NumberFormatException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(window, "Enter iterations, cReal, cImag, x5-x1","Error", JOptionPane.ERROR_MESSAGE);
            }
        };
        reset = e->{
            if (resetButton.isEnabled()) {
                zoomFactor = DEFAULT_ZOOM;
                topLeftX = DEFAULT_TOP_LEFT_X;
                topLeftY = DEFAULT_TOP_LEFT_Y;
                drawFractal();
                canvas.repaint();
            }
        };
        changedFractalType = e -> {
            zoomFactor = DEFAULT_ZOOM;
            topLeftX = DEFAULT_TOP_LEFT_X;
            topLeftY = DEFAULT_TOP_LEFT_Y;
            panel.removeAll();
            panel.add(chooseFractalComboBox);
            panel.add(image);
            panel.add(iterationsLabel);
            panel.add(iterationsField);
            resetButton.setEnabled(false);
            image.setToolTipText("<HTML>The Mandelbrot set is the set obtained from the quadratic recurrence equation<br>Zn+1=Zn^2+C, where Z0=0");
            iterationsField.setText("200");
            if (fractals_strings[1].equals(chooseFractalComboBox.getSelectedItem())) {
                image.setToolTipText("<HTML>The Julia set is the set obtained from the quadratic recurrence equation<br>Zn+1=Zn^2+C");
                panel.add(label1);
                panel.add(field1);
                panel.add(label2);
                panel.add(field2);
            } else if (fractals_strings[2].equals(chooseFractalComboBox.getSelectedItem())) {
                image.setToolTipText("<HTML>The Newton fractal is a boundary set in the complex plane which is characterized by Newton's method applied to a fixed polynomial or transcendental function<br>" +
                        "Zn+1=Zn-(P(Zn)/P'(Zn))");
                iterationsField.setText("32");
                panel.add(x5FormattedField);
                panel.add(label3);
                panel.add(x4FormattedField);
                panel.add(label4);
                panel.add(x3FormattedField);
                panel.add(label5);
                panel.add(x2FormattedField);
                panel.add(label6);
                panel.add(x1FormattedField);
                panel.add(label7);
                panel.add(x0FormattedField);
            }
            panel.add(colorButton);
            panel.add(resetButton);
            panel.add(drawButton);
            window.getContentPane().add(panel, BorderLayout.SOUTH);
            window.getContentPane().validate();
        };
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mX = e.getX();
                mY = e.getY();
                fractal_type = (String) chooseFractalComboBox.getSelectedItem();
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1 -> {
                        adjustZoom(mX, mY, zoomFactor * 2);
                    }
                    case MouseEvent.BUTTON3 -> {
                        adjustZoom(WIDTH/2, HEIGHT/2, zoomFactor /2);
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
        panel.add(chooseFractalComboBox);
        panel.add(image);
        panel.add(iterationsLabel);
        panel.add(iterationsField);
        panel.add(colorButton);
        panel.add(resetButton);
        panel.add(drawButton);
        window.getContentPane().add(canvas);
        window.getContentPane().add(panel, BorderLayout.SOUTH);
        window.pack();
        window.setVisible(true);
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
            case 0 -> new RenderFractal(WIDTH,HEIGHT,iterations, buffer, color,0,0,zoomFactor,topLeftX,topLeftY,null).render();
            case 1 -> new RenderFractal(WIDTH,HEIGHT,iterations, buffer, color,cr,ci,zoomFactor,topLeftX,topLeftY,null).render();
            case 2 -> new RenderFractal(WIDTH,HEIGHT,iterations, buffer, color,cr,ci,zoomFactor,topLeftX,topLeftY,polynomialCoeffs).render();
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
