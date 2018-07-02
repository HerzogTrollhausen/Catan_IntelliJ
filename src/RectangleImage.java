import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class RectangleImage// extends JComponent
{
    Image img;
    public Rectangle rechteck;

    RectangleImage(Image img, double x, int y)
    {
        if (img == null)
        {
            throw new IllegalArgumentException("img is null");
        }
        this.img = img;
        this.rechteck = new Rectangle((int) x, y, img.getHeight(null), img.getWidth(null));
    }

    @SuppressWarnings("SameParameterValue")
    void setSize(int width, int height)
    {
        rechteck.setSize(width, height);
    }

    public void draw(Graphics2D g2)
    {
        try
        {
            g2.drawImage(img, rechteck.x, rechteck.y, rechteck.width, rechteck.height, null);
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    void bewegen(double x, double y)
    {
        this.rechteck.setBounds((int) x, (int) y, rechteck.width, rechteck.height);
    }

}