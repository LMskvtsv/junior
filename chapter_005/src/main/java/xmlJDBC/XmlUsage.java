package xmlJDBC;

import com.sun.xml.internal.txw2.annotation.XmlElement;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.List;

public class XmlUsage {

    File target;

    public XmlUsage(File target) {
        this.target = target;
    }

    @XmlRootElement
    public static class Entries {
        private List<Entry> entries;

        public Entries() {
        }

        public Entries(List<Entry> values) {
            this.entries = values;
        }

        public List<Entry> getEntry() {
            return entries;
        }

        public void setEntry(List<Entry> values) {
            this.entries = values;
        }
    }

    @XmlElement
    public static class Entry {
        private long value;

        public Entry() {
        }

        public Entry(int value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }

    public void save(List<Entry> list) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Entries.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(new Entries(list), this.target);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}