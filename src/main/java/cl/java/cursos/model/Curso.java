package cl.java.cursos.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Column(nullable = false)
	private String nombre;
	
	@NotNull
	@Future
	@Column(nullable = false)
	private LocalDate fechaInicio;
	
	@NotNull
	@Future
	@Column(nullable = false)
	private LocalDate fechaFin;
	
	@NotNull
	@Column(nullable = false)
	private int cupos;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String descripcion;
	
	@Lob
	private byte[] imagen;
	
	@Transient
	@AssertTrue(message = "Campo 'fechaFin' debe ser una fecha posterior a 'fechaInicio'")
	private boolean isFechaFinMayorQueFechaInicio() {
		if(fechaFin != null) {
			return fechaFin.isAfter(fechaInicio);
		}
		return false;
	}
}
