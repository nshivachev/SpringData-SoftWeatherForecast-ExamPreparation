package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityImportDto;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static softuni.exam.util.Messages.INCORRECT_DATA_MESSAGE_FORMAT;
import static softuni.exam.util.Messages.SUCCESSFULLY_IMPORTED_DATA_MESSAGE_FORMAT;
import static softuni.exam.util.Paths.CITIES_JSON_INPUT_PATH;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
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
        final StringBuilder stringBuilder = new StringBuilder();

        Arrays.asList(gson.fromJson(readCitiesFileContent(), CityImportDto[].class))
                .forEach(cityDto -> {
                    boolean isValid = this.validationUtil.isValid(cityDto);

                    if (cityRepository.findByCityName(cityDto.getCityName()).isPresent()) {
                        isValid = false;
                    }

                    if (!isValid) {
                        stringBuilder.append(String.format(INCORRECT_DATA_MESSAGE_FORMAT, "city"));
                        return;
                    }

                    final City city = this.modelMapper.map(cityDto, City.class);
                    city.setCountry(countryRepository.findById(cityDto.getCountry()).orElseThrow(IllegalArgumentException::new));

                    stringBuilder.append(String.format(SUCCESSFULLY_IMPORTED_DATA_MESSAGE_FORMAT,
                            "city",
                            cityDto.getCityName(),
                            cityDto.getPopulation()));

                    this.cityRepository.saveAndFlush(city);
                });

        return stringBuilder.toString().trim();
    }
}
