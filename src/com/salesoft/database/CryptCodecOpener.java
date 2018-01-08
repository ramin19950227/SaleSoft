package com.salesoft.database;

import java.io.File;
import java.io.IOException;
import net.ucanaccess.jdbc.JackcessOpenerInterf­ace;
import com.healthmarketscience.jackcess.CryptCo­decProvider;
import com.healthmarketscience.jackcess.Databas­e;
import com.healthmarketscience.jackcess.Databas­eBuilder;

public class CryptCodecOpener implements JackcessOpenerInterface {

    @Override
    public Database open(File fl, String pwd) throws IOException {
        DatabaseBuilder dbd = new DatabaseBuilder(fl);
        dbd.setAutoSync(false);
        dbd.setCodecProvider(new CryptCodecProvider(pwd));
        dbd.setReadOnly(false);
        return dbd.open();
    }
}
