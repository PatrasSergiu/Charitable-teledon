package teledon.persistence.repository;

import teledon.model.CazCaritabil;
import teledon.model.Donator;

public interface ICazRepo extends IRepository<Long, CazCaritabil> {

    Iterable<Donator> findAllDonatori(CazCaritabil caz);

}
