package start;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rest.caz.TeledonCaz;
import teledon.model.CazCaritabil;
import teledon.services.rest.ServiceException;

public class StartRestClient {
    private final static TeledonCaz teledonClient = new TeledonCaz();
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        CazCaritabil proba=new CazCaritabil("testAdd",0);
        try{
            show(()-> System.out.println(teledonClient.create(proba)));
            show(()->{
                CazCaritabil[] res = teledonClient.getAll();
                for(CazCaritabil u:res){
                    System.out.println(u.getNume() +" "+u.getSumaAdunata());
                }
            });
            //proba.setId(9L);
            CazCaritabil pr = teledonClient.getById("16");
            pr.setNume("update");

            show(()->teledonClient.update(pr));
            show(()->{
                CazCaritabil[] res=teledonClient.getAll();
                for(CazCaritabil u:res){
                    System.out.println(u.getNume()+" "+u.getSumaAdunata());
                }
            });
            proba.setId(62L);
            show(()->teledonClient.delete(proba.getId().toString()));
            show(()->{
                CazCaritabil[] res=teledonClient.getAll();
                for(CazCaritabil u:res){
                    System.out.println(u.getNume()+" "+u.getSumaAdunata());
                }
            });
        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }

}
