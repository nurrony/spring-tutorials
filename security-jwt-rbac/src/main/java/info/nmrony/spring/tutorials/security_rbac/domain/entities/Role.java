package info.nmrony.spring.tutorials.security_rbac.domain.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(max = 20)
    @Column(length = 20, unique = true)
    private String name;

    @ManyToMany
    private Set<Permission> permissions = Collections.emptySet();

    // ----------------- system related methods ------
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Role other = (Role) obj;
        return Objects.equals(name, other.getName());
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return "Role[name = " + getName() + "]";
    }

}
