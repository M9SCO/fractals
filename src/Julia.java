import java.awt.*;
import java.awt.image.BufferedImage;

public class Julia extends Fractal {
    @Override
    public void render() {
        for (int x = 0; x< WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                int color = getColor(2.0f*(x-WIDTH/2)/(WIDTH/2), 1.33f*(y-HEIGHT/2)/(HEIGHT/2));
                BUFFER.setRGB(x, y, color);
            }

    }
    @Override
    public int getColor(float x, float y) {
        ComplexNumber constant = new ComplexNumber(cReal,cImag);
        ComplexNumber newz = new ComplexNumber(x, y);
        int i=0;
        for(; i<ITERATIONS; i++) {
            newz = newz.square();
            newz.add(constant);
            if(newz.mod() > 2)
                break;
        }
        int red = COLOR_CORRECT.getRed();
        int green = COLOR_CORRECT.getGreen();
        int blue = COLOR_CORRECT.getBlue();
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        float hue = hsb[0];
        float saturation = hsb[1];
        float brightness = hsb[2];
        if(i == ITERATIONS) return 0x00000000;
        return Color.HSBtoRGB(((float)i/ITERATIONS+hue)%1,saturation,brightness);
    }

    Julia(int w, int h, int i, BufferedImage buffer, Color color_correct,double cr, double ci) {
        super(w, h, i, buffer, 250, color_correct,cr,ci);
    }
}
