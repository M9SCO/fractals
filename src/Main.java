import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Main extends JComponent {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public Color color;
    public  JColorChooser chooser;
    BufferedImage buffer;
    public Main() {
        String[] items = {
                "Mandelbrot Set",
                "Julia Set",
        };
        buffer = new BufferedImage(WIDTH, HEIGHT , BufferedImage.TYPE_INT_RGB);
        //Blue - 0.5, Pink - 0.9f, Red -0.0f, Yellow - 0.1f, Green - 0.3f
        JFrame frame = new JFrame("Fractals");
        canvas.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        JPanel panel = new JPanel();
        panel.setSize(800,50);
        JComboBox comboBox = new JComboBox(items);
        JButton colorButton = new JButton("Choose color");
        chooser = new JColorChooser();
        JTextField field = new JTextField(10);
        JLabel label = new JLabel("Iterations:");
        JButton button = new JButton("OK");
        JTextField field1 = new JTextField(10);
        JTextField field2 = new JTextField(10);
        JLabel label1 = new JLabel("cReal:");
        JLabel label2 = new JLabel("cImag");
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = chooser.showDialog(chooser, "Choose color", chooser.getColor());
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox.getSelectedItem().equals(items[0])) {
                    Mandelbrot mandelbrot = new Mandelbrot(WIDTH, HEIGHT, Integer.parseInt(field.getText()), buffer, color,0,0);
                    mandelbrot.render();
                    canvas.repaint();
                }
                else if(comboBox.getSelectedItem().equals(items[1])){
                    Julia julia = new Julia(WIDTH, HEIGHT, Integer.parseInt(field.getText()), buffer, color,Float.parseFloat(field1.getText()),Float.parseFloat(field2.getText()));
                    julia.render();
                    canvas.repaint();
                }
            }
        });
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox.getSelectedItem().equals(items[0])) {
                    panel.removeAll();
                    panel.add(comboBox);
                    panel.add(label);
                    panel.add(field);
                    panel.add(colorButton);
                    panel.add(button);
                    frame.getContentPane().add(panel,BorderLayout.SOUTH);
                    frame.getContentPane().validate();
                }
                else if(comboBox.getSelectedItem().equals(items[1])){
                    panel.removeAll();
                    panel.add(comboBox);
                    panel.add(label);
                    panel.add(field);
                    panel.add(label1);
                    panel.add(field1);
                    panel.add(label2);
                    panel.add(field2);
                    panel.add(colorButton);
                    panel.add(button);
                    frame.getContentPane().add(panel,BorderLayout.SOUTH);
                    frame.getContentPane().validate();
                }
            }
        });
        panel.add(comboBox);
        panel.add(label);
        panel.add(field);
        panel.add(colorButton);
        panel.add(button);
        frame.getContentPane().add(canvas);
        frame.getContentPane().add(panel,BorderLayout.SOUTH);
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
}
