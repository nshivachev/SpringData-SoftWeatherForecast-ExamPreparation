package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "forecasts")
public class Forecast extends BaseEntity {
    @Column(name = "day_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private DaysOfWeek dayOfWeek;

    @Column(name = "max_temperature", nullable = false)
    @Min(-20)
    @Max(60)
    private Double maxTemperature;

    @Column(name = "min_temperature", nullable = false)
    @Min(-50)
    @Max(40)
    private Double minTemperature;

    @Column(nullable = false)
    private LocalTime sunrise;

    @Column(nullable = false)
    private LocalTime sunset;

    @ManyToOne
    private City city;
}
