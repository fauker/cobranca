package br.com.fauker.cobranca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fauker.cobranca.model.Titulo;

public interface TituloRepository extends JpaRepository<Titulo, Long> {

	List<Titulo> findByDescricaoContaining(String descricao);

}
