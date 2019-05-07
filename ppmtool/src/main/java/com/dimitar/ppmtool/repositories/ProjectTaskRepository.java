package com.dimitar.ppmtool.repositories;

import com.dimitar.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findAllByProjectIdentifierOrderByPriority(final String projectIdentifier);

    ProjectTask findByProjectSequence(final String projectSequence);

}
