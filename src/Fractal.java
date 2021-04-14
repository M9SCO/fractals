import java.awt.image.BufferedImage;

public abstract class Fractal {
    public static int HEIGHT, WIDTH, ITERATIONS, SCALE;
    public static float COLOR_CORRECT;
    public static BufferedImage BUFFER;
    public abstract void render();
    public abstract int getColor(float x, float y);

    Fractal(int w, int h, int i, BufferedImage buffer, int scale, float color_correct) {
        WIDTH = w;
        HEIGHT = h;
        ITERATIONS = i;
        BUFFER = buffer;
        SCALE = scale;
        COLOR_CORRECT = color_correct;
    }


}
