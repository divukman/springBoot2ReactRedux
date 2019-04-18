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
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists.");
		}
	}

}
