package com.xmlmachines;

import org.junit.Before;
import org.junit.Test;
import org.xwiki.component.embed.EmbeddableComponentManager;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.component.manager.ComponentRepositoryException;
import org.xwiki.rendering.converter.ConversionException;
import org.xwiki.rendering.converter.Converter;
import org.xwiki.rendering.parser.ParseException;
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter;
import org.xwiki.rendering.renderer.printer.WikiPrinter;
import org.xwiki.rendering.syntax.Syntax;

import java.io.StringReader;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Created by ableasdale on 24/08/2016.
 */
public class XhtmlToXWikiTest {


    private Converter converter;
    private WikiPrinter printer;

    @Test
    public void testHtmlToMarkdown10() throws ComponentLookupException, ConversionException, ParseException, ComponentRepositoryException {
        WikiPrinter printer = new DefaultWikiPrinter();
        converter.convert(new StringReader("<h3 id=\"HHeader3\"><span>Header 3</span></h3>"), Syntax.XHTML_1_0, Syntax.MARKDOWN_1_0, printer);

        //System.out.println(printer.toString());
        assertThat(printer.toString(), containsString("### Header 3 ###"));
    }

    @Test
    public void testHtmlToMarkdown11() throws ComponentLookupException, ConversionException, ParseException, ComponentRepositoryException {
        WikiPrinter printer = new DefaultWikiPrinter();
        converter.convert(new StringReader("<h3 id=\"HHeader3\"><span>Header 3</span></h3>"), Syntax.XHTML_1_0, Syntax.MARKDOWN_1_1, printer);

        //System.out.println(printer.toString());
        assertThat(printer.toString(), containsString("### Header 3 ###"));
    }

    @Test
    public void testHtmlToXWiki21() throws ComponentLookupException, ConversionException, ParseException, ComponentRepositoryException {
        WikiPrinter printer = new DefaultWikiPrinter();
        converter.convert(new StringReader("<h3 id=\"HHeader3\"><span>Header 3</span></h3>"), Syntax.XHTML_1_0, Syntax.XWIKI_2_1, printer);

        //System.out.println(printer.toString());
        assertThat(printer.toString(), containsString("=="));
    }

    @Before
    public void setUp() throws ComponentLookupException, ConversionException {
        EmbeddableComponentManager componentManager = new EmbeddableComponentManager();
        componentManager.initialize(this.getClass().getClassLoader());

        converter = componentManager.getInstance(Converter.class);
        printer = new DefaultWikiPrinter();
    }


}
