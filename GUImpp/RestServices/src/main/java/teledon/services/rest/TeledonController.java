package teledon.services.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teledon.model.CazCaritabil;
import teledon.persistence.repository.ICazRepo;
import teledon.persistence.repository.RepositoryException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/teledon/cazuri")
public class TeledonController {
    private static final String template = "Hello, %s!";

    @Autowired
    private ICazRepo repoCaz;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="numeCaz", defaultValue="NevoieBani") String name) {
        return String.format(template, name);
    }

    @RequestMapping( method= RequestMethod.GET)
    public CazCaritabil[] getAll(){
        System.out.println(repoCaz.findAll());
        List<CazCaritabil> probaList = (List<CazCaritabil>) StreamSupport.stream(repoCaz.findAll().spliterator(), false).collect(Collectors.toList());
        return probaList.toArray(new CazCaritabil[probaList.size()]);
    }

    @RequestMapping(method = RequestMethod.POST)
    public CazCaritabil create(@RequestBody CazCaritabil caz){

        CazCaritabil pr = repoCaz.save(caz);
        return pr;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){

        CazCaritabil caz = repoCaz.findOne(Long.parseLong(id));
        if (caz==null)
            return new ResponseEntity<String>("Cazul caritabil nu a fost gasit", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<CazCaritabil>(caz, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public CazCaritabil update(@RequestBody CazCaritabil caz) {
        System.out.println("Updating caz ...");
        System.out.println(caz);
        repoCaz.update(caz);
        return caz;

    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Deleting caz ... "+id);
        try {
            repoCaz.delete(Long.parseLong(id));
            return new ResponseEntity<CazCaritabil>(HttpStatus.OK);
        }catch (RepositoryException ex){
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }

}
