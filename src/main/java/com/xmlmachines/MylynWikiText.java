package com.xmlmachines;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;
import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;
import org.eclipse.mylyn.wikitext.core.parser.markup.MarkupLanguage;
import org.eclipse.mylyn.wikitext.markdown.core.MarkdownLanguage;
import org.eclipse.mylyn.wikitext.mediawiki.core.MediaWikiLanguage;

/**
 * Created by ableasdale on 23/08/2016.
 */
public class MylynWikiText {
    public static void main(String[] args) {
        String text = null;
        try {
            text = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"/src/main/resources/test2.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        StringBuilder sb = new StringBuilder();
//        sb.append("# Heading 1\n");
//        sb.append("\n");
//        sb.append("Hello World!\n");
//        sb.append("\n");
//        sb.append("* Lorem\n");
//        sb.append("* Ipsum\n");
//        sb.append("\n");
//        sb.append("This is **Markdown**  language.\n");
//        String text = sb.toString();

        //MarkupLanguage markupLanguage = new MarkdownLanguage();
        MarkupLanguage markupLanguage = new MediaWikiLanguage();

        StringWriter writer = new StringWriter();
        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
        builder.setEmitAsDocument(false);
        MarkupParser parser = new MarkupParser(markupLanguage, builder);
        parser.parse(text);

        System.out.println(text);
        System.out.println(writer.toString());
    }
}
