package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.repository.CityRepository;
import softuni.exam.service.CityService;

import java.io.IOException;
import java.nio.file.Files;

import static softuni.exam.util.Paths.CITIES_JSON_INPUT_PATH;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper, Gson gson) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(CITIES_JSON_INPUT_PATH);
    }

    @Override
    public String importCities() throws IOException {
        return null;
    }
}
