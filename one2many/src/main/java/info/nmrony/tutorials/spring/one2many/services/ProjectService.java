package info.nmrony.tutorials.spring.one2many.services;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import info.nmrony.tutorials.spring.one2many.domain.Project;
import info.nmrony.tutorials.spring.one2many.repositories.ProjectRepository;
import info.nmrony.tutorials.spring.one2many.utils.AppUtils;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Set<Project> listByCompany(Long companyId) {
        return projectRepository.findByCompanyId(companyId);
    }

    public List<Project> listAll(Long companyId) {
        return projectRepository.findAll();
    }

    public Project create(Project payload, Long companyId) {
        payload.setCompanyId(companyId);
        if (payload.getJobs() != null && payload.getJobs().size() > 0) {
            payload.getJobs().stream().forEach(job -> {
                job.setCompanyId(payload.getCompanyId());
                job.setOwnerId(payload.getOwnerId());
                payload.addJob(job);
            });
        }
        return projectRepository.save(payload);
    }

    public Project update(Long id, Long companyId, Project payload) {
        var project = projectRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new RuntimeException("Project Not found"));
        AppUtils.copyNonNullProperties(payload, project);
        payload.getJobs().forEach(job -> {
            job.setCompanyId(project.getCompanyId());
            job.setOwnerId(project.getOwnerId());
            project.addJob(job);
        });
        return projectRepository.save(project);
    }

    public void delete(Long id, Long companyId) {
        projectRepository.deleteByIdAndCompanyId(id, companyId);
    }
}
