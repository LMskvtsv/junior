package xml;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ConvertXSQT {

    public File convert(File source, File dest, File scheme) {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer(
                    new StreamSource(
                            new ByteArrayInputStream(Files.readAllBytes(scheme.toPath()))));

            transformer.transform(new StreamSource(
                            new ByteArrayInputStream(Files.readAllBytes(source.toPath()))),
                    new StreamResult(dest));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return dest;
    }
}
