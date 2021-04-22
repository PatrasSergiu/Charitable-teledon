using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using teledon.domain;

namespace teledon.networking.dto
{
    using Voluntar = teledon.domain.Voluntar;

    class DTOUtils
    {

        public static Voluntar getFromDTO(VoluntarDTO dto)
        {
            return new Voluntar(dto.Username, dto.Passwd);
        }

        public static VoluntarDTO getDTO(Voluntar voluntar)
        {
            return new VoluntarDTO(voluntar.Username, voluntar.Password);
        }
        public static CazCaritabil getFromDTO(CazDTO dto)
        {
            return new CazCaritabil(dto.Nume, dto.SumaAdunata);
        }
        public static CazDTO getDTO(CazCaritabil caz)
        {
            return new CazDTO(caz.Nume, caz.SumaAdunata);
        }

        public static Donator getFromDTO(DonatorDTO dto)
        {
            return new Donator(dto.Adresa, dto.Telefon, dto.Nume, dto.Prenume);
        }

        public static Donatie getFromDTO(DonatieDTO dto)
        {
            return new Donatie(getFromDTO(dto.Caz), getFromDTO(dto.Donator), dto.Suma);
        }
        public static DonatieDTO getDTO(Donatie d)
        {
            return new DonatieDTO(d.Caz, d.Don, d.Suma);
        }

        public static DonatorDTO getDTO(Donator donator)
        {
            return new DonatorDTO(donator.Adresa, donator.Telefon, donator.Nume, donator.Prenume);
        }

        public static Donator[] getFromDTO(DonatorDTO[] cdto)
        {
            Donator[] donatori = new Donator[cdto.Length];
            for (int i = 0; i < cdto.Length; i++)
            {
                donatori[i] = getFromDTO(cdto[i]);
            }
            return donatori;
        }

        public static DonatorDTO[] getDTO(Donator[] donatori)
        {
            DonatorDTO[] cdto = new DonatorDTO[donatori.Length];
            for (int i = 0; i < donatori.Length; i++)
            {
                cdto[i] = getDTO(donatori[i]);
            }
            return cdto;
        }

        public static CazCaritabil[] getFromDTO(CazDTO[] cdto)
        {
            CazCaritabil[] cazuri = new CazCaritabil[cdto.Length];
            for (int i = 0; i < cdto.Length; i++)
            {
                cazuri[i] = getFromDTO(cdto[i]);
            }
            return cazuri;
        }

        public static CazDTO[] getDTO(CazCaritabil[] cazuri)
        {
            CazDTO[] cdto = new CazDTO[cazuri.Length];
            for (int i = 0; i < cazuri.Length; i++)
            {
                cdto[i] = getDTO(cazuri[i]);
            }
            return cdto;
        }


    }
}
