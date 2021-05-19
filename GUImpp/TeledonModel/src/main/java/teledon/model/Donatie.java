package teledon.model;

public class Donatie extends Entity<Long> {

    public Donator donator;
    public CazCaritabil caz;
    public int suma;

    @Override
    public String toString() {
        return "teledon.model.Donatie{" +
                "idDonatie="  +
                ", donator=" + donator +
                ", caz=" + caz +
                ", suma=" + suma +
                '}';
    }

    public Donatie(Donator donator, CazCaritabil caz, int suma) {
        this.donator = donator;
        this.suma = suma;
        this.caz = caz;
    }
    public Donatie() {}
    public CazCaritabil getCaz() {
        return this.caz;
    }

    public Donator getDonator() {
        return donator;
    }

    public void setDonator(Donator donator) {
        this.donator = donator;
    }

    public void setCaz(CazCaritabil c) {
        this.caz = c;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }
}
