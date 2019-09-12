package es.rmc.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.rmc.controller.RootController;

/** Default home (root) controller (redirect to sswagger api docs)
 * 
 * @author rmc */
@Controller
public class RootControllerImpl implements RootController{
	
    @RequestMapping(value = "/")
    public String index() {
    	return "redirect:swagger-ui.html";
    }
}