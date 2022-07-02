package info.nmrony.tutorials.app.springonetomany.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "projects")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @NotBlank
  private String name;

  @NotNull
  private Long companyId;

  @NotNull
  private Long ownerId;

  @NotNull
  private Instant startDate = Instant.now();

  @NotNull
  private Instant endDate = Instant.now();

  @CreationTimestamp
  @Column(updatable = false)
  private Instant created = Instant.now();

  @UpdateTimestamp
  private Instant updated;

  @Column(columnDefinition = "boolean default false")
  private boolean archived = false;

  @JsonIgnoreProperties({ "projects" })
  @ManyToOne(fetch = FetchType.EAGER)
  private Customer customer;

  @JsonIgnoreProperties({ "project" })
  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Job> jobs = new HashSet<>();

  public void setJobs(Set<Job> jobs) {
    this.jobs.clear();
    if (jobs != null && jobs.size() > 0) {
      this.jobs.addAll(jobs);
    }
  }

  public void addJob(Job job) {
    jobs.add(job);
    job.setProject(this);
  }

  public void removeJob(Job job) {
    jobs.remove(job);
    job.setProject(null);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Project other = (Project) obj;
    return Objects.equals(id, other.getId());
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : super.hashCode();
  }

}