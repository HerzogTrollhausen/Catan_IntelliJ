public class Stuff
{
    public static void main(String[] args)
    {
        String[] s = "Hallo\"Hallo".split("\"");
        System.out.println(s[0]);
        System.out.println(s[1]);
    }

    static boolean a()
    {
        System.out.println("a");
        return true;
    }

    static boolean b()
    {
        System.out.println("b");
        return false;
    }

    public void printModulo(int x, int y)
    {
        if (y == 0)
        {
            System.out.println("Division durch 0 ist nicht möglich.");
        } else if (y > 10)
        {
            System.out.println("Division durch " + y + " ist nicht möglich, da der Divisor größer als 10 ist");
        } else if (y < 0)
        {
            System.out.println("Es werden nur nicht-negative Zahlen akzeptiert.");
        } else
        {
            System.out.println(intToString(x % y));
        }
    }


    public String intToString(int x)
    {
        switch (x)
        {
            case 0:
                return "null";
            case 1:
                return "eins";
            case 2:
                return "zwei";
            case 3:
                return "drei";
            case 4:
                return "vier";
            case 5:
                return "fünf";
            case 6:
                return "sechs";
            case 7:
                return "sieben";
            case 8:
                return "acht";
            case 9:
                return "neun";
            default:
                return "Irgendwas lief schief: x ist " + x;
        }
    }
}


