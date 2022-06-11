package cl.java.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.java.cursos.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

	

}
