package xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SAXParser {

    ArrayList<Long> list = new ArrayList<>();

    public long parse(File file) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        javax.xml.parsers.SAXParser saxParser;
        try {
            saxParser = spf.newSAXParser();
            saxParser.parse(file.getAbsolutePath(), new EntryHandler());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.stream().mapToLong(Long::longValue).sum();
    }

    public class EntryHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equalsIgnoreCase("entry")) {
                list.add(Long.valueOf(attributes.getValue("href")));
            }
        }
    }
}
