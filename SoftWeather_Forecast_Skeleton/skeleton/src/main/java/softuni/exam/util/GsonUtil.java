package softuni.exam.util;

import com.google.gson.Gson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public enum GsonUtil {
    ;
    public static <T> void writeJsonIntoFile(Gson gson, List<T> objects, Path filePath) throws IOException {
        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        gson.toJson(objects, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }

    public static <T> void writeXmlIntoFile(T object, Path filePath) throws JAXBException {
        final File file = filePath.toFile();

        final JAXBContext context = JAXBContext.newInstance(object.getClass());
        final Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(object, file);
    }
}
