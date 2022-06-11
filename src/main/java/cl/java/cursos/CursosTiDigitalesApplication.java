package cl.java.cursos;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import cl.java.cursos.model.Administrador;
import cl.java.cursos.model.Curso;
import cl.java.cursos.model.Estudiante;
import cl.java.cursos.repository.CursoRepository;
import cl.java.cursos.service.AdministradorService;
import cl.java.cursos.service.UsuarioService;

@SpringBootApplication
public class CursosTiDigitalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursosTiDigitalesApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner datosIniciales(AdministradorService adminService, UsuarioService userService, CursoRepository cursoR ) {
		return args -> {
			if(adminService.contarAdmin() == 0) {
				Administrador admin = Administrador.builder()
													.username("admin")
													.password("1234")
													.build();
				adminService.crearAdmin(admin);
														
			}
			
			if(userService.contarUsuarios() == 0) {
				Estudiante estudiante = Estudiante.builder()
											.nombres("Paola")
											.apellidos("Uribe")
											.direccion("bla bla 1212")
											.telefono("990101000")
											.email("correo@algo.com")
											.rut("16777417-k")
											.password("1234")
											.region("De La Araucania")
											.comuna("Temuco")
											.build()
									;
				userService.crearUsuario(estudiante);
			}
			if(cursoR.count()==0) {
				Curso curso = Curso.builder()
						.nombre("Aplicaciones Full Stack Java Trainee")
						.fechaInicio(LocalDate.of(2022, 07, 01))
						.fechaFin(LocalDate.of(2022, 12, 05))
						.cupos(30)
						.descripcion("Este plan está diseñado para desarrollar las habilidades necesarias para construir y mantener piezas de software en lenguaje Java bajo el paradigma de orientación a objetos; crear aplicaciones web en el full stack de Java; y operar base de datos para su consulta; a partir de especificaciones técnicas dadas; que den solución a las problemáticas de la organización; aplicando buenas prácticas de programación tendientes a lograr un producto con niveles de calidad acordes a las necesidades de la industria. El campo laboral corresponde a Organizaciones; ya sean públicas o privadas; transversal a todas las industrias (retail; banca; salud; minería; manufactura; servicios); que realicen desarrollo; implementación y mantenimiento de software; ya sean productivas; servicios; gubernamentales; o que prestan servicios TI a otras organizaciones; que requieran reclutamiento de desarrolladores trainee para procesos de selección; entrenamiento y ponerlos posteriormente a disposición de los equipos de proyectos de software en un rol Junior o Trainee")
						.imagen(Files.readAllBytes(Paths.get("src/main/resources/static/img/java.jpg")))
						.build();
				
				
				Curso cursoMark = Curso.builder()
						.nombre("Marketing Digital")
						.fechaInicio(LocalDate.of(2022, 07, 20))
						.fechaFin(LocalDate.of(2022, 12, 27))
						.cupos(30)
						.descripcion("Desarrolla tu habilidad creativa en este cuaderno de trabajo, donde generarás ideas increíbles y extrañas, que te ayudarán a abrir tu mente para tener ideas que realmente ayuden a tu marca y a tus clientes. Desarrolla el nombre de tu marca, la personalidad, el tono de voz, los mensajes y posibles campañas, que sean tan fantasiosas como las que te muestra Granatta en el curso.")
						.imagen(Files.readAllBytes(Paths.get("src/main/resources/static/img/marketing.png")))
						.build();
				
				Curso cursoInsta = Curso.builder()
						.nombre("Manejo de Crisis Instafood")
						.fechaInicio(LocalDate.of(2022, 07, 20))
						.fechaFin(LocalDate.of(2022, 12, 27))
						.cupos(30)
						.descripcion("No permitas que las crisis en redes sociales te tomen a ti o a tu empresa por sorpresa. Utiliza herramientas como Google Alerts y Buffer para monitorear tu marca y crear estrategias capaces de contener y enfrentar una crisis.\n"
								+ "\n"
								+ "Definir las posibles crisis de tu marca\n"
								+ "Crear un plan de defensa\n"
								+ "Reaccionar ante una crisis\n"
								+ "Monitorear la conversación en redes sociales sobre tu marca")
						.imagen(Files.readAllBytes(Paths.get("src/main/resources/static/img/crisis.png")))
						.build();
				
				cursoR.save(curso);
				cursoR.save(cursoMark);
				cursoR.save(cursoInsta);
			}
		};
	}
	
	

}
