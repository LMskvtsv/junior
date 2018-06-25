package xmlJDBC;

import java.io.File;

public class LightsCameraAction {

    public static void main(String[] args) {
        StoreSQL storeSQL = new StoreSQL("config/DataBaseSQLite.cfg");
        storeSQL.generate(1000000);
        File target = new File("/Users/Leeda/Downloads/xmlTest.xml");
        XmlUsage xmlUsage = new XmlUsage(target);
        xmlUsage.save(storeSQL.getContentFromDB());
        File convertedXML = new File("/Users/Leeda/Downloads/covertedXML.xml");
        File scheme = new File("/Users/Leeda/Downloads/sceme.xsl");
        ConvertXSQT convertXSQT = new ConvertXSQT();
        convertXSQT.convert(target, convertedXML, scheme);
        SAXParser parser = new SAXParser();
        System.out.println(parser.parse(convertedXML));
    }
}
