using teledon.repository;
using ServerTemplate;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using teledon.services;
using teledon.network.server;

namespace ProjectServer
{
    class StartServer
    {
        static void Main(string[] args)
        {

            // IUserRepository userRepo = new UserRepositoryMock();
            //  IUserRepository userRepo=new UserRepositoryDb();
            // IMessageRepository messageRepository=new MessageRepositoryDb();
            //IUserRepository userRepo = new UserRepositoryMock();
            //IMessageRepository messageRepository = new MessageRepositoryMock();
            //IChatServices serviceImpl = new ChatServerImpl(userRepo, messageRepository);

            IVoluntarRepo voluntarRepo = new VoluntarDBRepository();
            IDonatorRepo donatorRepo = new DonatorDBRepository();
            ICazRepo cazRepo = new CazDBRepository();
            IDonatieRepo donatieRepo = new DonatieDBRepository();

            ITeledonServices serviceImpl = new Service(voluntarRepo, cazRepo, donatorRepo, donatieRepo);


            // IChatServer serviceImpl = new ChatServerImpl();
            SerialChatServer server = new SerialChatServer("127.0.0.1", 55555, serviceImpl);
            server.Start();
            Console.WriteLine("Server started ...");
            //Console.WriteLine("Press <enter> to exit...");
            Console.ReadLine();

        }
    }

    public class SerialChatServer : ConcurrentServer
    {
        private ITeledonServices server;
        private TeledonClientWorker worker;
        public SerialChatServer(string host, int port, ITeledonServices server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialChatServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new TeledonClientWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }

}

