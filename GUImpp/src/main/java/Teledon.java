import java.io.FileReader;
import java.io.IOException;

import teledon.model.Donatie;
import teledon.persistence.repository.DonatieDBRepository;
import teledon.persistence.repository.IDonatieRepo;

import java.util.Properties;

public class Teledon {
        public static void main(String[] args) {
                Properties properties = new Properties();
                try {
                        properties.load(new FileReader("bd.config"));
                }
                catch(IOException e ){
                        System.out.println("Cannot find bd.config " + e);
                }
/*
                IDonatorRepo donatorRepo = new DonatorDBRepository(properties);
                System.out.println("Donatori: ");
                for(teledon.model.Donator donator: donatorRepo.findAll())
                        System.out.println(donator);
                System.out.println(donatorRepo.findOne(Long.parseLong("1")));
             */
               /* ICazRepo cazRepo = new CazDBRepository(properties);
                for(teledon.model.CazCaritabil caz: cazRepo.findAll())
                        System.out.println(caz);*/
                IDonatieRepo donatieRepo = new DonatieDBRepository(properties);
                for(Donatie donatie : donatieRepo.findAll())
                        System.out.println(donatie);

        }



}
