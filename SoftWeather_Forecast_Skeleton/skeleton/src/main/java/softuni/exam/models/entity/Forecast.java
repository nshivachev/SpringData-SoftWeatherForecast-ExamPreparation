package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "forecasts")
public class Forecast extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DaysOfWeek dayOfWeek;

    @Min(-20)
    @Max(60)
    @Column(name = "max_temperature", nullable = false)
    private Double maxTemperature;

    @Min(-50)
    @Max(40)
    @Column(name = "min_temperature", nullable = false)
    private Double minTemperature;

    @Column(nullable = false)
    private LocalTime sunrise;

    @Column(nullable = false)
    private LocalTime sunset;

    @ManyToOne
    private City city;
}
