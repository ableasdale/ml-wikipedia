package com.xmlmachines;

import org.apache.commons.io.FileUtils;
import org.xwiki.component.embed.EmbeddableComponentManager;
import org.xwiki.rendering.block.XDOM;
import org.xwiki.rendering.parser.Parser;
import org.xwiki.rendering.renderer.BlockRenderer;
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter;
import org.xwiki.rendering.renderer.printer.WikiPrinter;
import org.xwiki.rendering.syntax.Syntax;


import java.io.File;
import java.io.StringReader;

/**
 * Created by ableasdale on 23/08/2016.
 */
public class XWiki2 {

    public static void main(String[] args) throws Exception {
        XWiki2 xw = new XWiki2();
        xw.doStuff();
    }

    public void doStuff() {
        try {


            String str = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"/src/main/resources/test3.txt"), "UTF-8");

            // XWIKI

            // Initialize Rendering components and allow getting instances
            EmbeddableComponentManager componentManager = new
                    EmbeddableComponentManager();
            componentManager.initialize(this.getClass().getClassLoader());

// Get the MediaWiki Parser
            Parser parser = componentManager.getInstance(Parser.class,
                    "mediawiki/1.0");

// Parse the content in mediawiki markup and generate an AST (it's alsopossible to use a streaming parser for large content)
            XDOM xdom = parser.parse(new StringReader(str));

// Generate XHTML out of the modified XDOM
            WikiPrinter printer = new DefaultWikiPrinter();
//            BlockRenderer renderer =
//                    componentManager.getInstance(BlockRenderer.class, "html/5.0" );

            BlockRenderer renderer = componentManager.getInstance(
                                       BlockRenderer.class, Syntax.PLAIN_1_0.toIdString());
            //"xhtml/1.0");
            renderer.render(xdom, printer);

// The result is now in the printer object

            System.out.println(printer.toString());

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
