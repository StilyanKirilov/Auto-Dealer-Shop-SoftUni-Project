package softuni.springbootvehicles.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table(name = "offers")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    //    @NonNull
    private VehicleCategory category;

    //    @NonNull
    @NotNull(message = "Vehicle model is required")
    @ManyToOne
    private Model model;

    //    @NonNull
    // @PastOrPresent
    @NotNull(message = "Manufacturing year is required")
    @Min(1900)
    private Integer year;

    //    @NonNull
    @Positive
    @NotNull(message = "Vehicle mileage in km is required")
    private Integer mileage;

    //    @NonNull
    @NotNull(message = "Vehicle engine type is required")
    private EngineType engine;

    //    @NonNull
    @NotNull(message = "Vehicle transmission type is required")
    private TransmissionType transmission;

    @NonNull
    @NotNull(message = "Vehicle description is required")
    @Length(min = 2, max = 512,message = "Length must be between 2 and 512 symbols")
    private String description;

    //    @NonNull
    // @PastOrPresent
    @Positive
    @NotNull(message = "Vehicle price is required")
    private Double price;

    //    @NonNull
    @NotNull(message = "Vehicle image URL is required")
    @Length(min = 8, max = 512, message = "Length must be between 8 nad 512 symbols")
    private String imageUrl;

    @ManyToOne(optional = true)
    @ToString.Exclude
    private User seller;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long modelId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long sellerId;

    private Date created = new Date();
    private Date modified = new Date();

}
