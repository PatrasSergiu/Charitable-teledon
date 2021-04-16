using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace teledon.networking.dto
{
    [Serializable]
    public class CazDTO
    {
        public String nume;
        public int sumaAdunata { get; set; }

        public CazDTO(String nume, int suma)
        {
            this.nume = nume;
            this.sumaAdunata = suma;
        }

        public virtual string Nume
        {
            get
            {
                return nume;
            }
            set
            {
                this.nume = value;
            }
        }
        public virtual int SumaAdunata
        {
            get
            {
                return sumaAdunata;
            }
            set
            {
                this.sumaAdunata = value;
            }
        }




    }
}
