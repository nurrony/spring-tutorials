package info.nmrony.tutorials.spring.one2many.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
