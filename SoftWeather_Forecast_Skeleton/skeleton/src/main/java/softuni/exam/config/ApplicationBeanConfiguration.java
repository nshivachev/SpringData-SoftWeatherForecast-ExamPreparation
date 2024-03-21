package softuni.exam.config;

import com.google.gson.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public ModelMapper createModelMapper() {
        final ModelMapper modelMapper = new ModelMapper();

//        modelMapper.addConverter((Converter<String, LocalTime>) mappingContext ->
//                LocalTime.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("HH:mm:ss")));

        return modelMapper;
    }

    @Bean
    public Gson createGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (localTime, type, jsonSerializationContext) -> new JsonPrimitive(localTime.format(DateTimeFormatter.ISO_LOCAL_TIME)))
                .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (jsonElement, type, jsonDeserializationContext) -> LocalTime.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                .create();
    }
}
