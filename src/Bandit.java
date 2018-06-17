import javax.swing.*;

public class Bandit
{
    static boolean amSetzen = false;
    static Feld feld;

    static void ausschicken()
    {
        amSetzen = true;
        JButton naechster = Bildschirm.getNaechster();
        naechster.setEnabled(false);
        naechster.setToolTipText(Nuz.NAECHSTER_RAEUBER);
    }

    static void hinsetzen(Feld feld1)
    {
        OnlineInterpreter.raeuberVersetzen(feld1);
        amSetzen = false;
        new SpielerauswahlRaub(feld1);
        JButton naechster = Bildschirm.getNaechster();
        naechster.setEnabled(true);
        naechster.setToolTipText(Nuz.NAECHSTER_DEFAULT);
    }
}
