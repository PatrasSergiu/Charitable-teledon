using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using teledon.domain;

namespace teledon.networking.dto
{
    [Serializable]
    public class DonatieDTO
    {
        public DonatorDTO donator;
        public CazDTO caz;
        public int suma;

        public DonatieDTO(CazCaritabil c, Donator d, int s)
        {
            this.caz = DTOUtils.getDTO(c);
            this.donator = DTOUtils.getDTO(d);
            this.suma = s;
        }
        public DonatieDTO(CazDTO c, DonatorDTO d, int s)
        {
            this.caz = c;
            this.donator = d;
            this.suma = s;
        }

        public virtual DonatorDTO Donator
        {
            get
            {
                return donator;
            }
            set
            {
                this.donator = value;
            }
        }

        public virtual CazDTO Caz
        {
            get
            {
                return caz;
            }
            set
            {
                this.caz = value;
            }
        }

        public virtual int Suma
        {
            get
            {
                return suma;
            }
            set
            {
                this.suma = value;
            }
        }

    }
}
