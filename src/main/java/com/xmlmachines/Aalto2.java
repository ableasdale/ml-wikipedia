package com.xmlmachines;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import nu.xom.*;

import javax.xml.stream.*;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by ableasdale on 22/08/2016.
 */
public class Aalto2  {

    public static void main(String[] args) throws Exception {
        StringWriter sw = new StringWriter();
        XMLOutputFactory of = XMLOutputFactory.newInstance();


       // of.setProperty("javax.xml.stream.isRepairingNamespaces", Boolean.TRUE);

        XMLEventWriter xw = null;
        XMLInputFactory f = XMLInputFactory.newInstance();
        XMLEventReader xr = f.createXMLEventReader(new FileInputStream("/Users/ableasdale/Downloads/enwiki-20160720-pages-articles-multistream.xml"));
        while (xr.hasNext()) {
            XMLEvent e = xr.nextEvent();
            if (e.isStartElement()
                    && ((StartElement) e).getName().getLocalPart().equals("page")) {
                xw = of.createXMLEventWriter(sw);
            } else if (e.isEndElement()
                    && ((EndElement) e).getName().getLocalPart().equals("page")) {
                break;
            } else if (xw != null) {
                xw.add(e);
            }
        }
        xw.close();
        System.out.println(sw);
    }
}
