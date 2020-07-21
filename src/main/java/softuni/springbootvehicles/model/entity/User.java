package softuni.springbootvehicles.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @Length(min = 2, max = 60)
    private String firstName;

    @NonNull
    @Length(min = 2, max = 60)
    private String lastName;

    @NonNull
    @Length(min = 3, max = 60)
    @Column(unique = true, nullable = false)
    @NotNull
    @EqualsAndHashCode.Include
    private String username;

    @NonNull
    @Length(min = 4, max = 80)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NonNull
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private Boolean active = true;

    @Length(min = 8,max = 512)
    private String imageUrl;

    @OneToMany(mappedBy = "seller")
    @ToString.Exclude
    @JsonIgnore
    private Collection<Offer> offers = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created = new Date();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modified = new Date();

}
