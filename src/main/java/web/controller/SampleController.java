package web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TP4_GRUPO3/api")
public class SampleController {

	@GetMapping
	public String getSomething() {
		return "ALALALA";
	}
	
}
