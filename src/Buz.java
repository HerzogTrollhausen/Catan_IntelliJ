import java.awt.*;
import java.io.IOException;

public class Buz
{
    public static Image ECKE_LEER;
    public static Image KANTE_LEER;
    public static Image FELD_HOLZ;
    public static Image FELD_HOLZ_HELL;
    public static Image FELD_LEHM;
    public static Image FELD_LEHM_HELL;
    public static Image FELD_WEIZEN;
    public static Image FELD_WEIZEN_HELL;
    public static Image FELD_SCHAF;
    public static Image FELD_SCHAF_HELL;
    public static Image FELD_ERZ;
    public static Image FELD_ERZ_HELL;
    public static Image FELD_MEER;
    public static Image FELD_MEER_HELL;
    public static Image FELD_WUESTE;
    public static Image FELD_WUESTE_HELL;

    public static void init() throws IOException
    {
        ECKE_LEER = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Siedlungen/leer.gif"), Bildschirm.eckeb, Bildschirm.eckeh, false);
        KANTE_LEER = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Kanten/Weg.gif"), Bildschirm.kanteb, Bildschirm.kanteh, false);
        FELD_HOLZ = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Holz-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        FELD_HOLZ_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Holz_hell-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        FELD_LEHM = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Lehm-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        FELD_LEHM_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Lehm_hell-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        FELD_WEIZEN = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Weizen-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        FELD_WEIZEN_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Weizen_hell-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        FELD_SCHAF = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Schaf-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        FELD_SCHAF_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Schaf_hell-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        FELD_ERZ = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Erz-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        FELD_ERZ_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Erz_hell-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        //FELD_MEER = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Meer-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        //FELD_MEER_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Meer_hell-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        FELD_WUESTE = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Wueste-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
        FELD_WUESTE_HELL = FileManager.createResizedCopy(FileManager.bildLaden("Bilder/Felder/Wueste_hell-hex.gif"), Bildschirm.feldb, Bildschirm.feldh, false);
    }
}
