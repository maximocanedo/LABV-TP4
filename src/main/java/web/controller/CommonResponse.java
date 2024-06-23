package web.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import web.entity.User;
import web.logicImpl.TicketLogicImpl;

import javax.servlet.http.HttpServletRequest;

public class CommonResponse<T> {

    private final HttpStatus status;
    private T body;
    private HttpHeaders headers = new HttpHeaders();
    private User currentUser;

    private final TicketLogicImpl tickets; // No se autowirea en static

    public CommonResponse(HttpStatus status) {
        this.status = status;
        this.tickets = new TicketLogicImpl(); // Instancia manual por no ser estático
    }

    public CommonResponse<T> body(T body) {
        this.body = body;
        return this;
    }

    public CommonResponse<T> header(String headerName, String headerValue) {
        this.headers.add(headerName, headerValue);
        return this;
    }

    public ResponseEntity<T> build() {
        return ResponseEntity.status(status)
                .headers(headers)
                .body(body);
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public CommonResponse<T> authenticate(HttpServletRequest request) {
        String fullToken = request.getHeader("Authorization");
        String tokenType = fullToken.split(" ")[0];
        String token = fullToken.split(" ")[1];
        String accessToken;

        if (tokenType.equals("Refresh")) {
            accessToken = generateAccessToken(token);
        } else {
            accessToken = token;
        }

        update(accessToken);
        return this;
    }

    public static <T> CommonResponse<T> auth(HttpServletRequest request) {
    	CommonResponse<T> e = new CommonResponse<T>(HttpStatus.ACCEPTED);
    	e.authenticate(request);
    	return e;
    }
    
    private String generateAccessToken(String refreshToken) {
        String accessToken = tickets.generateAccessToken(refreshToken);
        headers.add("Authorization", "Bearer " + accessToken);
        return accessToken;
    }

    private void update(String accessToken) {
        setCurrentUser(tickets.validateAccessToken(accessToken));
    }

    private void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
