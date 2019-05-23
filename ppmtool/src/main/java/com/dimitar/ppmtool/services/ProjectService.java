package com.dimitar.ppmtool.services;

import com.dimitar.ppmtool.domain.Backlog;
import com.dimitar.ppmtool.domain.Project;
import com.dimitar.ppmtool.domain.User;
import com.dimitar.ppmtool.exceptions.ProjectIdException;
import com.dimitar.ppmtool.exceptions.ProjectNotFoundException;
import com.dimitar.ppmtool.repositories.BacklogRepository;
import com.dimitar.ppmtool.repositories.ProjectRepository;
import com.dimitar.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	private final ProjectRepository projectRepository;
	private final BacklogRepository backlogRepository;
	private final UserRepository userRepository;

	@Autowired
	public ProjectService(final ProjectRepository projectRepository, final BacklogRepository backlogRepository, final UserRepository userRepository) {
		this.projectRepository = projectRepository;
		this.backlogRepository = backlogRepository;
		this.userRepository = userRepository;
	}

	/**
	 * Ova metoda mu je trebala ic na 2, bar po mojoj logici, jedna za save, druga za update, ovako je cisti kupus.
	 * @param project
	 * @param username
	 * @return
	 */
	public Project saveOrUpdateProject(final Project project, final String username) {
		try {

			if (project.getId() != null) {
				Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
				if ( (existingProject != null) && !existingProject.getProjectLeader().equals(username)) {
					throw new ProjectNotFoundException("Not your project dude.");
				} else {
					throw new ProjectNotFoundException("Project with id:" + project.getId() + " and identifier: " + project.getProjectIdentifier() + " cannot be updated, it does not exist.");
				}
			}


			final User user = userRepository.findByUsername(username);

			project.setUser(user);
			project.setProjectLeader(username);

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

	public Project findProjectByIdentifier(final String projectIdentifier, final String username) {
		final Project project = projectRepository.findByProjectIdentifier(projectIdentifier != null ? projectIdentifier.toUpperCase() : null);
		if (project == null) {
			throw new ProjectIdException("Project ID '" + projectIdentifier.toUpperCase() + "' does not exists.");
		}

		if (!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project not found in your account.");
			// @todo: authorization can be done via annotation
		}

		return project;
	}

	public Iterable<Project> findAllProjects(final String username) {
		return projectRepository.findAllByProjectLeader(username);
	}

	public void deleteProjectByIdentifier(final String projectIdentifier, final String username) {
		projectRepository.delete(this.findProjectByIdentifier(projectIdentifier, username)) ;
	}
}
