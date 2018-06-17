public class Stuff
{
    public static void main(String[] args)
    {
        int[] a = {1, 2, 3, 4};
        int[] b = new int[4];
        System.out.println(a[1]);//2
        b = a.clone();
        System.out.println(b[1]);
        b[1] = 5;
        System.out.println(a[1]);
        System.out.println(b[1]);
    }
}

