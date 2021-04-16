using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace teledon.services
{
    public class TeledonException : Exception
    {
        public TeledonException() : base() { }

        public TeledonException(String msg) : base(msg) { }

        public TeledonException(String msg, Exception ex) : base(msg, ex) { }

    }
}
