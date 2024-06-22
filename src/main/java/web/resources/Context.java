package web.resources;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Context extends AnnotationConfigApplicationContext {
	public Context() {
		super(
				DAOConfig.class,
				LogicConfig.class,
				GeneratorConfig.class,
				SpecialtiesDataConfig.class
			);
	}
}
