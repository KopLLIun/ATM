package org.koplllun.atm.parser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.InputStream;

public interface Parser {
    Object getObject(InputStream file, Class c) throws JAXBException;
    void saveObject(File file, Object o) throws JAXBException;
}
