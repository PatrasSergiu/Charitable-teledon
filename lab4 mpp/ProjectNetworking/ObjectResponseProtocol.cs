using System;
using teledon.networking.dto;

namespace teledon.networking.protocol
{
	using VoluntarDTO = teledon.networking.dto.VoluntarDTO;

	public interface Response
	{
	}

	[Serializable]
	public class OkResponse : Response
	{

	}

	[Serializable]
	public class ErrorResponse : Response
	{
		private string message;

		public ErrorResponse(string message)
		{
			this.message = message;
		}

		public virtual string Message
		{
			get
			{
				return message;
			}
		}
	}

	[Serializable]
	public class GetCazuriResponse : Response
	{
		private CazDTO[] cazuri;

		public GetCazuriResponse(CazDTO[] cazuri)
		{
			this.cazuri = cazuri;
		}

		public virtual CazDTO[] Cazuri
		{
			get
			{
				return cazuri;
			}
		}
	}

	[Serializable]
	public class GetDonatoriResponse : Response
	{
		private DonatorDTO[] donatori;

		public GetDonatoriResponse(DonatorDTO[] donatori)
		{
			this.donatori = donatori;
		}

		public virtual DonatorDTO[] Donatori
		{
			get
			{
				return donatori;
			}
		}
	}

	public interface UpdateResponse : Response
	{
	}

}