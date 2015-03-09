package be.howest.nmct.stopafstand;

/**
 * Created by alisio on 12/02/2015.
 */
public class StopAfstandinfo {

    public static enum Wegtype{
        WEGDEK_DROOG(8), WEGDEK_NAT(5);

        private int afstand;

        Wegtype(int afstand) {
            this.afstand = afstand;
        }
    }

    private int snelheid;
    private float reactiesnelheid;
    private float stopafstand;
    private static float ms = (float)3.6;
    private Wegtype wegtype;

    public int getSnelheid() {
        return snelheid;
    }

    public float getReactiesnelheid() {
        return reactiesnelheid;
    }

    public float getStopafstand() {
        stopafstand = ((snelheid / ms) * reactiesnelheid) + (((snelheid / ms) * (snelheid / ms)) / (2 * wegtype.afstand));
        return stopafstand;
    }

    public void setSnelheid(int snelheid) {
        this.snelheid = snelheid;
    }

    public void setReactiesnelheid(float reactiesnelheid) {
        this.reactiesnelheid = reactiesnelheid;
    }

    public void setStopafstand(float stopafstand) {
        this.stopafstand = stopafstand;
    }

    public StopAfstandinfo(int snelheid, float reactiesnelheid, String wegtype) {
        this.snelheid = snelheid;
        this.reactiesnelheid = reactiesnelheid;
        if(wegtype.equals("NAT")) this.wegtype = Wegtype.WEGDEK_NAT;
        if(wegtype.equals("DROOG")) this.wegtype = Wegtype.WEGDEK_DROOG;
    }

    public StopAfstandinfo(int snelheid, float reactiesnelheid, Wegtype wegtype) {
        this.snelheid = snelheid;
        this.reactiesnelheid = reactiesnelheid;
        this.wegtype = wegtype;
    }

    @Override
    public String toString() {
        return "StopAfstandinfo{" +
                "snelheid=" + snelheid +
                ", reactiesnelheid=" + reactiesnelheid +
                ", stopafstand=" + stopafstand +
                ", wegtype=" + wegtype +
                '}';
    }
}
