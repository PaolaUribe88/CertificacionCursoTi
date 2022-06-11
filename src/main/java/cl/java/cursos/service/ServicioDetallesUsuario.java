package cl.java.cursos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.java.cursos.model.Administrador;
import cl.java.cursos.model.Estudiante;
import cl.java.cursos.repository.AdministradorRepository;
import cl.java.cursos.repository.EstudianteRepository;
import cl.java.cursos.security.UsuarioSeguridad;

@Service
public class ServicioDetallesUsuario implements UserDetailsService {

	@Autowired
	EstudianteRepository estudianteRepository;
	
	@Autowired
	AdministradorRepository administradorRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Estudiante> usuarioOpt = estudianteRepository.findByRut(username);
		if(usuarioOpt.isPresent()) {
			return new UsuarioSeguridad(usuarioOpt.get(), null);
		}else {
			Optional<Administrador> adminOpt = administradorRepository.findByUsername(username);
			if(adminOpt.isPresent()) {
				return new UsuarioSeguridad(null, adminOpt.get());
			}
		}
		throw new UsernameNotFoundException("Usuario no encontrado");
	}
	
	
}
