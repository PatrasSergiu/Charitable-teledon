using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace teledon.client
{
    public enum TeledonUserEvent
    {
        updateDonatii
    };
    public class ClientEventArgs : EventArgs
    {
        private readonly TeledonUserEvent teledonEvent;
        private readonly Object data;

        public ClientEventArgs(TeledonUserEvent userEvent, object data)
        {
            this.teledonEvent = userEvent;
            this.data = data;
        }

        public TeledonUserEvent UserEventType
        {
            get { return teledonEvent; }
        }

        public object Data
        {
            get { return data; }
        }
    }
}
