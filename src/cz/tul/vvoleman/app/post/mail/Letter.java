package cz.tul.vvoleman.app.post.mail;

import cz.tul.vvoleman.app.address.Address;
import cz.tul.vvoleman.app.auth.model.User;

public class Letter extends Mail{

    public static final String name = "letter";
    private static final String suffix = "P";

    private final Letter.Type letterType;

    public Letter(int id, Status status, User sender, Address receiverAddress, String receiverName, Type letterType) {
        super(id, status, sender, receiverAddress, receiverName);
        this.letterType = letterType;
    }

    @Override
    public String getTextId() {
        return String.format("CZ%s%s%10d",letterType.suffix,suffix,id);
    }

    public Type getType(){
        return letterType;
    }

    public enum Type{
        //Doporučené psaní
        Recorded("D"),
        Valuable("C");

        private final String suffix;

        Type(String suffix){
            this.suffix = suffix;
        }
    }

}
