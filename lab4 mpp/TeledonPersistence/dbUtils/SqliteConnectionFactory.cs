using System;
using System.Configuration;
using System.Data;
using System.Data.SQLite;

namespace ConnectionUtils
{
    public class SqliteConnectionFactory : ConnectionFactory
        {
            public override IDbConnection createConnection()
            {
            // Windows Sqlite Connection, fisierul .db ar trebuie sa fie in directorul debug/bin
            String props = "Data Source=C:\\Users\\Patras Sergiu\\source\\repos\\lab4 mpp\\TeledonPersistence\\bin\\Debug\\teledon.db; Version=3;";
                return new SQLiteConnection(props);
            }
        }
}
