using System;
using teledon.networking.dto;

namespace teledon.networking.protocol
{
	using VoluntarDTO = teledon.networking.dto.VoluntarDTO;


	public interface Request
	{
	}


	[Serializable]
	public class LoginRequest : Request
	{
		private VoluntarDTO voluntar;

		public LoginRequest(VoluntarDTO user)
		{
			this.voluntar = user;
		}

		public virtual VoluntarDTO Voluntar
		{
			get
			{
				return voluntar;
			}
		}
	}

	[Serializable]
	public class GetCazuriRequest : Request
	{
		public GetCazuriRequest()
		{
			
		}

	}

	[Serializable]
	public class GetAllDonatoriRequest: Request
    {
		public GetAllDonatoriRequest()
        {

        }
    }


	[Serializable]
	public class LogoutRequest : Request
	{
		private VoluntarDTO voluntar;

		public LogoutRequest(VoluntarDTO voluntar)
		{
			this.voluntar = voluntar;
		}

		public virtual VoluntarDTO Voluntar
		{
			get
			{
				return voluntar;
			}
		}
	}
}