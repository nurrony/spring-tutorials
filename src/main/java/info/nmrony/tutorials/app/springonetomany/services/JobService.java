package info.nmrony.tutorials.app.springonetomany.services;

import org.springframework.stereotype.Service;

import info.nmrony.tutorials.app.springonetomany.domain.Job;
import info.nmrony.tutorials.app.springonetomany.domain.Project;
import info.nmrony.tutorials.app.springonetomany.respository.JobRepository;
import info.nmrony.tutorials.app.springonetomany.respository.ProjectRepository;
import info.nmrony.tutorials.app.springonetomany.utils.AppUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {
  private final JobRepository jobRepository;
  private final ProjectRepository projectRepository;

  public Job create(Job payload) {
    Project project = null;
    if (payload.getProject() != null && payload.getProject().getId() != null) {
      project = projectRepository.findByIdAndCompanyId(payload.getProject().getId(), payload.getCompanyId())
          .orElseThrow(() -> new RuntimeException("Project not found for id " + payload.getProject().getId()));
    }

    payload.setProject(project);
    return jobRepository.save(payload);
  }

  public Job update(Long id, Long companyId, Job payload) {
    Project project = null;
    var job = jobRepository.findByIdAndCompanyId(id, companyId)
        .orElseThrow(() -> new RuntimeException("Job not found for id " + id));

    if (payload.getProject() != null && payload.getProject().getId() != null) {
      project = projectRepository.findByIdAndCompanyId(payload.getProject().getId(), payload.getCompanyId())
          .orElseThrow(() -> new RuntimeException("Project not found for id " + payload.getProject().getId()));
    }

    payload.setProject(project);
    AppUtils.copyNonNullProperties(payload, job);
    return jobRepository.save(job);
  }

}
