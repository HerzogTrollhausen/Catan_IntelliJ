class Guz {
    private final static double eckpanelXRatio = 0.25;
    private final static int eckPanelXMin = 200;
    private final static double eckpanelYRatio = 0.3;
    private final static int eckpanelYMin = 200;
    private final static int andereXMin = 100;
    private final static double andereXRatio = 0.65;
    private final static int farbeXMin = 100;
    private final static double farbeXRatio = 0.125;
    private final static int feldb = 11;
    private final static int feldh = 6;
    private final static int eckeb = 30;
    private final static int eckeh = 30;
    private final static int kanteb = 20;
    private final static int kanteh = 20;

    private static int x() {
        return Main.fenster.getWidth();
    }

    private static int y() {
        return Main.fenster.getHeight();
    }

    static int eckpanelX() {
        return Math.max((int) (eckpanelXRatio * x()), eckPanelXMin);
    }

    static int eckpanelY() {
        return Math.max((int) (eckpanelYRatio * y()), eckpanelYMin);
    }

    static int andereX() {
        return Math.max((int) (andereXRatio * x() / Main.anzahlSpieler), andereXMin);
    }

    static int andereY() {
        return y() - eckpanelY() - 2 * farbeX();
    }

    static int farbeX() {
        return Math.max((int) (farbeXRatio * x()), farbeXMin);
    }

    static int oX() {
        return (int) (0.5 * x()) - (5 * feldX());
    }

    static int oY() {
        return (int) (0.5 * y()) - (int) (3.5 * feldY());
    }

    static int feldX() {
        return x()/feldb;
    }

    static int feldY() {
        return y()/feldh;
    }

    static int eckeX() {
        return eckeb;
    }

    static int eckeY() {
        return eckeh;
    }

    static int kanteX(){
        return kanteb;
    }

    static int kanteY(){
        return kanteh;
    }
}
