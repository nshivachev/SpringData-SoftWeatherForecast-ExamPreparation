package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.DaysOfWeek;
import softuni.exam.util.LocalTimeAdapter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastImportDto {
    @NotNull
    @XmlElement(name = "day_of_week")
    private DaysOfWeek dayOfWeek;

    @DecimalMin("-20")
    @DecimalMax("60")
    @NotNull
    @XmlElement(name = "max_temperature")
    private Double maxTemperature;

    @DecimalMin("-50")
    @DecimalMax("40")
    @NotNull
    @XmlElement(name = "min_temperature")
    private Double minTemperature;

    @NotNull
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    @XmlElement
    private LocalTime sunrise;

    @NotNull
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    @XmlElement
    private LocalTime sunset;

    @XmlElement
    private Long city;
}
