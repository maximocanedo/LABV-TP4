package generator;

public interface IEntityGenerator<T> {
	
	T generate();
	
	T save();
	
}
