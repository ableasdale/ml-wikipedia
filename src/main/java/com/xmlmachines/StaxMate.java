package com.xmlmachines;

import org.codehaus.staxmate.SMInputFactory;
import org.codehaus.staxmate.in.SMHierarchicCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;

/**
 * Created by ableasdale on 24/07/2016.
 */
public class StaxMate {

    private static final Logger LOG = LoggerFactory.getLogger(StaxMate.class);

    public static void main(String[] args) throws XMLStreamException {


        String BASE = "/Users/ableasdale/Downloads/";

        String SRC_FILE = "enwiki-20160720-pages-articles-multistream.xml";

        // 1: need input factory
        SMInputFactory inf = new SMInputFactory(XMLInputFactory.newInstance());
        // 2: and root cursor that reads XML document from File:
        SMHierarchicCursor rootC = inf.rootElementCursor(new File(BASE+SRC_FILE));
        rootC.advance(); // note: 2.0 only method; can also call ".getNext()"
        //int employeeId = rootC.getAttrIntValue(0);
        String thing = rootC.getAttrValue(0);
        SMHierarchicCursor nameC = (SMHierarchicCursor) rootC.childElementCursor("page").advance();
        SMHierarchicCursor leafC = (SMHierarchicCursor) nameC.childElementCursor().advance();
        String first = leafC.collectDescendantText(false);
        LOG.info(first);
        leafC.advance();
        String last = leafC.collectDescendantText(false);
        rootC.getStreamReader().closeCompletely();
    }
}
