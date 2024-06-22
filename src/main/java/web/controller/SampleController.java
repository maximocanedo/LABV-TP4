package web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SampleController {

	@GetMapping
	public String getSomething() {
		return "200 OK";
	}
	
}
