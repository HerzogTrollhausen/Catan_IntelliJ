import javax.swing.*;
import java.awt.event.WindowEvent;

class Handel extends JPanel
{
    private JFrame f;
    private Handelzeile[] zeilen;
    private JButton ja, nein;
    private JPanel buttons;

    Handel(int art) //0-4 spezial, 5 3:1, 6 4:1, 7 Erfindung
    {
        framezeug();
        zeilen = new Handelzeile[5];
        for (int i = 0; i < 5; i++)
        {
            zeilen[i] = new Handelzeile(i, 6);
            add(zeilen[i]);
        }
        ja = new JButton("Ok");
        ja.addActionListener(e -> {
            try
            {
                if (isBankhandelErlaubt(art))
                {
                    int[] s1a = new int[5];
                    for (int i = 0; i < 5; i++)
                    {
                        s1a[i] = zeilen[i].wert();
                    }

                    OnlineInterpreter.bekommen(Main.ich(), s1a);

                    Bildschirm.anderePanelAkt();
                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                } else
                {
                    //new Fehler("Kein passendes Handelsverhältnis");
                    JOptionPane.showMessageDialog(f, "Kein passendes Handelsverhältnis", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex)
            {
                JOptionPane.showMessageDialog(f, "Nicht genug Rohstoffe", "Fehler", JOptionPane.ERROR_MESSAGE);

            }

        }
        );
        nein = new JButton("Abbrechen");
        nein.addActionListener(e -> f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING))
        );

        buttons = new JPanel();
        add(buttons);
        buttons.add(ja);
        buttons.add(nein);

        f.setVisible(true);
    }

    /**
     *
     * @param art Die Art des Bankhandels (0-4 spezial, 5 3:1, 6 4:1)
     * @return Ob die eingegebenen Werte das richtige Verhältnis für den angegebenen Bankhandel haben.
     */
    private boolean isBankhandelErlaubt(int art)
    {
        int konto = 0;
        int anzahl;
        if(art == 5)
        {
            anzahl = 3;
        }
        else if(art == 6)
        {
            anzahl = 4;
        }
        else if(art == 7)
        {
            return isErfindungErlaubt();
        }
        else
        {
            return isRohstoffPortHandelErlaubt(art);
        }
        for (Handelzeile aZeilen : zeilen)
        {
            if (aZeilen.wert() < 0 && aZeilen.wert() > anzahl * -1)
            {
                return false;
            }
            konto += aZeilen.wert() >= 0 ? aZeilen.wert() : aZeilen.wert() / anzahl;
        }
        return konto == 0;
    }

    private boolean isErfindungErlaubt()
    {
        int konto = 0;
        for (Handelzeile aZeilen : zeilen)
        {
            if (aZeilen.wert() < 0)
            {
                return false;
            } else
            {
                konto += aZeilen.wert();
            }
        }
        return konto == 2;
    }

    private boolean isRohstoffPortHandelErlaubt(int art)
    {
        int konto = 0;
        boolean festgelegt = false; //sobald true, darf sich portZuSonstigem nicht mehr ändern
        boolean portZuSonstigem = true; //true, wenn bei einem Schafhafen zwei Schafe in ein Holz umgewandelt werden
        for(int i = 0; i < zeilen.length; i++)
        {
            if(i != art)
            {
                if(portZuSonstigem)
                {
                    if(zeilen[i].wert() > 0)
                    {
                        konto += zeilen[i].wert();
                        festgelegt = true;
                    }
                    else if(zeilen[i].wert() < 0)
                    {
                        if(festgelegt)
                        {
                            System.out.println("!= art; portZuSonstigem, wert < 0, festgelegt");
                            return false;
                        }
                        else
                        {
                            portZuSonstigem = false;
                            festgelegt = true;
                            konto += zeilen[i].wert()/2;
                        }
                    }
                }
                else
                {
                    if(zeilen[i].wert() > 0)
                    {
                        System.out.println("!= art, !portZuSonstigem, wert > 0");
                        return false;
                    }
                    else
                    {
                        konto += zeilen[i].wert()/2;
                    }
                }
            }
            else
            {
                if(portZuSonstigem)
                {
                    if(zeilen[i].wert() > 0)
                    {
                        if(festgelegt)
                        {
                            System.out.println("==art, portZuSonstigem, wert > 0, festgelegt");
                            return false;
                        }
                        else
                        {
                            portZuSonstigem = false;
                            festgelegt = true;
                            konto += zeilen[i].wert();//2;

                        }
                    }
                    else if(zeilen[i].wert() < 0)
                    {
                        konto += zeilen[i].wert()/2;
                        festgelegt = true;
                    }
                }
                else
                {
                    if(zeilen[i].wert() < 0)
                    {
                        System.out.println("==art, !portZuSonstigem, wert < 0");
                        return false;
                    }
                    else
                    {
                        konto += zeilen[i].wert();
                    }
                }
            }
        }
        System.out.println(konto);
        return konto == 0;
    }

    private void framezeug()
    {
        f = new JFrame("Handel zwischen Spielern");
        f.setContentPane(this);
        f.setSize(350, 500);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    Handel(Spieler s1, Spieler s2)
    {
        framezeug();
        zeilen = new Handelzeile[5];
        for (int i = 0; i < 5; i++)
        {
            zeilen[i] = new Handelzeile(i, s1, s2);
            add(zeilen[i]);
        }
        ja = new JButton("Ok");
        ja.addActionListener(e ->
                {
                    int[] s1a = new int[5];
                    int[] s2a = new int[5];
                    for (int i = 0; i < 5; i++)
                    {
                        s1a[i] = zeilen[i].wert();
                        s2a[i] = zeilen[i].wert() * -1;
                    }
                    if(Main.lokal) {
                        OnlineInterpreter.bekommen(s1, s1a);
                        OnlineInterpreter.bekommen(s2, s2a);
                    }
                    else
                    {
                        OnlineInterpreter.spielerHandel(s1, s2, s2a);
                    }
                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                }
        );

        nein = new JButton("Abbrechen");
        nein.addActionListener(e -> f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING))
        );

        buttons = new JPanel();
        add(buttons);
        buttons.add(ja);
        buttons.add(nein);

        f.setVisible(true);
    }
}