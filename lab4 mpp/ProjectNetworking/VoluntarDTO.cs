using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace teledon.networking.dto
{
	[Serializable]
	public class VoluntarDTO
    {
        private string username;
        private string password;

        public VoluntarDTO(string u, string p)
        {
            this.username = u;
            this.password = p;
        }
		public virtual string Username
		{
			get
			{
				return username;
			}
			set
			{
				this.username = value;
			}
		}


		public virtual string Passwd
		{
			get
			{
				return this.password;
			}
		}
	}


}
