import java.awt.*;
import java.awt.image.BufferedImage;

public class Julia extends Fractal {


    Julia(int w, int h, int i, BufferedImage buffer, Color color_correct, double cr, double ci, int scale) {
        super(w, h, i, buffer, scale, color_correct, cr, ci);
    }

    @Override
    public void render() {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                int color = getColor(2.0f * (x - WIDTH / SCALE) / (WIDTH / SCALE), 1.33f * (y - HEIGHT / SCALE) / (HEIGHT / SCALE));
                BUFFER.setRGB(x, y, color);
            }
    }
    
}
