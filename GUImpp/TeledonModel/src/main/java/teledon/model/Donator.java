package teledon.model;

public class Donator extends Entity<Long>{

    public String nume;
    public String telefon;
    public String prenume;
    public String adresa;

    public Donator(String adresa, String telefon, String nume, String prenume){
        this.adresa = adresa;
        this.telefon = telefon;
        this.nume = nume;
        this.prenume = prenume;
    }

    public Donator() {

    }


    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
    public String getNumeComplet() {
        return getNume() + " " + getPrenume();
    }

    @Override
    public String toString() {
        return this.nume + " " + this.prenume + ", " + this.adresa + ", " + this.telefon;
    }

    public String getNume() {
        return this.nume;
    }
    public String getPrenume() {
        return this.prenume;
    }
    public String getAdresa() {
        return this.adresa;
    }
    public String getTelefon() {
        return this.telefon;
    }
}
