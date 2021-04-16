using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace teledon.domain
{
    public class Donatie : Entity<long>
    {
        public Donator Don { get; set; }
        
        public CazCaritabil Caz { get; set; }
        public int Suma { get; set; }

        public Donatie(CazCaritabil Caz, Donator Don, int Suma)
        {
            this.Don = Don;
            this.Suma = Suma;
            this.Caz = Caz;
        }
    }
}
