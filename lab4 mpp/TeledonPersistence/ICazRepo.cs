using teledon.domain;
using System;
using System.Collections.Generic;
using System.Text;

namespace teledon.repository
{
    public interface ICazRepo : IRepository<long, CazCaritabil>
    {

        IEnumerable<Donator> FindAllDonatori(CazCaritabil caz);

        CazCaritabil findByName(string nume);

    }
}
