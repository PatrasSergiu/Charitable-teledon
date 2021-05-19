package teledon.model;

public class CazCaritabil extends Entity<Long> {


    public String nume;
    public int SumaAdunata;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getSumaAdunata() {
        return SumaAdunata;
    }

    public void setSumaAdunata(int sumaAdunata) {
        SumaAdunata = sumaAdunata;
    }

    public CazCaritabil(String nume, int sumaAdunata) {
        this.nume = nume;
        SumaAdunata = sumaAdunata;
    }
    public CazCaritabil(String nume) {
        this.nume = nume;
        SumaAdunata = 0;
    }

    public CazCaritabil() {

    }

    @Override
    public String toString() {
        return this.nume + " " + this.SumaAdunata;
    }

}
