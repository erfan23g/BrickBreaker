import java.awt.Color;

public class ColorPalate {

    public static Color GREEN = new Color(39, 174, 96);
    public static Color RED = new Color(231, 76, 60);
    public static Color YELLOW = new Color(240, 250, 0);
    public static Color BLUE = new Color(52, 100, 255);
    public static Color PURPLE = new Color(129,10,100);
    public static Color DARK_GREEN = new Color(44,120,44);
    public static Color PINK = new Color(240,66,200);
    public static Color LIGHT_GREEN = new Color(190, 237, 19);
    public static Color ORANGE = new Color(200, 100, 0);
    public static Color CYAN = new Color(0, 200, 200);



    public static Color randomColor() {
        Color[] choices =  {GREEN, RED, YELLOW, BLUE, PURPLE, DARK_GREEN, PINK, LIGHT_GREEN, ORANGE, CYAN};
        return choices[(int) (Math.random() * 10)];
    }
}
