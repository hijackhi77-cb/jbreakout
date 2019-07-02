package jbreakout.primitive;

import java.awt.*;
import java.util.Random;

public class ColorManager {

    public static Color getRandomColor() {
        Random r = new Random();
        return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }

    public static Color applyAlpha(Color color, float alpha) {
        return new Color(color.getRed(), color.getGreen(),
                color.getBlue(), Math.round(255 * alpha));
    }
}
