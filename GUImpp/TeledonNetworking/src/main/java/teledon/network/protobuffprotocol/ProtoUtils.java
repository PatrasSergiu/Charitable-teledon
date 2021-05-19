package teledon.network.protobuffprotocol;

import teledon.model.CazCaritabil;
import teledon.model.Donatie;
import teledon.model.Donator;
import teledon.model.Voluntar;

import java.time.LocalDateTime;
import java.util.List;

public class ProtoUtils {

    //LOGIN REQ
    public static TeledonProtobufs.TeledonRequest createLoginRequest(Voluntar voluntar)
    {
        TeledonProtobufs.Voluntar v = TeledonProtobufs.Voluntar.newBuilder()
                .setUsername(voluntar.getUsername())
                .setPassword(voluntar.getPassword()).build();
        TeledonProtobufs.TeledonRequest request = TeledonProtobufs.TeledonRequest.newBuilder().setType(TeledonProtobufs.TeledonRequest.Type.LOGIN)
                .setVoluntar(v).build();
        return request;
    }

    public static String getError(TeledonProtobufs.TeledonResponse response)
    {
        String errorMessage = response.getError();
        return errorMessage;
    }

    public static Voluntar getVoluntar(TeledonProtobufs.TeledonRequest request)
    {
        Voluntar voluntar = new Voluntar();
        voluntar.setUsername(request.getVoluntar().getUsername());
        voluntar.setPassword(request.getVoluntar().getPassword());
        return voluntar;
    }

    public static TeledonProtobufs.TeledonResponse createOkResponse()
    {
        TeledonProtobufs.TeledonResponse response = TeledonProtobufs.TeledonResponse.newBuilder()
                .setType(TeledonProtobufs.TeledonResponse.Type.OK).build();
        return response;

    }

    public static TeledonProtobufs.TeledonResponse createErrorResponse(String text)
    {
        TeledonProtobufs.TeledonResponse response=TeledonProtobufs.TeledonResponse.newBuilder()
                .setType(TeledonProtobufs.TeledonResponse.Type.ERROR).setError(text).build();
        return response;
    }

    public static TeledonProtobufs.TeledonRequest createGetAllDonatoriRequest()
    {
        TeledonProtobufs.TeledonRequest request=TeledonProtobufs.TeledonRequest.newBuilder()
                .setType(TeledonProtobufs.TeledonRequest.Type.GET_ALL_DONATORI)
                .build();
        return request;
    }

    public static TeledonProtobufs.TeledonRequest createGetAllCazuriRequest()
    {
        TeledonProtobufs.TeledonRequest request = TeledonProtobufs.TeledonRequest.newBuilder()
                .setType(TeledonProtobufs.TeledonRequest.Type.GET_ALL_CAZURI)
                .build();
        return request;
    }

    public static TeledonProtobufs.TeledonRequest createNewDonationRequest(Donatie donatie) {
        Donator dto = donatie.getDonator();
        System.out.println(donatie);
            TeledonProtobufs.Donator donator = TeledonProtobufs.Donator.newBuilder()
                    .setAdresa(dto.getAdresa())
                    .setTelefon(dto.getTelefon())
                    .setNume(dto.getNume())
                    .setPrenume(dto.getPrenume())
                    .build();
            CazCaritabil dtoCaz = donatie.getCaz();
            TeledonProtobufs.CazCaritabil caz = TeledonProtobufs.CazCaritabil.newBuilder()
                    .setNume(dtoCaz.getNume())
                    .setSumaAdunata(dtoCaz.getSumaAdunata())
                    .build();

            TeledonProtobufs.Donatie donatie1 = TeledonProtobufs.Donatie.newBuilder()
                    .setCaz(caz)
                    .setDonator(donator)
                    .setSuma(donatie.getSuma())
                    .build();

            TeledonProtobufs.TeledonRequest request = TeledonProtobufs.TeledonRequest.newBuilder()
                    .setType(TeledonProtobufs.TeledonRequest.Type.NEW_DONATIE)
                    .setDonatie(donatie1)
                    .build();
            return request;

    }

