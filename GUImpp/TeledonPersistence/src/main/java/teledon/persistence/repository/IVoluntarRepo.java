package teledon.persistence.repository;

import teledon.model.Voluntar;

public interface IVoluntarRepo extends IRepository<Long, Voluntar> {

    public abstract Voluntar findByUsername(String username);

}
