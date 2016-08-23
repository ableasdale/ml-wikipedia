import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;

class Item{
    private String firstText = null;

    public void setFirstText(String str){
        firstText =  str;
    }

    public String getFirstText(){
        if(firstText == null){
            return null;
        }else{
            return firstText;
        }
    }
}

public class Stax {
    public static void main(String[] args) throws FileNotFoundException,
            XMLStreamException, FactoryConfigurationError {
        // First create a new XMLInputFactory
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();

        //inputFactory.setProperty("javax.xml.stream.isCoalescing", True)

        // Setup a new eventReader
        InputStream in = new FileInputStream("/usa/xiwang/Desktop/config");
        XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

        Item item = null;

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();

            //reach the start of an item
            if (event.isStartElement()) {

                StartElement startElement = event.asStartElement();

                if (startElement.getName().getLocalPart().equals("item")) {
                    item = new Item();
                    System.out.println("--start of an item");
                    // attribute
                    Iterator<Attribute> attributes = startElement.getAttributes();
                    while (attributes.hasNext()) {
                        Attribute attribute = attributes.next();
                        if (attribute.getName().toString().equals("id")) {
                            System.out.println("id = " + attribute.getValue());
                        }
                    }
                }

                // data
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart().equals("thetext")) {
                        event = eventReader.nextEvent();

                        if(item.getFirstText() == null){
                            System.out.println("thetext: "
                                    + event.asCharacters().getData());
                            item.setFirstText("notnull");
                            continue;
                        }else{
                            continue;
                        }

                    }
                }
            }

            //reach the end of an item
            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                if (endElement.getName().getLocalPart() == "item") {
                    System.out.println("--end of an item\n");
                    item = null;
                }
            }

        }
    }
}