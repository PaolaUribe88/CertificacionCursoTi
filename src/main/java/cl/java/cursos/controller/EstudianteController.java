package cl.java.cursos.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cl.java.cursos.model.Curso;
import cl.java.cursos.model.Estudiante;
import cl.java.cursos.repository.CursoRepository;
import cl.java.cursos.repository.EstudianteRepository;
import cl.java.cursos.security.UsuarioSeguridad;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

	@Autowired
	EstudianteRepository estudianteRepository;
	
	@Autowired
	CursoRepository cursoRepository;
	
	@GetMapping("/index")//revisar
	public String panelEstudiante( Authentication usuarioAutenticado, Model modelo) {
		UsuarioSeguridad principal = (UsuarioSeguridad) usuarioAutenticado.getPrincipal();
		Estudiante estudiante = estudianteRepository.findById(principal.getEstudiante().getId()).get();
		modelo.addAttribute("estudiante", estudiante);
		return "estudiante/panel";
		
	}

	
	@GetMapping("/postular/{id}")
	public String postular(@PathVariable("id") Curso curso, Authentication usuarioAutenticado
		, Model modelo
		) {		
			UsuarioSeguridad principal = (UsuarioSeguridad) usuarioAutenticado.getPrincipal();
			Estudiante estudiante = principal.getEstudiante();
			estudiante.setCurso(curso);
			estudianteRepository.save(estudiante);
		
			return "redirect:/";
	}
	@GetMapping("/imagen/{id}")
	public ResponseEntity<byte[]> muestraImagenes(@PathVariable("id") Long id) throws SQLException, IOException {
		Optional<Estudiante> estudiante = estudianteRepository.findById(id);
		byte[] imageBytes = null;
		if(estudiante.isPresent()) {
			imageBytes = estudiante.get().getImagen();
			if(imageBytes == null) {
				imageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/img/placeholder.png"));
			}
		}
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
	}
	
	@PostMapping("/subirimagen")
	public String procesarImagen(@RequestParam("image") MultipartFile imagen, Principal principal) throws IOException {
		byte[] contenidoImagen = imagen.getBytes();
		
		Optional<Estudiante> estudiantePrincipal = estudianteRepository.findByRut(principal.getName());
		estudiantePrincipal.get().setImagen(contenidoImagen);
		estudianteRepository.saveAndFlush(estudiantePrincipal.get());
		
		return "redirect:/estudiante/index";
	}

}
