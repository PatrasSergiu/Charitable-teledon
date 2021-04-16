using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace teledon.networking.dto
{
    public class DonatorDTO
    {
        public string nume;
        public string prenume;
        public string telefon;
        public string adresa;

		public DonatorDTO(string adresa, string telefon, string nume, string prenume) 
        {
			this.nume = nume;
			this.adresa = adresa;
			this.telefon = telefon;
			this.prenume = prenume;
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
		public virtual string Prenume
		{
			get
			{
				return prenume;
			}
			set
			{
				this.prenume = value;
			}
		}
		public virtual string Telefon
		{
			get
			{
				return telefon;
			}
			set
			{
				this.telefon = value;
			}
		}

		public virtual string Adresa
		{
			get
			{
				return adresa;
			}
			set
			{
				this.adresa = value;
			}
		}

	}
}
