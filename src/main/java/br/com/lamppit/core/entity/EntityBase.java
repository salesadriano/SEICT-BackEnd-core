package br.com.lamppit.core.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.lamppit.accesscontrol.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Audited
@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class EntityBase {

	@JsonIgnore
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@JsonIgnore
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@JsonIgnore
	@UpdateTimestamp
	private LocalDateTime deletedAt;

	@JsonIgnore
	@LastModifiedBy
	private User modifiedByUser;

	@JsonIgnore
	@CreatedBy
	@Column(updatable = false)
	private User deletedByUser;

	@JsonIgnore
	@CreatedBy
	@Column(updatable = false)
	private Operator createdByUser;
}