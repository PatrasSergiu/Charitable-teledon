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
    public class CazDBRepository : ICazRepo
    {
        private static readonly ILog log = LogManager.GetLogger("RepoCaz");

        public CazCaritabil Delete(long id)
        {
            log.InfoFormat("entering delete cazuri with value {0}", id);
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Cazuri where idCaz=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();

            }
            log.InfoFormat("exiting delete cazuri");
            return null;
        }

        public IEnumerable<CazCaritabil> FindAll()
        {
            log.InfoFormat("Entering findAll Cazuri");

            IDbConnection con = DBUtils.getConnection();
            IList<CazCaritabil> cazuri = new List<CazCaritabil>();
            Console.WriteLine(con);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Cazuri";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        long id = dataR.GetInt32(0);
                        String Descriere = dataR.GetString(1);
                        int sumaAdunata = dataR.GetInt32(2);
                        CazCaritabil caz = new CazCaritabil(Descriere, sumaAdunata);
                        caz.ID = id;

                        //Console.WriteLine(voluntar);
                        cazuri.Add(caz);
                    }
                }
            }

            log.InfoFormat("Exiting findAll cazuri with the result from it");
            return cazuri;
        }

        public IEnumerable<Donator> FindAllDonatori(CazCaritabil caz)
        {
            throw new NotImplementedException();
        }

        public CazCaritabil findByName(string nume)
        {
            log.InfoFormat("Entering findByNume caz with value {0}", nume);
            IDbConnection con = DBUtils.getConnection();

            Console.WriteLine(con);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Cazuri where descriere=@id";

                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = nume;
                comm.Parameters.Add(paramId);


                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        long idCaz = dataR.GetInt32(0);
                        String Descriere = dataR.GetString(1);
                        int sumaAdunata = dataR.GetInt32(2);
                        CazCaritabil caz = new CazCaritabil(Descriere, sumaAdunata);
                        caz.ID = idCaz;
                        log.InfoFormat("Exiting findOne with value {0}", caz);
                        return caz;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public CazCaritabil FindOne(long id)
        {
            log.InfoFormat("Entering findOne caz with value {0}", id);
            IDbConnection con = DBUtils.getConnection();

            Console.WriteLine(con);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Cazuri where idCaz=@id";

                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);


                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        long idCaz = dataR.GetInt32(0);
                        String Descriere = dataR.GetString(1);
                        int sumaAdunata = dataR.GetInt32(2);
                        CazCaritabil caz = new CazCaritabil(Descriere, sumaAdunata);
                        caz.ID = idCaz;
                        log.InfoFormat("Exiting findOne with value {0}", caz);
                        return caz;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public CazCaritabil Save(CazCaritabil entity)
        {
            IDbConnection con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "Insert into Cazuri(descriere, sumaAdunata) values (@descriere,@sumaAdunata)";
                IDbDataParameter paramDescriere = comm.CreateParameter();
                paramDescriere.ParameterName = "@descriere";
                paramDescriere.Value = entity.Nume;
                comm.Parameters.Add(paramDescriere);

                IDbDataParameter paramSuma = comm.CreateParameter();
                paramSuma.ParameterName = "@sumaAdunata";
                paramSuma.Value = entity.SumaAdunata;
                comm.Parameters.Add(paramSuma);

                var result = comm.ExecuteNonQuery();

                if (result == 0)
                    log.InfoFormat("save exited with value {0}", result);
                else
                    log.InfoFormat("save exited with value {0}", result);

            }


            return null;
        }



        public CazCaritabil Update(CazCaritabil entity)
        {
            log.InfoFormat("Entering updateCaz");
            IDbConnection con = DBUtils.getConnection();

            Console.WriteLine(con);
            long id = entity.ID;
            if(FindOne(id) == null)
            {
                throw new ArgumentException("This id does not exist..");
            }

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "UPDATE Cazuri SET sumaAdunata = @suma where idCaz = @id";
                IDbDataParameter paramSuma = comm.CreateParameter();
                paramSuma.ParameterName = "@suma";
                paramSuma.Value = entity.SumaAdunata;
                comm.Parameters.Add(paramSuma);

                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                var result = comm.ExecuteNonQuery();
                log.InfoFormat("Update {0} values", result);

            }
            log.InfoFormat("Updated {0} ", entity);
            return null;
        }
    }
}
