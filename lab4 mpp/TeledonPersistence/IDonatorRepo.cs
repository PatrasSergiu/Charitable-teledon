using teledon.domain;
using System;
using System.Collections.Generic;
using System.Text;

namespace teledon.repository
{
    public interface IDonatorRepo :IRepository<long, Donator>
    {

        Donator FindByNume(string nume, string prenume);

    }
}