    public static CazCaritabil[] getAllCazuri(TeledonProtobufs.TeledonResponse response) {
        CazCaritabil[] cazuri = new CazCaritabil[response.getCazuriCount()];
        for (int i = 0; i < response.getCazuriCount(); i++) {

            TeledonProtobufs.CazCaritabil cazDTO = response.getCazuri(i);
            CazCaritabil c = new CazCaritabil();
            c.setId(Long.valueOf(cazDTO.getId()));
            c.setNume(cazDTO.getNume());
            c.setSumaAdunata(cazDTO.getSumaAdunata());
            cazuri[i] = c;
        }
        return cazuri;
    }

    public static Donator[] getAllDonatori(TeledonProtobufs.TeledonResponse response) {
        Donator[] donatori = new Donator[response.getDonatoriCount()];
        for (int i = 0; i < response.getDonatoriCount(); i++) {

            TeledonProtobufs.Donator donatorDTO = response.getDonatori(i);
            Donator d = new Donator();
            d.setId(Long.valueOf(donatorDTO.getId()));
            d.setAdresa(donatorDTO.getAdresa());
            d.setTelefon(donatorDTO.getTelefon());
            d.setNume(donatorDTO.getNume());
            d.setPrenume(donatorDTO.getPrenume());

            donatori[i] = d;
        }
        return donatori;
    }

    public static TeledonProtobufs.TeledonResponse createGetAllDonatoriResponse(List<Donator> donatorList)
    {
        TeledonProtobufs.TeledonResponse.Builder response=TeledonProtobufs.TeledonResponse.newBuilder()
                .setType(TeledonProtobufs.TeledonResponse.Type.GET_ALL_DONATORI);

        for(Donator d : donatorList)
        {
            System.out.println(d.getNume());
            TeledonProtobufs.Donator donator = TeledonProtobufs.Donator.newBuilder()
                    .setId(d.getId())
                    .setAdresa(d.getAdresa())
                    .setTelefon(d.getTelefon())
                    .setNume(d.getNume())
                    .setPrenume(d.getPrenume())
                    .build();
            response.addDonatori(donator);
        }
        return response.build();
    }

    public static TeledonProtobufs.TeledonResponse createGetAllCazuriResponse(List<CazCaritabil> cazuriList)
    {
        TeledonProtobufs.TeledonResponse.Builder response=TeledonProtobufs.TeledonResponse.newBuilder()
                .setType(TeledonProtobufs.TeledonResponse.Type.GET_ALL_CAZURI);

        for(CazCaritabil c : cazuriList)
        {
            System.out.println(c.getNume());
            TeledonProtobufs.CazCaritabil caz = TeledonProtobufs.CazCaritabil.newBuilder()
                    .setId(c.getId())
                    .setNume(c.getNume())
                    .setSumaAdunata(c.getSumaAdunata())
                    .build();
            response.addCazuri(caz);
        }
        return response.build();
    }

    public static Donatie getDonatie(TeledonProtobufs.TeledonResponse response)
    {
        Donatie donatie = new Donatie();
        CazCaritabil caz = new CazCaritabil();
        Donator donator = new Donator();

        donator.setId(response.getDonatie().getDonator().getId());
        donator.setAdresa(response.getDonatie().getDonator().getAdresa());
        donator.setTelefon(response.getDonatie().getDonator().getTelefon());
        donator.setNume(response.getDonatie().getDonator().getNume());
        donator.setPrenume(response.getDonatie().getDonator().getPrenume());

        caz.setId(response.getDonatie().getCaz().getId());
        caz.setNume(response.getDonatie().getCaz().getNume());
        caz.setSumaAdunata(response.getDonatie().getCaz().getSumaAdunata());

        donatie = new Donatie(donator, caz, response.getDonatie().getSuma());
        donatie.setId(response.getDonatie().getId());

        return donatie;
    }


    public static TeledonProtobufs.TeledonRequest createLogoutRequest(Voluntar voluntar) {

        TeledonProtobufs.Voluntar voluntar1 =TeledonProtobufs.Voluntar.newBuilder()
                .setUsername(voluntar.getUsername())
                .setPassword(voluntar.getPassword())
                .build();
        TeledonProtobufs.TeledonRequest request = TeledonProtobufs.TeledonRequest.newBuilder()
                .setType(TeledonProtobufs.TeledonRequest.Type.LOGOUT)
                .setVoluntar(voluntar1)
                .build();
        return request;
    }
}
