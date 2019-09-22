package es.rmc.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.rmc.controller.RootController;
import springfox.documentation.annotations.ApiIgnore;

/** Default home (root) controller (redirect to swagger api docs)
 * 
 * @author rmc */
@Controller
@ApiIgnore
public class RootControllerImpl implements RootController{
	
    @RequestMapping(value = "/")
    public String index() {
    	return "redirect:swagger-ui.html";
    }
}