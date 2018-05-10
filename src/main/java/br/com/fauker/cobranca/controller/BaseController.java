package br.com.fauker.cobranca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {

	@RequestMapping("/")
	public String base() {
		return "redirect:/titulos";
	}
	
}
