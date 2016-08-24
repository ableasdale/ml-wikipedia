package com.xmlmachines;

import org.apache.commons.io.FileUtils;
import org.xwiki.component.embed.EmbeddableComponentManager;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.rendering.converter.ConversionException;
import org.xwiki.rendering.converter.Converter;
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter;
import org.xwiki.rendering.renderer.printer.WikiPrinter;
import org.xwiki.rendering.syntax.Syntax;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by ableasdale on 23/08/2016.
 */
public class XWiki {

    public static void main(String[] args) throws Exception {
        XWiki xw = new XWiki();
        xw.doStuff();
    }

    public void doStuff() {
        // Initialize Rendering components and allow getting instances
        EmbeddableComponentManager componentManager = new EmbeddableComponentManager();
        componentManager.initialize(this.getClass().getClassLoader());

//// Get the MediaWiki Parser
//        Parser parser = componentManager.getInstance(Parser.class, "mediawiki/1.0);
//
//// Parse the content in mediawiki markup and generate an AST (it's also possible to use a streaming parser for large content)
//                XDOM xdom = parser.parse(new StringReader("... input here"));
//
//// Perform any transformation you wish to the XDOM here
//
//
//// Generate XHTML out of the modified XDOM
//        WikiPrinter printer = new DefaultWikiPrinter();
//        BlockRenderer renderer = componentManager.getInstance(BlockRenderer.class, "xhtml/1.0");
//        renderer.render(xdom, printer);
//
//// The result is now in the printer object
//        printer.toString();

        // Use the Converter component to convert between one syntax to another.
        Converter converter = null;
        try {
            converter = componentManager.getInstance(Converter.class);
        } catch (ComponentLookupException e) {
            e.printStackTrace();
        }

// Convert input in XWiki Syntax 2.1 into XHTML. The result is stored in the printer.
        WikiPrinter printer = new DefaultWikiPrinter();

        try {
            String data = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"/src/main/resources/test3.txt"));
            converter.convert(new StringReader(data), Syntax.MEDIAWIKI_1_0, Syntax.XHTML_1_0, printer);
        } catch (ConversionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(printer.toString());
    }
}
