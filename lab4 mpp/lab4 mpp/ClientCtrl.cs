using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using teledon.domain;
using teledon.services;


namespace teledon.client
{
    public class ClientCtrl : ITeledonObserver
    {
        public event EventHandler<ClientEventArgs> updateEvent;
        private ITeledonServices server;
        private Voluntar loggedVoluntar;

        public ClientCtrl(ITeledonServices server)
        {
            this.server = server;
            loggedVoluntar = null;
        }

        public void login(String username, String pass)
        {
            Voluntar user = new Voluntar(username, pass);
            server.login(user, this);
            Console.WriteLine("Login succeeded ....");
            loggedVoluntar = user;
            Console.WriteLine("Current user {0}", user);
        }

        public CazCaritabil[] GetCazuri()
        {
            Console.WriteLine("GetCazuri CTRL");
            return server.GetCazuri();
        }

        public Donator[] GetDonatori()
        {
            Console.WriteLine("GetDonatori ctrl");
            return server.GetAllDonatori();
        }


        public void makeDonation(CazCaritabil caz, Donator donator, int suma)
        {
            Console.WriteLine("makeDonation CTRL");
            ClientEventArgs userEventArgs = new ClientEventArgs(TeledonUserEvent.updateDonatii, new Donatie(caz, donator, suma));
            server.addDonation(caz, donator, suma);
        }

        public void logout()
        {
            //Console.WriteLine("Ctrl logout");
            //server.logout(currentUser, this);
            //currentUser = null;
        }

        protected virtual void OnUserEvent(ClientEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update event called");

        }
            public void newDonationAdded(Donatie donatie)
        {
            ClientEventArgs userEventArgs = new ClientEventArgs(userEvent: TeledonUserEvent.updateDonatii, donatie);
            OnUserEvent(userEventArgs);
        }
    }
}
