package com.xmlmachines;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.marklogic.xcc.Request;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.InputStream;

public class Aalto3 {
    private static final Logger LOG = LoggerFactory.getLogger(Aalto.class);

    private void execute(String xmlFileName) throws Exception {

        LOG.info("Processing Filename: {0} " + xmlFileName);
        //InputStream xmlInputStream = getClass().getResourceAsStream(xmlFileName);
        InputStream xmlInputStream = new FileInputStream(xmlFileName);
        //Load Aalto's StAX parser factory

        //private static final AsyncXMLInputFactory XML_INPUT_FACTORY
        XMLInputFactory xmlInputFactory = new InputFactoryImpl();
        //XMLInputFactory2 xmlInputFactory = (XMLInputFactory2) XMLInputFactory.newFactory("com.fasterxml.aalto.stax.InputFactoryImpl", this.getClass().getClassLoader());
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(xmlInputStream);

        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        outputFactory.setProperty("javax.xml.stream.isRepairingNamespaces", Boolean.TRUE);


        StringBuilder content = new StringBuilder();
        while (xmlStreamReader.hasNext()) {
            //LOG.info("something"+xmlStreamReader.next());
            int eventType = xmlStreamReader.next();
            // LOG.info("e:"+eventType);
            switch (eventType) {
                case XMLEvent.START_ELEMENT:
                    if (xmlStreamReader.getName().toString().equals("{http://www.mediawiki.org/xml/export-0.10/}page")){
                        content = new StringBuilder();
                    }
                    content.append("<").append(xmlStreamReader.getLocalName()).append(">");
                    //LOG.info(content.toString());
                    //LOG.info(xmlStreamReader.getName().toString());
                    //if(xmlStreamReader.getName().toString().equals("{http://www.mediawiki.org/xml/export-0.10/}page") || xmlStreamReader.getName().toString().equals("{http://www.mediawiki.org/xml/export-0.10/}page"))
                    break;
                case XMLEvent.CHARACTERS:
                    //LOG.info("Chars:"+eventType)
                    content.append(xmlStreamReader.getText());
                    //ele.appendChild(xmlStreamReader.getText());
                    break;
                case XMLEvent.END_ELEMENT:
                    //LOG.info("End:"+eventType);
                    content.append("</").append(xmlStreamReader.getLocalName()).append(">");

                    if (xmlStreamReader.getName().toString().equals("{http://www.mediawiki.org/xml/export-0.10/}page")) {
                        //LOG.info(content.toString());

                        Session s = MarkLogicSessionProvider.getSession();
                        String query = "xdmp:document-insert('/'||xdmp:random()||'.xml', xdmp:quote("+content.toString()+"))";
                        LOG.info(query);
                        Request r = s.newAdhocQuery(query);

                        // Submit the Request and return a new ResultSequence object.
                        // By default, the result will be cached and need not be closed.
                        ResultSequence rs = s.submitRequest(r);
                        content = new StringBuilder();
                    }
                    break;
                default:
                    //content.append(xmlStreamReader.getText());
                    LOG.info("e:" + eventType);
                    LOG.error("DEAL WITH THIS");
                    break;
            }

//            int e = xmlStreamReader.next();
//            if (e == XMLEvent.START_ELEMENT
//                    && ((StartElement) e).getName().toString().equals("{http://www.mediawiki.org/xml/export-0.10/}page")) {
//                xw = of.createXMLEventWriter(sw);
//            } else if (e == XMLEvent.END_ELEMENT
//                    && ((EndElement) e).getName().toString().equals("{http://www.mediawiki.org/xml/export-0.10/}page")) {
//                break;
//
//            } else if (xw != null) {
//                xw.add(e);
//            }


//            XMLEvent event = xr.nextEvent();
//            if(event.isStartElement()) {
//                // other start element processing here
//
//                LOG.info("Start Element!");
//            } else if(event.isEndElement()) {
//                if(content != null) {
//                    // this was a leaf element
//                    String leafText = content.toString();
//                    // do something with the leaf node
//                } else {
//                    // not a leaf
//                }
//                // in all cases, discard content
//                content = null;
//            } else if(event.isCharacters()) {
//                if(content != null) {
//                    content.append(event.asCharacters().getData());
//                }
//            }


        }

    }

    public static void main(String[] args) throws Exception {
        String BASE = "/Users/ableasdale/Downloads/wikipedia-stuff/";
        String SRC_FILE = "enwiki-20160720-pages-articles-multistream.xml";
        (new Aalto3()).execute(BASE + SRC_FILE);
    }

}
