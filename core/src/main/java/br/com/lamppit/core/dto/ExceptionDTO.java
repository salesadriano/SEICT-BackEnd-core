package br.com.lamppit.core.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDTO {
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
	private String message;
	private List<ErrorDTO> errors;
}
