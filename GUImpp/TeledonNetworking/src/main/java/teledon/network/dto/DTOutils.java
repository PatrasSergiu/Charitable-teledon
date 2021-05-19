package teledon.network.dto;

import teledon.model.CazCaritabil;
import teledon.model.Donatie;
import teledon.model.Donator;
import teledon.model.Voluntar;

public class DTOutils {
    public static Voluntar getFromDTO(VoluntarDTO voluntar){
        String id=voluntar.getUsername();
        String pass=voluntar.getPassword();
        return new Voluntar(id, pass);

    }
    public static VoluntarDTO getDTO(Voluntar voluntar){
        String id = voluntar.getUsername();
        String pass = voluntar.getPassword();
        return new VoluntarDTO(id, pass);
    }

    public static Donatie getFromDTO(DonatieDTO dto) {
        int suma = dto.getSuma();
        Donator donator = dto.getDonator();
        CazCaritabil caz = dto.getCaz();
        return new Donatie(donator, caz, suma);
    }
}
