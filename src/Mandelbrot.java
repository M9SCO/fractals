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

        if(i == ITERATIONS) return 0x00000000;
        return Color.HSBtoRGB((((float) i/ITERATIONS)+ COLOR_CORRECT)%1, 0.5f, 1);
    }

    Mandelbrot(int w, int h, int i, BufferedImage buffer, float color_correct) {
        super(w, h, i, buffer, 250, color_correct);
    }
}
