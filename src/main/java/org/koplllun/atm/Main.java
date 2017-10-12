package org.koplllun.atm;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, JAXBException {
        ATM atm = new ATM();
        atm.runATM();
    }
}