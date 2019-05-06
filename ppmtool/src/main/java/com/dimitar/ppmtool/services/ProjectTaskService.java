package com.dimitar.ppmtool.services;

import com.dimitar.ppmtool.domain.Backlog;
import com.dimitar.ppmtool.domain.ProjectTask;
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

		//@todo: no backlog exception

		projectTask.setBacklog(backlog);
		Integer backLogSequence = backlog.getPTSequence();
		backLogSequence++;

		projectTask.setProjectSequence(projectIdentifier + "-" + backLogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);

		backlog.setPTSequence(backLogSequence);

		if (projectTask.getPriority() == null) {
			projectTask.setPriority(3);
		}

		if ( projectTask.getStatus() == null || projectTask.getStatus().trim().equalsIgnoreCase("") ) {
			projectTask.setStatus("TO_DO");
		}

		return projectTaskRepository.save(projectTask);
	}

	public List<ProjectTask> getProjectTasks(final String projectIdentifier) {
		return projectTaskRepository.findAllByProjectIdentifier(projectIdentifier);
	}
}
