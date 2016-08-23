package com.xmlmachines;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
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

public class Aalto {
    private static final Logger LOG = LoggerFactory.getLogger(Aalto.class);

    private void execute(String xmlFileName) throws Exception {

        LOG.info("Processing Filename: {0} "+ xmlFileName);
        //InputStream xmlInputStream = getClass().getResourceAsStream(xmlFileName);
        InputStream xmlInputStream = new FileInputStream(xmlFileName);
        //Load Aalto's StAX parser factory

        //private static final AsyncXMLInputFactory XML_INPUT_FACTORY
        XMLInputFactory xmlInputFactory = new InputFactoryImpl();
        //XMLInputFactory2 xmlInputFactory = (XMLInputFactory2) XMLInputFactory.newFactory("com.fasterxml.aalto.stax.InputFactoryImpl", this.getClass().getClassLoader());
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(xmlInputStream);

        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        outputFactory.setProperty("javax.xml.stream.isRepairingNamespaces", Boolean.TRUE);


//        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory("com.fasterxml.aalto.stax.OutputFactoryImpl",getClass().getClassLoader());
//        XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(System.out);



//        StringWriter sw = new StringWriter();
//        XMLOutputFactory of = XMLOutputFactory.newInstance();
//        XMLEventWriter xw = null;
//        XMLInputFactory f = XMLInputFactory.newInstance();
//            XMLEventReader xr = f.createXMLEventReader(xmlInputStream);
        boolean isStarted = false;
        Element ele = null;
        Document doc = null;

        while(xmlStreamReader.hasNext()){
            //LOG.info("something"+xmlStreamReader.next());
            int eventType = xmlStreamReader.next();
           // LOG.info("e:"+eventType);
            switch (eventType) {
                case XMLEvent.START_ELEMENT:
                    //LOG.info("Start:"+eventType);
                    //LOG.info("Start "+xmlStreamReader.getLocalName());
                    //doc = new Document(root);
                    ele = new Element(xmlStreamReader.getLocalName(), xmlStreamReader.getNamespaceURI());
                    if(xmlStreamReader.getName().toString().equals("{http://www.mediawiki.org/xml/export-0.10/}page")){
                        isStarted = true;
                        doc = new Document(ele);
                    }
                    //content.append("<").append(xmlStreamReader.getLocalName()).append(">");
                    //LOG.info(content.toString());
                    //LOG.info(xmlStreamReader.getName().toString());
                    //if(xmlStreamReader.getName().toString().equals("{http://www.mediawiki.org/xml/export-0.10/}page") || xmlStreamReader.getName().toString().equals("{http://www.mediawiki.org/xml/export-0.10/}page"))
                    break;
                case XMLEvent.CHARACTERS:
                    //LOG.info("Chars:"+eventType)
                      ele.appendChild(xmlStreamReader.getText());
                    break;
                case XMLEvent.END_ELEMENT:
                    //LOG.info("End:"+eventType);
                    //content.append("</").append(xmlStreamReader.getLocalName()).append(">");
                    if(isStarted) {
                        //if doc.
                        doc.appendChild(ele);
                    }
                    if(xmlStreamReader.getName().toString().equals("{http://www.mediawiki.org/xml/export-0.10/}page")){
                        //LOG.info(content.toString());
                        LOG.info(doc.toXML());
                        doc = null;
                    }
                    break;
                default:
                    //content.append(xmlStreamReader.getText());
                    LOG.info("e:"+eventType);
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
        String BASE = "/Users/ableasdale/Downloads/";
        String SRC_FILE = "enwiki-20160720-pages-articles-multistream.xml";
        (new Aalto()).execute(BASE+SRC_FILE);
    }

}

/*

   StringWriter sw = new StringWriter();
    XMLOutputFactory of = XMLOutputFactory.newInstance();
    XMLEventWriter xw = null;
    XMLInputFactory f = XMLInputFactory.newInstance();
    XMLEventReader xr = f.createXMLEventReader(new FileInputStream("test.xml"));
    while (xr.hasNext()) {
        XMLEvent e = xr.nextEvent();
        if (e.isStartElement()
                && ((StartElement) e).getName().getLocalPart().equals("tagINeed")) {
            xw = of.createXMLEventWriter(sw);
        } else if (e.isEndElement()
                && ((EndElement) e).getName().getLocalPart().equals("tagINeed")) {
            break;
        } else if (xw != null) {
            xw.add(e);
        }
    }
    xw.close();
    System.out.println(sw);
 */

/*
    Element root = new Element("ip2country");
    Document doc = new Document(root);

    Element from = new Element("IP-From");
                from.appendChild(String.valueOf(start));
                        root.appendChild(from);

                        Element to = new Element("IP-TO");
                        to.appendChild(String.valueOf(end));
                        root.appendChild(to);

                        Element cc = new Element("CountryCode");
                        cc.appendChild(line.substring(20,22));
                        root.appendChild(cc);

                        // Now write the XOM Obj to MarkLogic
                        XMLDocumentManager docMgr = client.newXMLDocumentManager();
                        docMgr.write(String.format("/%s.xml", DigestUtils.md5Hex(doc.toXML())), new XOMHandle(doc));
*/