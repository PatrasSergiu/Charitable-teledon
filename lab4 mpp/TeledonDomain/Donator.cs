using System;
using System.Collections.Generic;
using System.Text;

namespace teledon.domain
{
    public class Donator : Entity<long>
    {

        public string Nume { get; set; }
        public string Prenume { get; set; }
        public string Telefon { get; set; }
        public string Adresa { get; set; }

        public override string ToString()
        {
            return Nume + " " + Prenume + ", " + Adresa.ToString() + ", " + Telefon;
        }

        public string getNumeComplet()
        {
            return this.Nume+this.Prenume;
        }

        public Donator(string Adresa, string Telefon, string Nume, string Prenume)
        {
            this.Nume = Nume;
            this.Prenume = Prenume;
            this.Adresa = Adresa;
            this.Telefon = Telefon;
        }
    }
}
