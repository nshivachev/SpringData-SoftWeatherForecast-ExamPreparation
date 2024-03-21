package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static softuni.exam.util.Messages.INCORRECT_DATA_MESSAGE_FORMAT;
import static softuni.exam.util.Messages.SUCCESSFULLY_IMPORTED_DATA_MESSAGE_FORMAT;
import static softuni.exam.util.Paths.COUNTRIES_JSON_INPUT_PATH;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
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
        StringBuilder stringBuilder = new StringBuilder();

        final Set<CountryImportDto> countryDtos = new HashSet<>(Arrays.asList(gson.fromJson(readCountriesFromFile(), CountryImportDto[].class)));

        final List<Country> countries = countryDtos.stream().filter(countryDto -> {
                    boolean isValid = this.validationUtil.isValid(countryDto);

                    if (!isValid) {
                        stringBuilder
                                .append(String.format(INCORRECT_DATA_MESSAGE_FORMAT, "country"))
                                .append(System.lineSeparator());

                        return isValid;
                    }

                    stringBuilder
                            .append(String.format(SUCCESSFULLY_IMPORTED_DATA_MESSAGE_FORMAT, "country", countryDto.getCurrency()))
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(countryDto -> this.modelMapper.map(countryDto, Country.class))
                .toList();

        this.countryRepository.saveAllAndFlush(countries);

        return stringBuilder.toString().trim();
    }
}
