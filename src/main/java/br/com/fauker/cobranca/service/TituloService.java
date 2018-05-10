package br.com.fauker.cobranca.service;

import java.util.List;
import java.util.Optional;

import javax.sql.rowset.FilteredRowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.fauker.cobranca.model.StatusTitulo;
import br.com.fauker.cobranca.model.Titulo;
import br.com.fauker.cobranca.repository.TituloRepository;
import br.com.fauker.cobranca.repository.filter.TituloFiltro;

@Service
public class TituloService {

	@Autowired
	private TituloRepository tituloRepostiory;
	
	public void salvar(Titulo titulo) {
		try {
			tituloRepostiory.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido!");
		}
	}

	public void excluir(Long codigo) {
		tituloRepostiory.deleteById(codigo);
	}
	
	public List<Titulo> todos() {
		return tituloRepostiory.findAll();
	}

	public Titulo obterPor(Long codigo) {
		return tituloRepostiory.findById(codigo).orElseThrow(() -> new IllegalArgumentException("Nenhum título encontrado com o parametro informado"));
	}

	public String receber(Long codigo) {
		Optional<Titulo> tituloOptional = tituloRepostiory.findById(codigo);
		Titulo titulo = tituloOptional.get();
		titulo.setStatus(StatusTitulo.RECEBIDO);
		tituloRepostiory.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
	}

	public List<Titulo> obterPor(TituloFiltro filtro) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		return tituloRepostiory.findByDescricaoContaining(descricao);
	}
	
}
