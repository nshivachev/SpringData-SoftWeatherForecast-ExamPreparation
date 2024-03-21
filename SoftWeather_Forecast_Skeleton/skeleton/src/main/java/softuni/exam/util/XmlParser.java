package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

@Component
public class XmlParser {
    public <T> T fromFile(Path filePath, Class<T> object) throws FileNotFoundException, JAXBException {
        final FileReader fileReader = new FileReader(filePath.toFile());

        final JAXBContext context = JAXBContext.newInstance(object);
        final Unmarshaller unmarshaller = context.createUnmarshaller();

        return (T) unmarshaller.unmarshal(fileReader);
    }
}
