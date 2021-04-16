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
    public class VoluntarDBRepository : IVoluntarRepo
    {
        private static readonly ILog log = LogManager.GetLogger("RepoVoluntar");
        public Voluntar Delete(long id)
        {
            log.InfoFormat("entering delete with value {0}", id);
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Voluntari where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();

            }
            log.InfoFormat("exiting delete");
            return null;
        }

        public IEnumerable<Voluntar> FindAll()
        {
            log.InfoFormat("Entering findAll Voluntari");

            IDbConnection con = DBUtils.getConnection();
            IList<Voluntar> voluntari = new List<Voluntar>();
            Console.WriteLine(con);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Voluntari";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        long id = dataR.GetInt32(0);
                        String Username = dataR.GetString(1);
                        String Passwd = dataR.GetString(2);
                        Voluntar voluntar = new Voluntar(Username, Passwd);
                        voluntar.ID = id;

                        //Console.WriteLine(voluntar);
                        voluntari.Add(voluntar);
                    }
                }
            }

            log.InfoFormat("Exiting findAll voluntari with the result from it");
            return voluntari;

        }

        public Voluntar FindByUsername(string username)
        {
            Console.WriteLine("Find by username1");
            log.InfoFormat("Entering findByUsername with value {0}", username);
            IDbConnection con = DBUtils.getConnection();
            Console.WriteLine("Find by username2");
            Console.WriteLine(con);
            using (var comm = con.CreateCommand())
            {
                Console.WriteLine("Find by username3");
                comm.CommandText = "select * from Voluntari where username=@username";
                Console.WriteLine("Find by username4");
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@username";
                paramId.Value = username;
                comm.Parameters.Add(paramId);
                Console.WriteLine("Find by username5");
                // merge
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        Console.WriteLine("Find by username6");
                        long idVoluntar = dataR.GetInt32(0);
                        String Username = dataR.GetString(1);
                        String Passwd = dataR.GetString(2);
                        Voluntar voluntar = new Voluntar(Username, Passwd);
                        voluntar.ID = idVoluntar;
                        log.InfoFormat("Exiting findByUsername with value {0}", voluntar);
                        return voluntar;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public Voluntar FindOne(long id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection();

            Console.WriteLine(con);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Voluntari where id=@id";

                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);


                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        long idVoluntar = dataR.GetInt32(0);
                        String Username = dataR.GetString(1);
                        String Passwd = dataR.GetString(2);
                        Voluntar voluntar = new Voluntar(Username, Passwd);
                        voluntar.ID = idVoluntar;
                        log.InfoFormat("Exiting findOne with value {0}", voluntar);
                        return voluntar;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public Voluntar Save(Voluntar entity)
        {
            IDbConnection con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "Insert into Voluntari(username,password)  values (@Username,@Password)";
                IDbDataParameter paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@Username";
                paramUsername.Value = entity.Username;
                comm.Parameters.Add(paramUsername);

                IDbDataParameter paramPassword = comm.CreateParameter();
                paramPassword.ParameterName = "@Password";
                paramPassword.Value = entity.Password;
                comm.Parameters.Add(paramPassword);

                var result = comm.ExecuteNonQuery();
              
                if (result == 0)
                    log.InfoFormat("save exited with value {0}", result);
                else
                    log.InfoFormat("save exited with value {0}", result);

            }


            return null;
        }

        public Voluntar Update(Voluntar entity)
        {
            throw new NotImplementedException();
        }
    }
}
