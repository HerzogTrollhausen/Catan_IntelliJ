public class Knoten
{
    Knoten next;
    public String stringInhalt;
    int intInhalt;
    Knoten(String inhalt)
    {
        stringInhalt = inhalt;
    }
    
    Knoten(int inhalt)
    {
        intInhalt = inhalt;
    }
}