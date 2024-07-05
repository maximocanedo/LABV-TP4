package web.generator;

import web.entity.User;

public interface IEntityGenerator<T> {
	
	T generate();
	
	T save(User requiring);
	
}
