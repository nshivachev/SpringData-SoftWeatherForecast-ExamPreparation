package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.City;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class ForecastExportDto {
    private Double maxTemperature;
    private Double minTemperature;
    private LocalTime sunrise;
    private LocalTime sunset;
    private City city;

    @Override
    public String toString() {
        return String.format(
                "City: %s:%n" +
                        "   -min temperature: %.2f%n" +
                        "   --max temperature: %.2f%n" +
                        "   --- sunrise: %s%n" +
                        "   ----sunset: %s%n",
                city.getCityName(),
                maxTemperature,
                minTemperature,
                sunrise,
                sunset);
    }
}
