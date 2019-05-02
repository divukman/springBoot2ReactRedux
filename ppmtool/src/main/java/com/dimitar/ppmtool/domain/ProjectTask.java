package com.dimitar.ppmtool.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProjectTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false)
	private String projectSequence;

	@NotBlank(message = "Please include the project summary.")
	private String summary;

	private String acceptanceCriteria;

	private String status;

	private Integer priority;

	private Date dueDate;

	@Column(updatable = false)
	private Date created_At;


	private Date updated_At;

	// Many to one with the backlog

	@Column(updatable = false )
	private String projectIdentifier;

	@PrePersist
	private void onCreate() {
		this.created_At = new Date();
	}

	@PreUpdate
	private void onUpdate() {
		this.updated_At = new Date();
	}
}
