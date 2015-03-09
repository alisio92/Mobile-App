package be.howest.nmct.ScoresStudenten;

/**
 * Created by alisio on 12/02/2015.
 */
public class ModulePunt {
    private String modulenaam;
    private int aantalStudiepunten = 6;
    private double score;

    public String getModulenaam() {
        return modulenaam;
    }

    public int getAantalStudiepunten() {
        return aantalStudiepunten;
    }

    public double getScore() {
        return score;
    }

    public void setAantalStudiepunten(int aantalStudiepunten) {
        this.aantalStudiepunten = aantalStudiepunten;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setModulenaam(String modulenaam) {
        this.modulenaam = modulenaam;
    }

    public ModulePunt(String modulenaam, int aantalStudiepunten, double score) {
        this.modulenaam = modulenaam;
        this.aantalStudiepunten = aantalStudiepunten;
        this.score = score;
    }

    public ModulePunt(String modulenaam, double score) {
        this.modulenaam = modulenaam;
        this.score = score;
    }

    @Override
    public String toString() {
        return "ModulePunt{" +
                "modulenaam='" + modulenaam + '\'' +
                ", aantalStudiepunten=" + aantalStudiepunten +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModulePunt)) return false;

        ModulePunt that = (ModulePunt) o;

        if (!modulenaam.equals(that.modulenaam)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return modulenaam.hashCode();
    }
}
