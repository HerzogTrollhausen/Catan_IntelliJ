import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Dimension;
class Anderepanel extends JPanel
{
    private Spieler spieler;
    private JLabel[] rohstoffe;
    private JLabel siegpunkte;
    private JButton handel;
    Anderepanel(Spieler spieler)
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.spieler = spieler;
        JPanel farbe = new JPanel();
        farbe.setMinimumSize(new Dimension(Guz.andereX(), 50));
        farbe.setMaximumSize(new Dimension(Guz.andereX(), 50));
        farbe.setBackground(spieler.farbe);
        
        farbe.setLayout(new BoxLayout(farbe, BoxLayout.Y_AXIS));
        handel = new JButton(Nuz.HANDEL);
        handel.addActionListener(e -> new Handel(Main.spieler(), spieler)
        );
        farbe.add(handel);
        
        farbe.setVisible(true);
        add(farbe);
        
        rohstoffe = new JLabel[5];
        siegpunkte = new JLabel();
        for(int i = 0; i < 5; i++)
        {
            rohstoffe[i] = new JLabel();
            add(rohstoffe[i]);
        }
        updateRohstoffe();
        add(siegpunkte);
    }
    
    void updateRohstoffe()
    {
        handel.setVisible(!Main.frueh && spieler != Main.ich() && (spieler == Main.spieler() || Main.spieler() == Main.ich()));
        for(int i = 0; i < 5; i++)
        {
            rohstoffe[i].setText(Nuz.rohstoffName(i, true)+" : "+spieler.inv.rohstoffe[i] + (spieler.letzteErnte == null || spieler.letzteErnte.rohstoffe[i] == 0
            ? "" : "(+"+spieler.letzteErnte.rohstoffe[i]+")"));
        }
        //spieler.letzteErnte = null;
        siegpunkte.setText(Nuz.ANDEREPANEL_SIEGPUNKTE+spieler.siegPunkte());
    }
    
}