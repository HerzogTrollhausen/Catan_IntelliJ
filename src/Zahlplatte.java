import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Zahlplatte extends RectangleImage
{
    Feld feld;
    private String inhalt;
    private Font font;
    private Color color;

    Zahlplatte(Feld feld, int wert, int x, int y)
    {
        super(Buz.KREIS, x, y);
        this.feld = feld;
        inhalt = ""+wert;
        if(wert == 6 || wert == 8)
        {
            font = new Font("Arial", Font.BOLD, 30);
            color = new Color(255, 0, 0);
        }
        else
        {
            font = new Font("Arial", Font.PLAIN, 25);
            color = new Color(0, 0, 0);
        }
        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        Main.bildschirm.add(button);
        button.setBounds((int)rechteck.getX(), (int)rechteck.getY(), (int)rechteck.getWidth(), (int)rechteck.getHeight());
        button.addActionListener(e ->
        {
            if(Bandit.amSetzen)
            {
                Bandit.hinsetzen(feld);
            }
        });
    }

    @Override
    public void draw(Graphics2D g2)
    {
        try
        {
            if(inhalt != null)
            {
                g2.drawImage(feld.besetzt ? Buz.KREIS_BANDIT : Buz.KREIS, rechteck.x, rechteck.y, rechteck.width, rechteck.height, null);
                g2.setFont(font);
                g2.setColor(color);
                g2.drawString(inhalt ,rechteck.x+rechteck.width/2-10, rechteck.y+rechteck.height/2+5);
            }
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}