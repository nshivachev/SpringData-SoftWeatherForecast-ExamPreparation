package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cities")
public class City extends BaseEntity {
    @Column(name = "city_name", unique = true, nullable = false)
    @Size(min = 2, max = 60)
    private String cityName;

    @Column
    @Size(min = 2)
    private String description;

    @Column(nullable = false)
    @Size(min = 500)
    private Integer population;

    @ManyToOne
    private Country country;
}
