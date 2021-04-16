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
    public class DonatieDBRepository : IDonatieRepo
    {
        private static readonly ILog log = LogManager.GetLogger("RepoDonatie");
        private DonatorDBRepository donatorRepo = new DonatorDBRepository();
        private CazDBRepository cazRepo = new CazDBRepository();


        public Donatie Delete(long id)
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

        public IEnumerable<Donatie> FindAll()
        {
            throw new NotImplementedException();
        }

        public Donatie FindOne(long id)
        {
            throw new NotImplementedException();
        }

        public Donatie Save(Donatie entity)
        {
            IDbConnection con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "Insert into Donatii(idDonator, idCaz, suma)  values (@idDonator, @idCaz, @suma)";

                IDbDataParameter paramIdDonator = comm.CreateParameter();
                paramIdDonator.ParameterName = "@idDonator";
                paramIdDonator.Value = entity.Don.ID;
                comm.Parameters.Add(paramIdDonator);

                IDbDataParameter paramIdCaz = comm.CreateParameter();
                paramIdCaz.ParameterName = "@idCaz";
                paramIdCaz.Value = entity.Caz.ID;
                comm.Parameters.Add(paramIdCaz);

                IDbDataParameter paramSuma = comm.CreateParameter();
                paramSuma.ParameterName = "@suma";
                paramSuma.Value = entity.Suma;
                comm.Parameters.Add(paramSuma);


                var result = comm.ExecuteNonQuery();

                if (result == 0)
                    log.InfoFormat("save exited with value {0}", result);
                else
                    log.InfoFormat("save exited with value {0}", result);

            }


            return null;
        }

        public Donatie Update(Donatie entity)
        {
            throw new NotImplementedException();
        }
    }
}
