package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonProperty;

import web.exceptions.CommonException;
import web.exceptions.ServerException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	public static class AuxException {
		private CommonException error;
		public AuxException() {}
		public AuxException(CommonException ex) {
			this.setError(ex);
		}
		@JsonProperty("error")
		public CommonException getError() {
			return error;
		}
		public void setError(CommonException ex) {
			this.error = ex;
		}
	}

    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public ResponseEntity<AuxException> handleCommonException(CommonException ex) {
        return ResponseEntity.status(ex.getHttpCode()).body(new AuxException(ex));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<AuxException> handleException(Exception ex) {
    	ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuxException(new ServerException()));
    }
}
