package info.nmrony.tutorials.app.springonetomany.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customers")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @NotBlank
  private String firstName;

  @NotNull
  @NotBlank
  private String lastName;

  @NotNull
  private Long companyId;

  @NotNull
  private Long ownerId;

  @JsonIgnoreProperties({ "customer" })
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Project> projects = new HashSet<>();

  public void setProjects(Set<Project> projects) {
    this.projects.clear();
    if (projects != null && projects.size() > 0) {
      this.projects.addAll(projects);
    }
  }

  public void addProject(Project project) {
    projects.add(project);
    project.setCustomer(this);
  }

  public void removeProject(Project project) {
    projects.remove(project);
    project.setCustomer(null);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Customer other = (Customer) obj;
    return Objects.equals(id, other.getId());
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : super.hashCode();
  }
}
