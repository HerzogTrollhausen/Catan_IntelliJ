import javax.swing.*;

public class Bandit
{
    static boolean amSetzen = false;
    static Feld feld;

    static void ausschicken()
    {
        amSetzen = true;
        Bildschirm.enableNaechster(false);
        Bildschirm.getNaechster().setToolTipText(Nuz.NAECHSTER_RAEUBER);
    }

    static void hinsetzen(Feld feld1)
    {
        OnlineInterpreter.raeuberVersetzen(feld1);
        amSetzen = false;
        Spieler[] auswahl = feld1.angrenzendeSpieler();
        if(auswahl.length > 0)
        {
            Spieler ziel = (Spieler) JOptionPane.showInputDialog(Bildschirm.getF(), Nuz.RAUB_ATTACKER_WELCHEN_SPIELER,
                    Nuz.RAUB_NOTIFICATION_TITLE, JOptionPane.QUESTION_MESSAGE, null, auswahl, auswahl[0]);
            if (ziel != null)
            {
                feld1.ausrauben(ziel);
            }
        }
        JButton naechster = Bildschirm.getNaechster();
        Bildschirm.enableNaechster(true);
        naechster.setToolTipText(Nuz.NAECHSTER_DEFAULT);
    }
}
