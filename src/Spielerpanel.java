import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
public class Spielerpanel extends JPanel
{
    JLabel label;

    Spielerpanel()
    {
        setBackground(Main.ich().farbe);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label = new JLabel();
        add(label);
    }

    public static void akt()
    {
        if(Main.frueh)
        {
            Bildschirm.getSpielerpanel().start();
        }
        else
        {
            System.err.println("Spielerpanel.akt()"+Main.frueh);
        }
    }

    private void start()
    {
        setBackground(Main.ich().farbe);
        label.setText(Main.fruehsiedlung?Nuz.verbleibendeFruehSiedlungenSpielerpanel((Main.fruehvor+3)/2):Nuz.ECKPANEL_STRASSE_AN_SIEDLUNG);
        revalidate();
        setVisible(true);
    }
}