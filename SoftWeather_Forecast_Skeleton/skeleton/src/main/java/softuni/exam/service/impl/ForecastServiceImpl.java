package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;

import static softuni.exam.util.Paths.FORECASTS_XML_INPUT_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {
    private final ForecastRepository forecastRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public ForecastServiceImpl(ForecastRepository forecastRepository, ModelMapper modelMapper, Gson gson) {
        this.forecastRepository = forecastRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(FORECASTS_XML_INPUT_PATH);
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        return null;
    }

    @Override
    public String exportForecasts() {
        return null;
    }
}
