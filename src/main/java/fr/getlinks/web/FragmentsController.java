package fr.getlinks.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Duy Hai DOAN
 */
@Controller
public class FragmentsController
{

	@RequestMapping("/fragments/{interfaces}/{fragmentName}")
	public String homeFragment(@PathVariable String interfaces, @PathVariable String fragmentName, Model model)
	{
		return "fragments/" + interfaces + "/" + fragmentName;
	}

}
