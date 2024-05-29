package entity;

public class Optional<T> {
	public T object;
	public Optional(T object) {
		set(object);
	}
	public Optional() {
		this(null);
	}
	public T get() {
		return this.object;
	}
	public void set(T object) {
		this.object = object;
	}
	public boolean isPresent() {
		return this.object != null;
	}
	public boolean isEmpty() {
		return this.object == null;
	}
}
