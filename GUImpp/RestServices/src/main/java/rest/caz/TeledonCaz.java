package rest.caz;

import teledon.model.CazCaritabil;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import teledon.services.rest.ServiceException;

import java.util.concurrent.Callable;

public class TeledonCaz {


        public static final String URL = "http://localhost:8080/teledon/cazuri";

        private RestTemplate restTemplate = new RestTemplate();

        private <T> T execute(Callable<T> callable) {
            try {
                return callable.call();
            } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
                throw new ServiceException(e);
            } catch (Exception e) {
                throw new ServiceException(e);
            }
        }

        public CazCaritabil[] getAll() {
            return execute(() -> restTemplate.getForObject(URL, CazCaritabil[].class));
        }

        public CazCaritabil create(CazCaritabil caz) {
            return execute(() -> restTemplate.postForObject(URL, caz, CazCaritabil.class));
        }

        public void update(CazCaritabil caz) {
            execute(() -> {
                restTemplate.put(String.format("%s/%s", URL, caz.getId()), caz);
                return null;
            });
        }

    public CazCaritabil getById(String id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), CazCaritabil.class));
    }

        public void delete(String id) {
            execute(() -> {
                restTemplate.delete(String.format("%s/%s", URL, id));
                return null;
            });
        }


}
