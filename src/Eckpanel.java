import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

public class Eckpanel extends JPanel
{
    private JLabel desc;
    private JButton button;

    Eckpanel()
    {
        setBackground(new Color(200, 200, 200));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void weg(Kante kante)
    {
        removeAll();
        if (Main.spieler().id == Main.ich)
        {
            desc = new JLabel();
            button = new JButton(Nuz.ECKPANEL_NEUE_STRASSE_KNOPF);

            if (kante.spieler == null)
            {
                if (Main.spieler().anzahlStrassen < Main.maxStrassen)
                {
                    if (Main.frueh)
                    {
                        if (!Main.fruehsiedlung)
                        {
                            if (fruehVerfuegbar(kante))
                            {
                                desc.setText(Nuz.ECKPANEL_FRUEHE_STRASSE);
                                button.addActionListener(e ->
                                {
                                    //Main.fruehsiedlung = true;
                                    OnlineInterpreter.strasseBauen(Main.spieler(), kante);
                                    OnlineInterpreter.wuerfel(0);
                                    //weg(kante);
                                    //Spielerpanel.akt();
                                });
                            } else
                            {
                                desc.setText(Nuz.ECKPANEL_FRUEHE_STRASSE_AN_SIEDLUNG);
                                button.setEnabled(false);
                            }
                        } else
                        {
                            desc.setText(Nuz.ECKPANEL_ERST_SIEDLUNG);
                            button.setEnabled(false);
                        }
                    } else
                    {
                        if (kante.nachbar(Main.spieler()))
                        {
                            if (Main.strassenbauinprogress == 0)
                            {
                                if (Main.spieler().inv.bezahlbar(new Inventar(0)))
                                {
                                    desc.setText(Nuz.ECKPANEL_STRASSE_ERLAUBT);
                                    button.addActionListener(e ->
                                    {
                                        //TODO
                                        OnlineInterpreter.strasseBauen(Main.spieler(), kante);
                                        OnlineInterpreter.bezahlen(Main.spieler(), Inventar.strasse);
                                    });
                                } else
                                {
                                    desc.setText(Nuz.NICHT_GENUG_ROHSTOFFE);
                                    button.setEnabled(false);
                                }
                            } else
                            {
                                desc.setText(Nuz.strassenBauEntwicklung(Main.strassenbauinprogress));
                                button.addActionListener(e ->
                                {
                                    kante.spieler = Main.spieler();
                                    Main.spieler().anzahlStrassen++;
                                    Main.strassenbauinprogress--;
                                    kante.aktBild();
                                    weg(kante);
                                    Spielerpanel.akt();
                                });
                            }
                        } else
                        {
                            desc.setText(Nuz.ECKPANEL_NUR_AN_EIGENE_ANBAUEN);
                            button.setEnabled(false);
                        }
                    }
                } else
                {
                    desc.setText(Nuz.ECKPANEL_STRASSEN_VERBRAUCHT);
                    button.setEnabled(false);
                }
            } else
            {
                if (kante.spieler == Main.spieler())
                {
                    desc.setText(Nuz.ECKPANEL_EIGENE_STRASSE);
                } else
                {
                    desc.setText(Nuz.ECKPANEL_FREMDE_STRASSE);
                }
                button.setEnabled(false);
            }
            if (kante.hafen)
            {
                Hafen hafenkante = (Hafen) kante;
                desc.setText(Nuz.hafenKante(hafenkante.typ,desc.getText()));
                JButton handelbutton = new JButton(Nuz.HANDEL);
                handelbutton.setEnabled(kante.nachbar(Main.spieler()));
                handelbutton.addActionListener(e -> new Handel(hafenkante.typ));
                add(handelbutton);
            }

            add(desc);
            add(button);
        }
        revalidate();
    }

    private static boolean fruehVerfuegbar(Kante kante)
    {
        return kante.ecke1 == Main.letzteSiedlung || kante.ecke2 == Main.letzteSiedlung;
    }

    public void leerEcke(Ecke ecke)
    {
        String exString = "";
        removeAll();
        Siedlung siedlung = ecke.getSiedlung();
        if (siedlung != null)
        {
            desc = new JLabel(Nuz.siedlungsTyp(siedlung.art));
        } else
        {
            desc = new JLabel(Nuz.ECKPANEL_LEERE_FLAECHE);
        }
        add(desc);
        if (Main.spieler().id == Main.ich)
        {
            button = new JButton(Nuz.ECKPANEL_NEUE_SIEDLUNG_BAUEN);

            if (siedlung != null && siedlung.spieler == Main.spieler())
            {
                if (Main.spieler().anzahlStaedte < Main.maxStaedte)
                {
                    button.setText(Nuz.ECKPANEL_ZU_STADT_AUSBAUEN);
                    if (siedlung.art != 1)
                    {
                        if (Main.frueh)
                        {
                            exString = Nuz.ECKPANEL_AUSBAUEN_FRUEH;
                            button.setEnabled(false);
                        } else
                        {
                            if (Main.spieler().inv.bezahlbar(new Inventar(2)))
                            {
                                button.addActionListener(e ->
                                        {
                                            OnlineInterpreter.stadtBauen(Main.spieler(), ecke);
                                            OnlineInterpreter.bezahlen(Main.spieler(), Inventar.stadt);
                                            leerEcke(ecke);
                                        }
                                );
                            } else
                            {
                                exString = Nuz.NICHT_GENUG_ROHSTOFFE;
                                button.setEnabled(false);
                            }
                        }
                    } else
                    {
                        exString = Nuz.ECKPANEL_BEREITS_STADT;
                        button.setEnabled(false);
                    }
                } else
                {
                    exString = Nuz.ECKPANEL_STAEDTE_VERBRAUCHT;
                    button.setEnabled(false);
                }
            } else
            {
                if (Main.spieler().anzahlSiedlungen < Main.maxSiedlungen)
                {
                    if (ecke.keinNachbar())
                    {
                        if (siedlung == null || siedlung.spieler == Main.spieler())
                        {
                            if (Main.frueh)
                            {
                                if (Main.fruehsiedlung)
                                {
                                    button.addActionListener(e ->
                                            {
                                                OnlineInterpreter.siedlungBauen(Main.spieler(), ecke);
                                                Main.letzteSiedlung = ecke;
                                                Main.fruehsiedlung = false;
                                                Spielerpanel.akt();
                                                leerEcke(ecke);
                                            }
                                    );
                                } else
                                {
                                    exString = Nuz.ECKPANEL_STRASSE_AN_SIEDLUNG;
                                    button.setEnabled(false);
                                }
                            } else
                            {
                                if (ecke.nachbar(Main.spieler()))
                                {
                                    if (Main.spieler().inv.bezahlbar(new Inventar(1)))
                                    {
                                        button.addActionListener(e ->
                                                {
                                                    OnlineInterpreter.bezahlen(Main.spieler(), Inventar.siedlung);
                                                    OnlineInterpreter.siedlungBauen(Main.spieler(), ecke);
                                                    Spielerpanel.akt();
                                                    leerEcke(ecke);
                                                }
                                        );
                                    } else
                                    {
                                        exString = Nuz.NICHT_GENUG_ROHSTOFFE;
                                        button.setEnabled(false);
                                    }
                                } else
                                {
                                    exString = Nuz.ECKPANEL_SIEDLUNG_NUR_AN_EIGENE;
                                    button.setEnabled(false);
                                }
                            }
                        } else
                        {
                            exString = Nuz.ECKPANEL_FREMDE_SIEDLUNG;
                            button.setEnabled(false);
                        }
                    } else
                    {
                        exString = Nuz.ECKPANEL_ANGRENZENDE_SIEDLUNG;
                        button.setEnabled(false);
                    }
                } else
                {
                    exString = Nuz.ECKPANEL_SIEDLUNGEN_VERBRAUCHT;
                    button.setEnabled(false);
                }
            }
            add(button);
            JLabel ex = new

                    JLabel(exString);

            add(ex);
        }
        revalidate();
    }
}

