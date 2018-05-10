package br.com.fauker.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fauker.cobranca.model.StatusTitulo;
import br.com.fauker.cobranca.model.Titulo;
import br.com.fauker.cobranca.repository.filter.TituloFiltro;
import br.com.fauker.cobranca.service.TituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	@Autowired
	private TituloService tituloService;
	
	private static final String CADASTRO_VIEW = "CadastroTitulo";
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		try {
			tituloService.salvar(titulo);
			redirectAttributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");
			return "redirect:/titulos/novo";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFiltro filtro) {
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", tituloService.obterPor(filtro));
		return mv;
	}
	
	@RequestMapping("/{codigo}")
	public ModelAndView edicao(@PathVariable Long codigo) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		Titulo titulo = tituloService.obterPor(codigo);
		mv.addObject(titulo);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public String receber(@PathVariable Long codigo) {
		return tituloService.receber(codigo);
	}
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes redirectAttributes) {
		tituloService.excluir(codigo);
		redirectAttributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/titulos";
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
	
}
