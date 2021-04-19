using teledon.domain;
using System;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using teledon.networking.dto;
using teledon.networking.protocol;
using teledon.services;

namespace teledon.network.server
{
    public class TeledonClientWorker : ITeledonObserver
    {

        private ITeledonServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;


		public TeledonClientWorker(ITeledonServices server, TcpClient connection)
		{
			this.server = server;
			this.connection = connection;
			try
			{

				stream = connection.GetStream();
				formatter = new BinaryFormatter();
				connected = true;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}

		public virtual void run()
		{
			while (connected)
			{
				try
				{
					object request = formatter.Deserialize(stream);
					object response = handleRequest((Request)request);
					if (response != null)
					{
						sendResponse((Response)response);
					}
				}
				catch (Exception e)
				{
					Console.WriteLine(e.StackTrace);
				}

				try
				{
					Thread.Sleep(1000);
				}
				catch (Exception e)
				{
					Console.WriteLine(e.StackTrace);
				}
			}
			try
			{
				stream.Close();
				connection.Close();
			}
			catch (Exception e)
			{
				Console.WriteLine("Error " + e);
			}
		}

		private void sendResponse(Response response)
		{
			Console.WriteLine("sending response " + response);
			formatter.Serialize(stream, response);
			stream.Flush();

		}

		private Response handleRequest(Request request)
		{
			Response response = null;
			if (request is LoginRequest)
			{
				Console.WriteLine("Login request ...");
				LoginRequest logReq = (LoginRequest)request;
				VoluntarDTO udto = logReq.Voluntar;
				Voluntar voluntar = DTOUtils.getFromDTO(udto);
				try
				{
					lock (server)
					{
						Console.WriteLine("ClientWorker login");
						server.login(voluntar, this);
					}
					return new OkResponse();
				}
				catch (TeledonException e)
				{
					connected = false;
					return new ErrorResponse(e.Message);
				}
			}
			//if (request is LogoutRequest)
			//{
			//	Console.WriteLine("Logout request");
			//	LogoutRequest logReq = (LogoutRequest)request;
			//	UserDTO udto = logReq.User;
			//	User user1 = DTOUtils.getFromDTO(udto);
			//	Angajat user = new Angajat(user1.Id, user1.Password);
			//	try
			//	{
			//		lock (server)
			//		{

			//			server.logout(user, this);
			//		}
			//		connected = false;
			//		return new OkResponse();

			//	}
			//	catch (CurseException e)
			//	{
			//		return new ErrorResponse(e.Message);
			//	}

			if (request is GetCazuriRequest)
			{
				Console.WriteLine("GetCazuriRequest Request ...");
				Console.WriteLine("GetCazuri Worker1");
				GetCazuriRequest getReq = (GetCazuriRequest)request;
				Console.WriteLine("GetCazuri Worker2");
				try
				{
					CazCaritabil[] cazuri;
					lock (server)
					{
						Console.WriteLine("GetCazuri Worker3");
						cazuri = server.GetCazuri();
						Console.WriteLine(cazuri);
					}
					CazDTO[] frDTO = DTOUtils.getDTO(cazuri);
					return new GetCazuriResponse(frDTO);
				}
				catch (TeledonException e)
				{
					return new ErrorResponse(e.Message);
				}
			}

			if(request is GetAllDonatoriRequest)
            {
				Console.WriteLine("GetDonatoriRequest Request ...");
				Console.WriteLine("GetDonatori Worker1");
				GetAllDonatoriRequest getReq = (GetAllDonatoriRequest)request;
				Console.WriteLine("GetDonatori Worker2");
				try
                {
					Donator[] donatori;
					lock(server)
                    {
						Console.WriteLine("GetAllDonatori Woker3");
						donatori = server.GetAllDonatori();
						Console.WriteLine("GetAllDonatori worker4");
                    }
					DonatorDTO[] cdto = DTOUtils.getDTO(donatori);
					return new GetAllDonatoriResponse(cdto);
                }
				catch(TeledonException e)
                {
					return new ErrorResponse(e.Message);
                }
			}


			return response;
		}
		public void newDonationAdded(Donatie donatie)
        {
            throw new NotImplementedException();
        }
    }
}
