package xml;

import org.apache.log4j.Logger;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ConvertXSQT {

    private static final Logger LOGGER = Logger.getLogger(ConvertXSQT.class);

    public File convert(File source, File dest, File scheme) {
        TransformerFactory factory = TransformerFactory.newInstance();
        try {
            Transformer transformer = factory.newTransformer(
                    new StreamSource(
                            new ByteArrayInputStream(Files.readAllBytes(scheme.toPath()))));

            transformer.transform(new StreamSource(
                            new ByteArrayInputStream(Files.readAllBytes(source.toPath()))),
                    new StreamResult(dest));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return dest;
    }
}
