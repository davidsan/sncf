package fr.upmc.dar.sncf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RedirectController {

	/**
	 * Redirect /train/redirect?id=X to /train/X
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/train/redirect", method = RequestMethod.GET)
	public String train(@RequestParam(value = "id", required = true) String id,
			Model model) {
		return "redirect:/train/" + id;
	}

}
