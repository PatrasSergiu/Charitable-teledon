using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace teledon.domain
{
    public class Voluntar : Entity<long>
    {
        public string Username { get; set; }
        public string Password { get; set; }

        public Voluntar(string Username, string Password)
        {
            this.Username = Username;
            this.Password = Password;
        }

    }
}
