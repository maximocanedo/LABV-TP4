package web.entity.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseContainer<T> {
	
	public static <F> ResponseContainer<F> of(F content) {
		return new ResponseContainer<F>(content);
	}
	
	private T content;
	
	public ResponseContainer() {
		
	}
	
	public ResponseContainer(T content) {
		this.setContent(content);
	}
	
	@JsonProperty("content")
	public T getContent() {
		return this.content;
	}
	
	public void setContent(T content) {
		this.content = content;
	}
}
