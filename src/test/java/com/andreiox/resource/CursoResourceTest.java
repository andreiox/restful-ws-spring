package com.andreiox.resource;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.web.client.RestTemplate;

import com.andreiox.Main;
import com.andreiox.entidade.Curso;

public class CursoResourceTest {

	@BeforeClass
	public static void setup() {
		SpringApplication.run(Main.class);
	}

	@Test
	public void criarTest() {
		RestTemplate rest = new RestTemplate();
		Curso curso = new Curso(4, "git gud kid", "1200hs");
		String response = rest.postForObject("http://localhost:8080/cursos/new", curso, String.class);
		Assert.assertTrue(response.equals("Curso criado com sucesso!"));
	}

	@Test
	public void atualizarTest() {
		RestTemplate rest = new RestTemplate();
		Curso curso = new Curso(2, "Básico Spring Boot", "2hs");
		String response = rest.postForObject("http://localhost:8080/cursos/update", curso, String.class);
		Assert.assertTrue(response.equals("Curso atualizado com sucesso!"));
	}

	@Test
	public void buscarTest() {
		RestTemplate rest = new RestTemplate();
		Curso c = rest.getForObject("http://localhost:8080/cursos/1", Curso.class);
		Assert.assertTrue(c.getNome().equals("Workshop Rest"));
	}

	@Test
	public void listarTest() throws Exception {
		RestTemplate rest = new RestTemplate();
		List<Curso> cursos = Arrays.asList(rest.getForObject("http://localhost:8080/cursos", Curso[].class));
		Assert.assertTrue(cursos.size() > 0);
	}

	@Test
	public void deletarTest() {
		RestTemplate rest = new RestTemplate();

		int quantidadeAntes = rest.getForObject("http://localhost:8080/cursos", Curso[].class).length;
		rest.delete("http://localhost:8080/cursos/3");
		int quantidadeDepois = rest.getForObject("http://localhost:8080/cursos", Curso[].class).length;

		Assert.assertTrue(quantidadeDepois < quantidadeAntes);
	}

}