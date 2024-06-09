package generator;

import entity.Specialty;
import logic.ISpecialtyLogic;
import logicImpl.SpecialtyLogicImpl;

public class SpecialtyGenerator implements IEntityGenerator<Specialty[]> {

    private ISpecialtyLogic specialties;
	
	public SpecialtyGenerator() {
    	specialties = new SpecialtyLogicImpl();		
	}
	
	@Override
	public Specialty[] generate() {
        Specialty cirugiaGeneral = new Specialty();
        cirugiaGeneral.setId(1);
        cirugiaGeneral.setName("Cirugía General");
        cirugiaGeneral.setDescription("Responsable de una amplia gama de condiciones quirúrgicas, como apendicectomías, hernias, entre otras.");

        Specialty pediatria = new Specialty();
        pediatria.setId(2);
        pediatria.setName("Pediatría");
        pediatria.setDescription("Tratan a niños desde recién nacidos hasta la adolescencia, abordando problemas de salud infantil.");

        Specialty ginecologiaObstetricia = new Specialty();
        ginecologiaObstetricia.setId(3);
        ginecologiaObstetricia.setName("Ginecología y Obstetricia");
        ginecologiaObstetricia.setDescription("Se especializan en la salud reproductiva de las mujeres, desde el embarazo hasta la menopausia.");

        Specialty cardiologia = new Specialty();
        cardiologia.setId(4);
        cardiologia.setName("Cardiología");
        cardiologia.setDescription("Diagnostican y tratan enfermedades del corazón y los vasos sanguíneos.");

        Specialty neurologia = new Specialty();
        neurologia.setId(5);
        neurologia.setName("Neurología");
        neurologia.setDescription("Se centran en trastornos del sistema nervioso, como migrañas, epilepsia y enfermedad de Parkinson.");

        Specialty dermatologia = new Specialty();
        dermatologia.setId(6);
        dermatologia.setName("Dermatología");
        dermatologia.setDescription("Tratan enfermedades de la piel, cabello y uñas, como acné, eczema y cáncer de piel.");

        Specialty psiquiatria = new Specialty();
        psiquiatria.setId(7);
        psiquiatria.setName("Psiquiatría");
        psiquiatria.setDescription("Se ocupan de los trastornos mentales, como la depresión, la ansiedad y la esquizofrenia.");

        Specialty oftalmologia = new Specialty();
        oftalmologia.setId(8);
        oftalmologia.setName("Oftalmología");
        oftalmologia.setDescription("Se dedican a la salud ocular, desde exámenes de la vista hasta cirugía de cataratas y corrección de la visión.");

        Specialty otorrinolaringologia = new Specialty();
        otorrinolaringologia.setId(9);
        otorrinolaringologia.setName("Otorrinolaringología");
        otorrinolaringologia.setDescription("Especializados en trastornos del oído, nariz y garganta, como otitis, sinusitis y amigdalitis.");

        Specialty medicinaInterna = new Specialty();
        medicinaInterna.setId(10);
        medicinaInterna.setName("Medicina Interna");
        medicinaInterna.setDescription("Tratan una amplia gama de enfermedades en adultos, desde diabetes hasta enfermedades cardíacas y pulmonares.");
        
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
