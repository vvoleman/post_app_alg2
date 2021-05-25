package cz.tul.vvoleman.test;

import cz.tul.vvoleman.app.address.AddressLibrary;
import cz.tul.vvoleman.app.auth.Auth;
import cz.tul.vvoleman.app.post.PostLibrary;
import cz.tul.vvoleman.app.post.mail.Mail;
import cz.tul.vvoleman.app.post.mail.MailContainer;
import cz.tul.vvoleman.app.post.mail.Package;
import cz.tul.vvoleman.app.post.mail.Status;
import cz.tul.vvoleman.app.post.storage.DatabasePostStore;
import cz.tul.vvoleman.utils.exception.address.BadAddressFormatException;
import cz.tul.vvoleman.utils.exception.auth.UnknownUserException;
import cz.tul.vvoleman.utils.exception.storage.StorageException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PostLibraryTest {

    @Test
    @DisplayName("Status na string")
    void statusToString() {
        assertEquals("Registered", Status.Registered.toString());
    }

    @Test
    @DisplayName("Vytvoření zásilky")
    void create() throws UnknownUserException, StorageException, SQLException, BadAddressFormatException {
        MailContainer mc = new MailContainer(
                Status.Registered, Auth.getUser(1),
                AddressLibrary.getAddressByInput("Krupá 114 27009"),
                "Vojtěch Kalný",
                "package",
                Package.Type.M.toString()
        );
        DatabasePostStore store = new DatabasePostStore();
        Mail m = store.create(mc);
        assertEquals(m.getReceiverName(),mc.receiverName);

    }
}