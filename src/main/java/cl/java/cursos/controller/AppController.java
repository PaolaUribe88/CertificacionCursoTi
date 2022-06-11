package cl.java.cursos.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.java.cursos.model.Curso;
import cl.java.cursos.model.Estudiante;
import cl.java.cursos.repository.CursoRepository;


import cl.java.cursos.service.UsuarioService;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	CursoRepository cursoRepository;
	
	@GetMapping("/")
	public String index(Curso curso, Model modelo) {
		List<Curso> cursos = cursoRepository.findAll();
		modelo.addAttribute("cursos",cursos);
		return "index";
	}
	
	@GetMapping("/imagen/{id}")
	public ResponseEntity<byte[]> mostrarImagenes(@PathVariable("id") Long id) throws SQLException, IOException {
		Optional<Curso> curso = cursoRepository.findById(id);
		byte[] imagenBt = null;
		if(curso.isPresent()) {
			imagenBt = curso.get().getImagen();
		}
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagenBt);
	}
	
	@GetMapping("/registro")
	public String registrarEstudiante(Estudiante estudiante) {
		Authentication autenticar = SecurityContextHolder.getContext().getAuthentication();
        if (autenticar == null || autenticar instanceof AnonymousAuthenticationToken) {
        	return "registro";
        }
  
        return "redirect:/";
	}
	
	@PostMapping("/registro")
	public String procesarRegistro(@Valid Estudiante estudiante, BindingResult validacion) {
		if(validacion.hasErrors()) return "registro";
		
		usuarioService.crearUsuario(estudiante);
		return "redirect:/";
	}
	
	@GetMapping("/ingreso")
	public String login() {
		Authentication autenticar = SecurityContextHolder.getContext().getAuthentication();
        if (autenticar == null || autenticar instanceof AnonymousAuthenticationToken) {
            return "ingreso";
        }
		return "redirect:/";
	}
	
	
	@GetMapping("/curso/detalles/{id}")
	public String detallesCurso(@PathVariable("id")Curso curso, Model modelo) {
		modelo.addAttribute("curso", curso);
		return "detalles";
	}
}
