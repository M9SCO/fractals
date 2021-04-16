import java.awt.*;
import java.awt.image.BufferedImage;

public class Mandelbrot extends Fractal {
    @Override
    public void render() {
        for (int x = 0; x< WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                int color = getColor((x- WIDTH/2f)/SCALE, (y-HEIGHT/2f)/SCALE);
//                System.out.println(color);color
                BUFFER.setRGB(x, y, color);
            }

    }

    @Override
    public int getColor(float x, float y) {
        float cx = x, cy = y;
        int i = 0;

        for(;i<ITERATIONS; i++){
            float nx = x*x - y*y + cx;
            float ny = 2*x*y + cy;
            x = nx;
            y = ny;

            if(x*x+y*y>4) break;
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

    Mandelbrot(int w, int h, int i, BufferedImage buffer, Color color_correct) {
        super(w, h, i, buffer, 250, color_correct);
    }
}
