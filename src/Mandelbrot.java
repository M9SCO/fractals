import java.awt.*;
import java.awt.image.BufferedImage;

public class Mandelbrot extends Fractal {

    Mandelbrot(int w, int h, int i, BufferedImage buffer, Color color_correct) {
        super(w, h, i, buffer, 250, color_correct, 0, 0);
    }

    @Override
    public void render() {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                int color = getColor((x - WIDTH / 2f) / SCALE, (y - HEIGHT / 2f) / SCALE);
//                System.out.println(color);color
                BUFFER.setRGB(x, y, color);
            }

    }
}
