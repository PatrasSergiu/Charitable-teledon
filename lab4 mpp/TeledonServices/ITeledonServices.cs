using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using teledon.domain; 

namespace teledon.services
{
    public interface ITeledonServices
    {
        void login(Voluntar voluntar, ITeledonObserver client);
        void logout(Voluntar voluntar, ITeledonObserver client);

        void addDonation(CazCaritabil caz, Donator donator, int suma);

        CazCaritabil[] GetCazuri();

        Donator[] GetAllDonatori();
    }
}
