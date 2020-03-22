package ru.typik.springboot.rest.incrementService.controllers;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.typik.springboot.rest.incrementService.model.IncrementRequest;
import ru.typik.springboot.rest.incrementService.model.IncrementResponse;

@RestController
public class IncrementServiceController {

	private static final String URI_INCREMENTS = "/increments";

	private final static String PATTERN = "(\\d+ )*\\d+";

	@PostMapping(URI_INCREMENTS)
	public IncrementResponse increment(@RequestBody IncrementRequest request, HttpServletResponse httpServletResponse) {
		if (request == null || request.getRequest() == null || !request.getRequest().matches(PATTERN)) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
			return null;
		}
		IncrementResponse response = new IncrementResponse();
		response.setResponse(Stream.of(request.getRequest().split(" ")).map(el -> Integer.parseInt(el) + 1)
				.map(el -> el.toString()).collect(Collectors.joining(" ")));
		return response;
	}

}
