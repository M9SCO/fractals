import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Fractal {
    public static int HEIGHT, WIDTH, ITERATIONS, SCALE;
    public static Color COLOR_CORRECT;
    public static BufferedImage BUFFER;
    public static double cReal;
    public static double cImag;
    public static double zoom;
    public static double topLeftX;
    public static double topLeftY;

    Fractal(int w, int h, int i, BufferedImage buffer, int scale, Color color_correct, double cr, double ci) {
        WIDTH = w;
        HEIGHT = h;
        ITERATIONS = i;
        BUFFER = buffer;
        SCALE = scale;
        COLOR_CORRECT = color_correct;
        cReal = cr;
        cImag = ci;

    }

    public abstract void render();

    public int getColor(float x, float y) {
        ComplexNumber constant;
        if (cReal != 0 | cImag != 0) {
            constant = new ComplexNumber(cReal, cImag);
        } else {
            constant = new ComplexNumber(x, y);
        }

        ComplexNumber newz = new ComplexNumber(x, y);
        int i = 0;
        for (; i < ITERATIONS; i++) {
            newz = newz.square();
            newz.add(constant);
            if (newz.mod() > 2)
                break;
        }
        int red;
        int green;
        int blue;
        if (i == ITERATIONS) {
            i = 1;
            red = 255 - COLOR_CORRECT.getRed();
            green = 255 - COLOR_CORRECT.getGreen();
            blue = 255 - COLOR_CORRECT.getBlue();
        } else {
            red = COLOR_CORRECT.getRed();
            green = COLOR_CORRECT.getGreen();
            blue = COLOR_CORRECT.getBlue();
        }


        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        float hue = hsb[0];
        float saturation = hsb[1];
        float brightness = hsb[2];
        return Color.HSBtoRGB(((float) i / ITERATIONS + hue) % 1, saturation, brightness);
    }}
