package teledon.services;

import teledon.model.Donatie;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITeledonObserver extends Remote {
    void newDonationAdded(Donatie donatie) throws TeledonException, RemoteException;

}
