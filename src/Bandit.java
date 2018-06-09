public class Bandit
{
    static boolean amSetzen = false;
    static Feld feld;
    static Zahlplatte platte;

    static void ausschicken()
    {
        amSetzen = true;
        Main.bildschirm.naechster.setEnabled(false);
        Main.bildschirm.naechster.setToolTipText(nuz.NAECHSTER_RAEUBER);
    }

    static void hinsetzen(Feld feld1)
    {
        OnlineInterpreter.raeuberVersetzen(feld1);
        amSetzen = false;
        feld.ausrauben();
        Main.bildschirm.naechster.setEnabled(true);
        Main.bildschirm.naechster.setToolTipText(nuz.NAECHSTER_DEFAULT);
    }
}
