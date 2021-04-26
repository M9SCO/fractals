import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderFractal extends Fractal {

    RenderFractal(int w, int h, int i, BufferedImage buffer, Color color_correct,double cr, double ci, double zoom, double topX, double topY) {
        super(w,h,i, buffer, color_correct, cr, ci,zoom,topX,topY);
    }
    @Override
    public void render() {
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++)
            {
                int color = getColor(x/zoomFactor + topLeftX,y/zoomFactor - topLeftY);
                BUFFER.setRGB(x,y,color);
            }
        }
    }
}