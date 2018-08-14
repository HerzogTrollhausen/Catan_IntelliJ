import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.*;

class Spielerpanel extends JPanel {
    static JLabel label;

    Spielerpanel() {
        if (!Main.lokal) {
            setBackground(Main.ich().farbe);
        } else {
            setBackground(Color.LIGHT_GRAY);
        }
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label = new JLabel();
        add(label);
    }

    static void akt() {
            Bildschirm.getSpielerpanel().start();
    }

    private void start() {
        if(Main.frueh) {
            if (!Main.lokal) {
                setBackground(Main.ich().farbe);
            }
            label.setText(Main.fruehsiedlung ? Nuz.verbleibendeFruehSiedlungenSpielerpanel((Main.fruehvor + 3) / 2) : Nuz.ECKPANEL_STRASSE_AN_SIEDLUNG);
        }
        else{
            label.setText(""+Main.sWurf);
        }
        revalidate();
        setVisible(true);
    }
}