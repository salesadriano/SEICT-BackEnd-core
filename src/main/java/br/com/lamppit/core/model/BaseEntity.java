package br.com.lamppit.core.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.lamppit.core.configuration.AuditListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@Audited
@EntityListeners(AuditListener.class)
public abstract class BaseEntity {

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
	private Long modifiedByUser;

	@JsonIgnore
	@CreatedBy
	@Column(updatable = false)
	private Long deletedByUser;

	@JsonIgnore
	@CreatedBy
	@Column(updatable = false)
	private Long createdByUser;
	
}
