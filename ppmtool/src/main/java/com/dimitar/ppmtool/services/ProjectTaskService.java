package com.dimitar.ppmtool.services;

import com.dimitar.ppmtool.domain.Backlog;
import com.dimitar.ppmtool.domain.Project;
import com.dimitar.ppmtool.domain.ProjectTask;
import com.dimitar.ppmtool.exceptions.ProjectIdException;
import com.dimitar.ppmtool.exceptions.ProjectNotFoundException;
import com.dimitar.ppmtool.repositories.BacklogRepository;
import com.dimitar.ppmtool.repositories.ProjectRepository;
import com.dimitar.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

	@Autowired
	BacklogRepository backlogRepository;

	@Autowired
	ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask(final String projectIdentifier, final ProjectTask projectTask) {
		final Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);


		if (backlog == null) {
			throw new ProjectNotFoundException("Project with id: '" + projectIdentifier + "' does not exist.");
		}

		projectTask.setBacklog(backlog);
		Integer backLogSequence = backlog.getPTSequence();
		backLogSequence++;

		projectTask.setProjectSequence(projectIdentifier + "-" + backLogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);

		backlog.setPTSequence(backLogSequence);

		if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
			projectTask.setPriority(3);
		}

		if ( projectTask.getStatus() == null || projectTask.getStatus().trim().equalsIgnoreCase("") ) {
			projectTask.setStatus("TO_DO");
		}

		return projectTaskRepository.save(projectTask);
	}

	public List<ProjectTask> getProjectTasks(final String projectIdentifier) {
		return projectTaskRepository.findAllByProjectIdentifierOrderByPriority(projectIdentifier);
	}

	public ProjectTask findByProjectSequence(final String projectIdentifier, final String projectSequence) {

		final Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		if (backlog == null) {
			throw new ProjectNotFoundException("Project with id '" + projectIdentifier + "' does not exist.");
		}

		final ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectSequence);
		if (projectTask == null) {
			throw new ProjectNotFoundException("Project task '" + projectSequence + "' not found.");
		}

		if (!projectTask.getProjectIdentifier().equalsIgnoreCase(projectIdentifier)) {
			throw new ProjectNotFoundException("Project task '" + projectSequence + "' does not exist in project '" + projectIdentifier + "'");
		}

		return projectTask;
	}

	public ProjectTask updateByProjectSequence(final ProjectTask updatedTask, final String projectIdentifier, final String pt_id) {
		ProjectTask projectTask = this.findByProjectSequence(projectIdentifier,pt_id);



		projectTask = updatedTask;

		return projectTaskRepository.save(projectTask);
	}

	public void deletePTByProjectSequence(final String projectIdentifier, final String projectSequence) {
		ProjectTask projectTask = this.findByProjectSequence(projectIdentifier,projectSequence);
		projectTaskRepository.delete(projectTask);
	}
}
