package com.xmlmachines;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.config.WikiConfig;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;
import org.sweble.wom3.Wom3Document;
import org.sweble.wom3.swcadapter.AstToWomConverter;

public class Sweble2
{
    public static void main(String[] args) throws Exception
    {
        run(
                new File(System.getProperty("user.dir")+"/src/main/resources/test3.txt"),
                "title",
                false);


    }

    static String run(File file, String fileTitle, boolean renderHtml) throws Exception
    {
        // Set-up a simple wiki configuration
        WikiConfig config = DefaultConfigEnWp.generate();

        final int wrapCol = 80;

        // Instantiate a compiler for wiki pages
        WtEngineImpl engine = new WtEngineImpl(config);

        // Retrieve a page
        PageTitle pageTitle = PageTitle.make(config, fileTitle);

        PageId pageId = new PageId(pageTitle, -1);

        String wikitext = FileUtils.readFileToString(file);

        // Compile the retrieved page
        EngProcessedPage cp = engine.postprocess(pageId, wikitext, null);

        // Convert the AST to a WOM document
        Wom3Document womDoc = AstToWomConverter.convert(
                config.getParserConfig(),
                null /*pageNamespace*/,
                null /*pagePath*/,
                pageId.getTitle().getTitle(),
                "Mr. Tester",
                DateTime.parse("2012-12-07T12:15:30.000+01:00"),
                cp.getPage());

        if (renderHtml)
        {

            throw new UnsupportedOperationException(
                    "HTML rendering is not yet supported!");

			/*
			String ourHtml = HtmlRenderer.print(new MyRendererCallback(), config, pageTitle, cp.getPage());

			String template = IOUtils.toString(App.class.getResourceAsStream("/render-template.html"), "UTF8");

			String html = template;
			html = html.replace("{$TITLE}", StringUtils.escHtml(pageTitle.getDenormalizedFullTitle()));
			html = html.replace("{$CONTENT}", ourHtml);

			return html;
			*/
        }
        else
        {
            TextConverter p = new TextConverter(config, wrapCol);
            System.out.println(p.go(womDoc));
            return (String) p.go(womDoc);
        }
    }

}