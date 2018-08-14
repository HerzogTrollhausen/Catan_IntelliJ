import java.awt.*;
import java.io.IOException;

class Buz
{
    static Image ECKE_LEER;
    static Image KANTE_LEER;
    static Image FELD_HOLZ;
    static Image FELD_HOLZ_HELL;
    static Image FELD_LEHM;
    static Image FELD_LEHM_HELL;
    static Image FELD_WEIZEN;
    static Image FELD_WEIZEN_HELL;
    static Image FELD_SCHAF;
    static Image FELD_SCHAF_HELL;
    static Image FELD_ERZ;
    static Image FELD_ERZ_HELL;
    static Image FELD_MEER;
    static Image FELD_MEER_HELL;
    static Image FELD_WUESTE;
    static Image FELD_WUESTE_HELL;
    static Image KREIS;
    static Image KREIS_BANDIT;

    static void init() throws IOException
    {
        ECKE_LEER = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Siedlungen/leer.gif"), Guz.eckeX(), Guz.eckeY(), false);
        KANTE_LEER = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Kanten/Weg.gif"), Guz.kanteX(), Guz.kanteY(), false);
        FELD_HOLZ = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Holz-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        FELD_HOLZ_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Holz_hell-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        FELD_LEHM = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Lehm-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        FELD_LEHM_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Lehm_hell-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        FELD_WEIZEN = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Weizen-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        FELD_WEIZEN_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Weizen_hell-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        FELD_SCHAF = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Schaf-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        FELD_SCHAF_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Schaf_hell-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        FELD_ERZ = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Erz-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        FELD_ERZ_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Erz_hell-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        //FELD_MEER = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Meer-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        //FELD_MEER_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Meer_hell-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        FELD_WUESTE = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Wueste-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        FELD_WUESTE_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Wueste_hell-hex.gif"), Guz.feldX(), Guz.feldY(), false);
        KREIS = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Kreis.gif"),
                (int) (Guz.feldX() / Bildschirm.plattenr), (int) (Guz.feldY() / Bildschirm.plattenr), false);
        KREIS_BANDIT = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Kreis_bandit.gif"),
                (int)(Guz.feldX()/Bildschirm.plattenr), (int)(Guz.feldY()/Bildschirm.plattenr), false);
    }
}
