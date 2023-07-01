package info.nmrony.spring.tutorials.security_rbac.domain.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "permissions")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(max = 50)
    @Column(length = 50, unique = true)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    // ----------------- system related methods ------
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Permission other = (Permission) obj;
        return Objects.equals(name, other.getName());
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return "Permission[name = " + getName() + "]";
    }

}
