import Complex.ComplexNumber;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Fractal {
    public static int HEIGHT, WIDTH, ITERATIONS;
    public static Color COLOR_CORRECT;
    public static BufferedImage BUFFER;
    public static double cReal;
    public static double cImag;
    public static double zoomFactor;
    public static double topLeftX;
    public static double topLeftY;
    public static ComplexNumber[] PolynomialCoeffs;
    private static final double EPSILON = 0.0000001;

    Fractal(int w,int h, int i, BufferedImage buffer, Color color_correct,
            double cr, double ci,double zoom, double topX, double topY, ComplexNumber[] complexNumbers) {
        WIDTH = w;
        HEIGHT = h;
        ITERATIONS = i;
        BUFFER = buffer;
        COLOR_CORRECT = color_correct;
        cReal=cr;
        cImag=ci;
        zoomFactor=zoom;
        topLeftX=topX;
        topLeftY=topY;
        PolynomialCoeffs = complexNumbers;
    }

    public int getColor(double x, double y) {
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
    }
    public int getColorNewton(double x, double y){
        ComplexNumber a = new ComplexNumber(x,y);
        int i;
        for (i = 0; i < ITERATIONS; i++) {
            ComplexNumber f = fx(a);
            ComplexNumber df = dfx(a);
            a.subtract(ComplexNumber.divide(f, df));
            if (isRoot(a)) {
                break;
            }
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
    }
    public ComplexNumber fx(ComplexNumber x) {
        ComplexNumber f = new ComplexNumber(0,0);
        for(int z=5;z>=0;z--) {
            if (PolynomialCoeffs[z].getReal() != 0.0 || PolynomialCoeffs[z].getImaginary() != 0.0) {
                ComplexNumber fs;
                if (z!=0)
                    fs = ComplexNumber.pow(x,z);
                else
                    fs = new ComplexNumber(1,0);
                fs.multiply(PolynomialCoeffs[z]);

                f.add(fs);
            }
        }
        return f;
    }
    public ComplexNumber dfx(ComplexNumber x) {
        ComplexNumber df = new ComplexNumber(0,0);
        for(int z=4;z>=0;z--) {
            if (PolynomialCoeffs[z+1].getReal() != 0.0 || PolynomialCoeffs[z+1].getImaginary() != 0.0) {
                ComplexNumber fs;
                if (z!=0)
                    fs = ComplexNumber.pow(x,z);
                else
                    fs = new ComplexNumber(1,0);
                fs.multiply(PolynomialCoeffs[z+1]);
                fs.multiply(new ComplexNumber(z+1,0));

                df.add(fs);
            }
        }
        return df;
    }
    public boolean isRoot(ComplexNumber a) {
        return Math.abs(fx(a).mod()) < EPSILON;
    }
    public abstract void render();
}
