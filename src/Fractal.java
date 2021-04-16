import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Fractal {
    public static int HEIGHT, WIDTH, ITERATIONS, SCALE;
    public static Color COLOR_CORRECT;
    public static BufferedImage BUFFER;
    public static double cReal;
    public static double cImag;
    public abstract void render();
    public abstract int getColor(float x, float y);

    Fractal(int w, int h, int i, BufferedImage buffer, int scale, Color color_correct, double cr, double ci) {
        WIDTH = w;
        HEIGHT = h;
        ITERATIONS = i;
        BUFFER = buffer;
        SCALE = scale;
        COLOR_CORRECT = color_correct;
        cReal = cr;
        cImag=ci;
    }
}
