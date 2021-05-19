package teledon.persistence.repository;

import teledon.model.Donator;

public interface IDonatorRepo extends IRepository<Long, Donator>{

    public Donator findByNume(String nume, String prenume);

}
