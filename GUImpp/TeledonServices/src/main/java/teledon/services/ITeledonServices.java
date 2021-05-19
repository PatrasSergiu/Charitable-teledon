package teledon.services;

import teledon.model.CazCaritabil;
import teledon.model.Donatie;
import teledon.model.Donator;
import teledon.model.Voluntar;

public interface ITeledonServices {

    void login(Voluntar voluntar, ITeledonObserver client) throws TeledonException;
    void logout(Voluntar voluntar, ITeledonObserver client) throws TeledonException;
    public Iterable<Donator> getAllDonatori() throws TeledonException;
    public void newDonation(Donatie donatie) throws TeledonException;
    public Iterable<CazCaritabil> getAllCazuri() throws TeledonException;


}
