package cz.tul.vvoleman.app.post;

import cz.tul.vvoleman.app.address.Address;

import java.util.Map;

public class Warehouse{

    /**
     * Warehouse content
     */
    protected MailStorage mailStorage;

    public Warehouse(){
        mailStorage = new MailStorage();
    }

    public void incomingTransport(MailTransport mailTransport){
        mailStorage.add(mailTransport);
    }

    /**
     * Processes outgoing transport
     *
     * @param target PostalInterface
     */
    public void outgoingTransport(PostOffice target) {
        target.incomingTransport(new MailTransport(mailStorage.filterByPsc(target.getPsc(),true,true)));
    }

}