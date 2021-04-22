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
	public class GetAllDonatoriResponse : Response
    {
		private DonatorDTO[] donatori;
		public GetAllDonatoriResponse(DonatorDTO[] d)
        {
			this.donatori = d;
        }
		public virtual DonatorDTO[] Donatori
        {
            get
            {
				return donatori;
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

	public interface UpdateResponse : Response
	{
	}


	[Serializable] 
	public class newDonationResponse: Response
    {

    }

	[Serializable]
	public class updateDonationResponse : UpdateResponse
	{
		private DonatieDTO dto;

		public updateDonationResponse(DonatieDTO dto)
		{
			this.dto = dto;
		}

		public virtual DonatieDTO Donatie
		{
			get
			{
				return dto;
			}
		}
	}

}