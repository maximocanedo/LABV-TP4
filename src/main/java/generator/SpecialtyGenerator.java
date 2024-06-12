package generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entity.Specialty;
import logicImpl.SpecialtyLogicImpl;

@Component
public class SpecialtyGenerator implements IEntityGenerator<Specialty[]> {

    @Autowired
	private SpecialtyLogicImpl specialties;
   
    @Autowired
    private Specialty cirugiaGeneral;

    @Autowired
    private Specialty pediatria;
    
    @Autowired
    private Specialty ginecologiaObstetricia;

    @Autowired
    private Specialty cardiologia;

    @Autowired
    private Specialty neurologia;

    @Autowired
    private Specialty dermatologia;

    @Autowired
    private Specialty psiquiatria;

    @Autowired
    private Specialty oftalmologia;

    @Autowired
    private Specialty otorrinolaringologia;

    @Autowired
    private Specialty medicinaInterna;
	
	public SpecialtyGenerator() {}
	
	@Override
	public Specialty[] generate() {
		
        return new Specialty[] {
            cirugiaGeneral, pediatria, ginecologiaObstetricia, cardiologia, 
            neurologia, dermatologia, psiquiatria, oftalmologia, otorrinolaringologia,
            medicinaInterna
        };
	}

	@Override
	public Specialty[] save() {
        Specialty[] especialidades = generate();
        for (Specialty especialidad : especialidades) {
            specialties.add(especialidad);
        }
        return especialidades;
	}

}
