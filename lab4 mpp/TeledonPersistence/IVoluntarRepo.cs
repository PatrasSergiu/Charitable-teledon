using teledon.domain;
using System;
using System.Collections.Generic;
using System.Text;

namespace teledon.repository
{
    public interface IVoluntarRepo : IRepository<long, Voluntar>
    {
       Voluntar FindByUsername(string username);
    }
}
