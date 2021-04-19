using teledon.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using teledon.services;
using System.Net.Sockets;
using System.Runtime.Serialization;
using teledon.networking.protocol;
using System.Threading;
using System.Runtime.Serialization.Formatters.Binary;
using teledon.networking.dto;

namespace teledon.network.server
{
    public class TeledonServerObjectProxy : ITeledonServices
    {

		private string host;
		private int port;

		private ITeledonObserver client;

		private NetworkStream stream;

		private IFormatter formatter;
		private TcpClient connection;

		private Queue<Response> responses;
		private volatile bool finished;
		private EventWaitHandle _waitHandle;

		public TeledonServerObjectProxy(string host, int port)
		{
			this.host = host;
			this.port = port;
			responses = new Queue<Response>();
		}

		//public CazCaritabil[] GetCazuri()
		//{
		//	server.
		//}

		public void login(Voluntar voluntar, ITeledonObserver client)
        {
			Console.WriteLine("Proxy login...");
			initializeConnection();
			VoluntarDTO udto = DTOUtils.getDTO(voluntar);
			sendRequest(new LoginRequest(udto));
			Response response = readResponse();
			if (response is OkResponse)
			{
				this.client = client;
				return;
			}
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				closeConnection();
				throw new TeledonException(err.Message);
			}
		}

        public void logout(Voluntar voluntar, ITeledonObserver client)
        {
            throw new NotImplementedException();
        }

		private void closeConnection()
		{
			finished = true;
			try
			{
				stream.Close();
				//output.close();
				connection.Close();
				_waitHandle.Close();
				client = null;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}

		}

		private void sendRequest(Request request)
		{
			try
			{
				formatter.Serialize(stream, request);
				stream.Flush();
			}
			catch (Exception e)
			{
				throw new TeledonException("Error sending object " + e);
			}

		}

		private Response readResponse()
		{
			Response response = null;
			try
			{
				_waitHandle.WaitOne();
				lock (responses)
				{
					//Monitor.Wait(responses); 
					response = responses.Dequeue();

				}


			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
			return response;
		}
		private void initializeConnection()
		{
			try
			{
				connection = new TcpClient(host, port);
				stream = connection.GetStream();
				formatter = new BinaryFormatter();
				finished = false;
				_waitHandle = new AutoResetEvent(false);
				startReader();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}
		private void startReader()
		{
			Thread tw = new Thread(run);
			tw.Start();
		}

		private void handleUpdate(UpdateResponse update)
		{
			//if (update is NewInscriereResponse)
			//{

			//	NewInscriereResponse req = (NewInscriereResponse)update;
			//	Pilot pilot = new Pilot(req.pilot.nume, req.pilot.NumeEchipa);

			//	try
			//	{
			//		client.updateInscrieri(pilot, req.pilot.username);
			//	}
			//	catch (CurseException e)
			//	{
			//		Console.WriteLine(e.StackTrace);
			//	}
			//}
		}

		public virtual void run()
		{
			while (!finished)
			{
				try
				{
					object response = formatter.Deserialize(stream);
					Console.WriteLine("response received " + response);
					if (response is UpdateResponse)
					{
						handleUpdate((UpdateResponse)response);
					}
					else
					{

						lock (responses)
						{


							responses.Enqueue((Response)response);

						}
						_waitHandle.Set();
					}
				}
				catch (Exception e)
				{
					Console.WriteLine("Reading error " + e);
				}

			}
		}

        public CazCaritabil[] GetCazuri()
        {
			Console.WriteLine("Proxy1");
			sendRequest(new GetCazuriRequest());
			Console.WriteLine("Proxy2");
			Response response = readResponse();
			Console.WriteLine("Proxy3");
			if (response is ErrorResponse)
            {
				ErrorResponse err = (ErrorResponse)response;
				throw new TeledonException(err.Message);
			}
			GetCazuriResponse resp = (GetCazuriResponse)response;
			Console.WriteLine("Proxy4");
			CazDTO[] cazuridto = resp.Cazuri;
			CazCaritabil[] cazuri = DTOUtils.getFromDTO(cazuridto);
			Console.WriteLine("Proxy5");
			return cazuri;
		}

        public Donator[] GetAllDonatori()
        {
			Console.WriteLine("Proxy1");
			sendRequest(new GetAllDonatoriRequest());
			Console.WriteLine("Proxy2");
			Response response = readResponse();
			Console.WriteLine("Proxy3");
			if (response is ErrorResponse)
			{
				ErrorResponse err = (ErrorResponse)response;
				throw new TeledonException(err.Message);
			}
			GetAllDonatoriResponse resp = (GetAllDonatoriResponse)response;
			Console.WriteLine("Proxy4");
			DonatorDTO[] cazuridto = resp.Donatori;
			Donator[] cazuri = DTOUtils.getFromDTO(cazuridto);
			Console.WriteLine("Proxy5");
			return cazuri;
		}
    }




}
