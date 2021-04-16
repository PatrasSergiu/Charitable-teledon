using teledon.domain;
using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace teledon.repository
{
    public class DonatorDBRepository : IDonatorRepo
    {

        private static readonly ILog log = LogManager.GetLogger("RepoDonator");

        public Donator Delete(long id)
        {
            log.InfoFormat("entering delete with value {0}", id);
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Donatori where idDonator=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();

            }
            log.InfoFormat("exiting delete");
            return null;
        }

        public IEnumerable<Donator> FindAll()
        {
            log.InfoFormat("Entering findAll");
            IDbConnection con = DBUtils.getConnection();
            IList<Donator> donatori = new List<Donator>();
            Console.WriteLine(con);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Donatori";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        long id = dataR.GetInt32(0);
                        string AdresaDonator = dataR.GetString(1);
                        string Telefon = dataR.GetString(2);
                        string Nume = dataR.GetString(3);
                        string Prenume = dataR.GetString(4);
                        Donator donator = new Donator(AdresaDonator, Telefon, Nume, Prenume);
                        donator.ID = id;
                        donatori.Add(donator);
                    }
                }
            }
            log.InfoFormat("Exiting findAll with the result from it");
            return donatori; 
        }

        public Donator FindOne(long id)
        {

            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection();
            Console.WriteLine(con);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Donatori where idDonator=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        long idDonator = dataR.GetInt32(0);
                        string Adresa = dataR.GetString(1);
                        string Telefon = dataR.GetString(2);
                        string Nume = dataR.GetString(3);
                        string Prenume = dataR.GetString(4);

                        //   long id = long.Parse(dataR.GetString(3));

                        Donator donator = new Donator(Adresa, Telefon, Nume, Prenume);
                        donator.ID = idDonator;
                        log.InfoFormat("Entering findOne with value {0}", idDonator);
                        return donator;
                    }
                }
            }
            log.InfoFormat("Entering findOne with value {0}", null);
            return null;
        }

        public Donator Save(Donator entity)
        {
            IDbConnection con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "Insert into Donatori(adresa, telefon, nume, prenume)  values (@adresa, @telefon, @nume, @prenume)";
                
                IDbDataParameter paramAdresa = comm.CreateParameter();
                paramAdresa.ParameterName = "@adresa";
                paramAdresa.Value = entity.Adresa;
                comm.Parameters.Add(paramAdresa);

                IDbDataParameter paramTelefon = comm.CreateParameter();
                paramTelefon.ParameterName = "@telefon";
                paramTelefon.Value = entity.Telefon;
                comm.Parameters.Add(paramTelefon);

                IDbDataParameter paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = entity.Nume;
                comm.Parameters.Add(paramNume);

                IDbDataParameter paramPrenume = comm.CreateParameter();
                paramPrenume.ParameterName = "@prenume";
                paramPrenume.Value = entity.Prenume;
                comm.Parameters.Add(paramPrenume);

                var result = comm.ExecuteNonQuery();
              
                if (result == 0)
                    log.InfoFormat("save exited with value {0}", result);
                else
                    log.InfoFormat("save exited with value {0}", result);

            }


            return null;
        }

        public Donator FindByNume(string nume, string prenume)
        {
            log.InfoFormat("Entering findByName with value {0}, {1}", nume, prenume);
            IDbConnection con = DBUtils.getConnection();
            Console.WriteLine(con);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Donatori where nume=@nume and prenume=@prenume";
                IDbDataParameter paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = nume;
                comm.Parameters.Add(paramNume);
                IDbDataParameter paramPrenume = comm.CreateParameter();
                paramPrenume.ParameterName = "@prenume";
                paramPrenume.Value = prenume;
                comm.Parameters.Add(paramPrenume);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        long idDonator = dataR.GetInt32(0);
                        string Adresa = dataR.GetString(1);
                        string Telefon = dataR.GetString(2);
                        string Nume = dataR.GetString(3);
                        string Prenume = dataR.GetString(4);

                        //   long id = long.Parse(dataR.GetString(3));

                        Donator donator = new Donator(Adresa, Telefon, Nume, Prenume);
                        donator.ID = idDonator;
                        if (donator.ID == null)
                            return null;
                        log.InfoFormat("Exiting findByNume with value {0}", idDonator);
                        return donator;
                    }
                }
            }
            log.InfoFormat("Entering findOne with value {0}", null);
            return null;
        }

        public Donator Update(Donator entity)
        {
            throw new NotImplementedException();
        }
    }
}
