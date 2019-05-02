package com.dimitar.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Backlog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer PTSequence = 0;

	private String projectIdentifier;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="project_id", nullable = false)
	@JsonIgnore
	private Project project;


	// One to many with the project tasks
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "backlog")
	private List<ProjectTask> projectTaskList = new ArrayList<ProjectTask>();
}
