package com.dimitar.ppmtool.services;

import com.dimitar.ppmtool.domain.Project;
import com.dimitar.ppmtool.exceptions.ProjectIdException;
import com.dimitar.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	private final ProjectRepository projectRepository;

	@Autowired
	public ProjectService(final ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	public Project saveOrUpdateProject(final Project project) {
		try {
			final String projectIdentifierUppercased = project.getProjectIdentifier().toUpperCase();
			project.setProjectIdentifier(projectIdentifierUppercased);

//			final Project projectInDB = projectRepository.findByProjectIdentifier(projectIdentifierUppercased);
//			if (projectInDB != null) {
//				projectRepository.delete(projectInDB);
//			}

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
