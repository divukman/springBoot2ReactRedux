package com.dimitar.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


	@Column(updatable = false )
	private String projectIdentifier;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "backlog_id", nullable = false, updatable = false)
	@JsonIgnore
	private Backlog backlog;

	@PrePersist
	private void onCreate() {
		this.created_At = new Date();
	}

	@PreUpdate
	private void onUpdate() {
		this.updated_At = new Date();
	}
}
