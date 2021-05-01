import Complex.ComplexNumber;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderFractal extends Fractal {

    RenderFractal(int w, int h, int i, BufferedImage buffer, Color color_correct,
                  double cr, double ci, double zoom, double topX, double topY, ComplexNumber[] polynomialCoeffs) {
        super(w, h, i, buffer, color_correct, cr, ci, zoom ,topX, topY,polynomialCoeffs);
    }
    @Override
    public void render() {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                if (PolynomialCoeffs==null) BUFFER.setRGB(x, y, getColor(x/zoomFactor + topLeftX,y/zoomFactor - topLeftY));
                else BUFFER.setRGB(x, y, getColorNewton(x/zoomFactor + topLeftX,y/zoomFactor - topLeftY));
    }
}