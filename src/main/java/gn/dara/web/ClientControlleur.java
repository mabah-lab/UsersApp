package gn.dara.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gn.dara.dao.ClientRepository;
import gn.dara.entites.Client;

@Controller
public class ClientControlleur {
	@Autowired
	private ClientRepository cltRep;

	@RequestMapping(value = "/user/index")
	public String client(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "size", defaultValue = "10") int s,
			@RequestParam(name = "motCle", defaultValue = "") String mc) {

		Page<Client> pageClients = cltRep.chercher("%" + mc + "%", PageRequest.of(p, s));
		model.addAttribute("listClients", pageClients.getContent());
		int[] pages = new int[pageClients.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("size", s);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "clients";
	}

	@RequestMapping(value = "/admin/editer", method = RequestMethod.GET)
	public String editer(Model model, Long id) {
		Client client = cltRep.getOne(id);
		model.addAttribute("client", client);
		return "edit";
	}

	@RequestMapping(value = "/admin/supprimer", method = RequestMethod.GET)
	public String supprimer(Long id, String motCle, int page, int size) {
		cltRep.deleteById(id);
		return "redirect:/user/index?page=" + page + "&size=" + size + "&motCle=" + motCle;
	}

	@RequestMapping(value = "/admin/ajout")
	public String formAjout(Model model) {
		model.addAttribute("client", new Client());
		return "ajout";
	}

	@RequestMapping(value = "/admin/save", method = RequestMethod.POST)
	public String save(Model model, @Valid Client client, BindingResult bindingresult) {
		if (bindingresult.hasErrors()) {
			return "ajout";
		}
		cltRep.save(client);
		return "confirmClient";
	}

	@RequestMapping(value = "/")
	public String home() {
		return "redirect:/user/index";
	}

	@RequestMapping(value = "/403")
	public String accessDenied() {
		return "403";
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login.html";
	}
}
