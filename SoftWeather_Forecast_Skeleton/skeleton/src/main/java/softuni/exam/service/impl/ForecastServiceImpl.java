package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastExportDto;
import softuni.exam.models.dto.ForecastImportWrapperDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;

import static softuni.exam.models.entity.DaysOfWeek.SUNDAY;
import static softuni.exam.util.Messages.INCORRECT_DATA_MESSAGE_FORMAT;
import static softuni.exam.util.Messages.SUCCESSFULLY_IMPORTED_DATA_MESSAGE_FORMAT;
import static softuni.exam.util.Paths.FORECASTS_XML_INPUT_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {
    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
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
        final StringBuilder stringBuilder = new StringBuilder();

        final ForecastImportWrapperDto forecastImportWrapperDto = xmlParser.fromFile(FORECASTS_XML_INPUT_PATH, ForecastImportWrapperDto.class);

        forecastImportWrapperDto.getForecasts().forEach(forecastDto -> {
            boolean isValid = this.validationUtil.isValid(forecastDto);

            final City city = cityRepository.findById(forecastDto.getCity()).orElseThrow(IllegalArgumentException::new);

            if (forecastRepository.findByDayOfWeekAndCityCityName(forecastDto.getDayOfWeek(), city.getCityName()).isPresent()) {
                isValid = false;
            }

            if (!isValid) {
                stringBuilder.append(String.format(INCORRECT_DATA_MESSAGE_FORMAT, "forecast"));
                return;
            }

            final Forecast forecast = this.modelMapper.map(forecastDto, Forecast.class);
            forecast.setCity(city);

            stringBuilder.append(String.format(SUCCESSFULLY_IMPORTED_DATA_MESSAGE_FORMAT,
                    "forecast",
                    forecastDto.getDayOfWeek(),
                    String.format("%.2f", forecastDto.getMaxTemperature())));

            this.forecastRepository.saveAndFlush(forecast);
        });

        return stringBuilder.toString().trim();
    }

    @Override
    public String exportForecasts() {
        final StringBuilder stringBuilder = new StringBuilder();

        forecastRepository
                .findAllByDayOfWeekAndCityPopulationLessThanOrderByMaxTemperatureDescIdAsc(SUNDAY, 150000)
                .stream()
                .map(forecast -> modelMapper.map(forecast, ForecastExportDto.class))
                .forEach(stringBuilder::append);

        return stringBuilder.toString().trim();
    }
}
