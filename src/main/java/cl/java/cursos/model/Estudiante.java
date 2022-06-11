package cl.java.cursos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Estudiante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(nullable = false, unique = true, length = 10)
	private String rut;
	
	@NotNull
	@Column(nullable = false)
	private String nombres;
	
	@NotNull
	@Column(nullable = false)
	private String apellidos;
	
	@NotNull
	@Column(nullable = false)
	private String email;
	
	@NotNull
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String telefono;
	
	@Column(nullable = false)
	private String direccion;
	
	@Column(nullable = false)
	private String region;
	
	@Column(nullable = false)
	private String comuna;
	
	@Lob
	private byte[] imagen;
	
	@ManyToOne
	private Curso curso;
	
}
