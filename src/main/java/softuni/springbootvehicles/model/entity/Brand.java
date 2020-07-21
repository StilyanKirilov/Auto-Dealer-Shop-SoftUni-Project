package softuni.springbootvehicles.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "brands")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    @Length(min = 2, max = 40)
    private String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Model> models = new ArrayList<>();

    private Date created = new Date();
    private Date modified = new Date();

    //This is Factory method
    public static Brand create(String name, Set<Model> models) {
        Brand brand = new Brand(name);
        models.forEach(model -> {
            model.setBrand(brand);
            brand.getModels().add(model);
        });
        return brand;

    }

}
