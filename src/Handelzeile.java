import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.*;

public class Handelzeile extends JPanel
{
    private JSpinner betrag;

    Handelzeile(int gut, Spieler s1, Spieler s2)
    {
        normal(s1, gut);
        betrag = new JSpinner(new SpinnerNumberModel(0, -1 * s1.inv.rohstoffe[gut], s2.inv.rohstoffe[gut], 1));
        betrag.setMinimumSize(new Dimension(100, 50));
        add(betrag);
        JLabel rechtslabel = new JLabel("" + s2.inv.rohstoffe[gut]);
        add(rechtslabel);
    }

    private void normal(Spieler s1, int gut)
    {
        JLabel name = new JLabel(Nuz.rohstoffName(gut, true) + ":");
        add(name);
        JLabel linkslabel = new JLabel("" + s1.inv.rohstoffe[gut]);
        add(linkslabel);
    }

    Handelzeile(int gut, int art)
    {
        normal(Main.spieler(), gut);
        if (art == 6)
        {
            betrag = new JSpinner();//new SpinnerNumberModel(0, -1 * Main.spieler().inv.rohstoffe[gut], 0, 1));
        }
        betrag.setMinimumSize(new Dimension(100, 50));
        add(betrag);
    }

    int wert()
    {
        return (int) betrag.getValue();
    }
}