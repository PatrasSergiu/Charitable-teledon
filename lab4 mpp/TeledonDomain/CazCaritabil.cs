using System;
using System.Collections.Generic;
using System.Text;

namespace teledon.domain
{
    public class CazCaritabil : Entity<long>
    {

        public String Nume { get; set; }
        public int SumaAdunata { get; set; }
       
        public CazCaritabil(String nume)
        {
            this.Nume = nume;
            SumaAdunata = 0;
        }
        public CazCaritabil(String nume, int suma)
        {
            this.Nume = nume;
            this.SumaAdunata = suma;
        }
        public void AdaugaDonatie(int Suma)
        {
            this.SumaAdunata += Suma;
        }

        public override string ToString()
        {
            return $"{Nume} , {SumaAdunata}";
        }

    }
}
