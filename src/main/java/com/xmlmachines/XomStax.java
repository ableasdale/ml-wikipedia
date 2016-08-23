package com.xmlmachines;

import nu.xom.Document;
import nu.xom.NodeFactory;
import nux.xom.io.StaxParser;
import nux.xom.io.StaxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by ableasdale on 22/08/2016.
 */
public class XomStax {
    public static void main(String[] args) throws Exception {

        Logger LOG = LoggerFactory.getLogger(XomStax.class);


        InputStream in = new FileInputStream("/Users/ableasdale/Downloads/enwiki-20160720-pages-articles-multistream.xml");
        XMLStreamReader reader = StaxUtil.createXMLStreamReader(in, null);
        reader.require(XMLStreamConstants.START_DOCUMENT, null, null);

        /*
        while (reader.nextTag() != XMLStreamConstants.END_ELEMENT && reader.getLocalName() != "siteinfo"){
            LOG.info("EVT:"+reader.getEventType());
            LOG.info("Skipping:" + reader.getLocalName());
            reader.nextTag();
        }*/
        boolean ready = false;

        while (!ready) {
            reader.next();
            LOG.info("EVT:"+reader.getEventType());
            if(reader.getEventType() == XMLStreamConstants.END_ELEMENT && reader.getLocalName().equals("siteinfo")){
                LOG.info("found start");
                ready = true;
            }
        }

        LOG.info("READER AT:"+reader.getEventType() + " | " +reader.getLocalName());
        reader.nextTag();

        /*L OG.info("READER AT:"+reader.getEventType() + " "+reader.getText());
        reader.next(); */

        reader.require(XMLStreamConstants.START_ELEMENT, "http://www.mediawiki.org/xml/export-0.10/", "page");


        while (reader.nextTag() == XMLStreamConstants.START_ELEMENT) { // yet another article
            reader.require(XMLStreamConstants.START_ELEMENT, "http://www.mediawiki.org/xml/export-0.10/", "page");

            Document fragment = new StaxParser(reader, new NodeFactory()).buildFragment();

            // do something useful with the fragment...
            System.out.println("fragment = "+ fragment.getRootElement().toXML());
        }

        reader.close();
        in.close();
    }
}
