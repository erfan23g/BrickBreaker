
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class EmptyObject extends GameObject{

    public EmptyObject(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void draw(Graphics g) {
        Color transparent = new Color(0, 0, 0, 0);
        g.setColor(transparent);
        g.drawRect(getX(), getY(), getWidth(), getHeight());
    }
}
