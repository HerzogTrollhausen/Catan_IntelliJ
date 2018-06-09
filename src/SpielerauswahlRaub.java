import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpielerauswahlRaub extends JPanel
{
    private JFrame f;
    public SpielerauswahlRaub(Feld feld)
    {
        int[] spielerInt = feld.angrenzendeSpieler();
        if(spielerInt.length == 0)
        {
            return;
        }
        framezeug();
        String[] spielerString = new String[spielerInt.length];
        for(int i = 0; i < spielerString.length; i++)
        {
            spielerString[i] = Main.spieler[spielerInt[i]].name;
        }
        JComboBox<String> box = new JComboBox<String>(spielerString);
        JButton ok = new JButton(Nuz.OK);
        ok.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int r = box.getSelectedIndex();
                Spieler s = Main.spieler[spielerInt[r]];
                feld.ausrauben(s);
                f.dispose();
            }
        });
        add(box);
        add(ok);
        f.setVisible(true);
    }

    private void framezeug()
    {
        f = new JFrame("Spieler ausrauben");
        f.setContentPane(this);
        f.setSize(350, 150);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
}
