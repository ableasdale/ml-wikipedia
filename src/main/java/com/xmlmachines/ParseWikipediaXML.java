package com.xmlmachines;

import com.ximpleware.extended.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;

public class ParseWikipediaXML {

    private static final Logger LOG = LoggerFactory.getLogger(ParseWikipediaXML.class);
    private static final String BASE = "/Users/ableasdale/Downloads/";
    private static final String OUTPUT_FOLDER = "wikibooks-xml/";
    private static final String SRC_FILE = "enwiki-20160720-pages-articles-multistream.xml";

//private static final String SRC_FILE = "enwiki-20160720-pages-articles1.xml-p000000010p000030302";
    private static final String XPATH = "/*";

    public static void main(String[] args) throws
            ParseExceptionHuge,
            XPathParseExceptionHuge, XPathEvalExceptionHuge, NavExceptionHuge,
            IOException {
        LOG.info("started");
        VTDGenHuge vg = new VTDGenHuge();
        if (vg.parseFile(BASE + SRC_FILE, true, VTDGenHuge.MEM_MAPPED)) {
            VTDNavHuge vnh = vg.getNav();
            AutoPilotHuge aph = new AutoPilotHuge(vnh);
            aph.selectXPath(XPATH);
            int i = 0;
            while ((i = aph.evalXPath()) != -1) {
                LOG.info(MessageFormat.format(
                        "Found element: {0} at index: {1}", vnh.toString(i),
                        vnh.getCurrentIndex()));
                long[] la = vnh.getElementFragment();
                if (la != null) {
                    LOG.info(MessageFormat
                            .format("Fragment offset start: {0} end: {1}",
                                    la[0], la[1]));
//                    vnh.getXML().writeToFileOutputStream(
//                            new FileOutputStream(BASE + OUTPUT_FOLDER
//                                    + vnh.getCurrentIndex() + ".xml"), la[0],
//                            la[1]);
                }
            }
        }
    }
}