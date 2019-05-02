package com.dimitar.ppmtool.services;

import com.dimitar.ppmtool.domain.Backlog;
import com.dimitar.ppmtool.domain.Project;
import com.dimitar.ppmtool.exceptions.ProjectIdException;
import com.dimitar.ppmtool.repositories.BacklogRepository;
import com.dimitar.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	private final ProjectRepository projectRepository;
	private final BacklogRepository backlogRepository;

	@Autowired
	public ProjectService(final ProjectRepository projectRepository, final BacklogRepository backlogRepository) {
		this.projectRepository = projectRepository;
		this.backlogRepository = backlogRepository;
	}

	public Project saveOrUpdateProject(final Project project) {
		try {
			final String projectIdentifierUppercased = project.getProjectIdentifier().toUpperCase();
			project.setProjectIdentifier(projectIdentifierUppercased);

			// Ovo mi se cini lose, ali jebiga. Ako bi slucajno proslijedio ID, dobija bi problem.
			if (project.getId() == null) {
				final Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(projectIdentifierUppercased);
			} else {
				final Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifierUppercased);
				project.setBacklog(backlog);
			}

			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists.");
		}
	}

	public Project findProjectByIdentifier(final String projectIdentifier) {
		final Project project = projectRepository.findByProjectIdentifier(projectIdentifier != null ? projectIdentifier.toUpperCase() : null);
		if (project == null) {
			throw new ProjectIdException("Project ID '" + projectIdentifier.toUpperCase() + "' does not exists.");
		}
		return project;
	}

	public Iterable<Project> findAllProjects() {
		return projectRepository.findAll();
	}

	public void deleteProjectByIdentifier(final String projectIdentifier) {
		final Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if (project == null) {
			throw new ProjectIdException("Can not delete project with identifier: '" + projectIdentifier.toUpperCase() + "'. It does not exists.");
		}
		projectRepository.delete(project) ;
	}
}
