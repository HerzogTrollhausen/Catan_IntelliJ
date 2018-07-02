import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
public class Spielerpanel extends JPanel
{
    JLabel label;

    Spielerpanel()
    {
        setBackground(Main.spieler[Main.ich].farbe);
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
        label.setText(Main.fruehsiedlung?"Du kannst noch "+(Main.fruehvor+3)/2+" Siedlungen bauen!":"Baue eine Straße an die Siedlung!");
        revalidate();
        setVisible(true);
    }
}