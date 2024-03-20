package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;

import java.io.IOException;
import java.nio.file.Files;

import static softuni.exam.util.Paths.COUNTRIES_JSON_INPUT_PATH;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, Gson gson) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(COUNTRIES_JSON_INPUT_PATH);
    }

    @Override
    public String importCountries() throws IOException {
        return null;
    }
}
