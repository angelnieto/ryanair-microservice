package es.rmc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloGradleController {

	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> helloGradle() {
        return new ResponseEntity<String>("Hello Gradle!", HttpStatus.OK);
    }

}
