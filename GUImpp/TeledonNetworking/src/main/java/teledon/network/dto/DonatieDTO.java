package teledon.network.dto;

import teledon.model.CazCaritabil;
import teledon.model.Donator;

import java.io.Serializable;

public class DonatieDTO implements Serializable {
    public Donator donator;
    public CazCaritabil caz;
    public int suma;

    @Override
    public String toString() {
        return "teledon.model.Donatie{ donator=" + donator +
                ", caz=" + caz +
                ", suma=" + suma +
                '}';
    }

    public DonatieDTO(Donator donator, CazCaritabil caz, int suma) {
        this.donator = donator;
        this.suma = suma;
        this.caz = caz;
    }
    public DonatieDTO() {}
    public CazCaritabil getCaz() {
        return this.caz;
    }

    public Donator getDonator() {
        return donator;
    }

    public void setDonator(Donator donator) {
        this.donator = donator;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }
}
