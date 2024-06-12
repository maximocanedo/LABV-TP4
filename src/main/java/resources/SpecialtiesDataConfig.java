package resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import entity.Specialty;

@Configuration
public class SpecialtiesDataConfig {
	
	@Bean
    public Specialty cirugiaGeneral() {
        Specialty specialty = new Specialty();
        specialty.setId(1);
        specialty.setName("Cirugía General");
        specialty.setDescription("Responsable de una amplia gama de condiciones quirúrgicas, como apendicectomías, hernias, entre otras.");
        return specialty;
    }

    @Bean
    public Specialty pediatria() {
        Specialty specialty = new Specialty();
        specialty.setId(2);
        specialty.setName("Pediatría");
        specialty.setDescription("Tratan a niños desde recién nacidos hasta la adolescencia, abordando problemas de salud infantil.");
        return specialty;
    }

    @Bean
    public Specialty ginecologiaObstetricia() {
        Specialty specialty = new Specialty();
        specialty.setId(3);
        specialty.setName("Ginecología y Obstetricia");
        specialty.setDescription("Se especializan en la salud reproductiva de las mujeres, desde el embarazo hasta la menopausia.");
        return specialty;
    }

    @Bean
    public Specialty cardiologia() {
        Specialty specialty = new Specialty();
        specialty.setId(4);
        specialty.setName("Cardiología");
        specialty.setDescription("Diagnostican y tratan enfermedades del corazón y los vasos sanguíneos.");
        return specialty;
    }

    @Bean
    public Specialty neurologia() {
        Specialty specialty = new Specialty();
        specialty.setId(5);
        specialty.setName("Neurología");
        specialty.setDescription("Se centran en trastornos del sistema nervioso, como migrañas, epilepsia y enfermedad de Parkinson.");
        return specialty;
    }

    @Bean
    public Specialty dermatologia() {
        Specialty specialty = new Specialty();
        specialty.setId(6);
        specialty.setName("Dermatología");
        specialty.setDescription("Tratan enfermedades de la piel, cabello y uñas, como acné, eczema y cáncer de piel.");
        return specialty;
    }

    @Bean
    public Specialty psiquiatria() {
        Specialty specialty = new Specialty();
        specialty.setId(7);
        specialty.setName("Psiquiatría");
        specialty.setDescription("Se ocupan de los trastornos mentales, como la depresión, la ansiedad y la esquizofrenia.");
        return specialty;
    }

    @Bean
    public Specialty oftalmologia() {
        Specialty specialty = new Specialty();
        specialty.setId(8);
        specialty.setName("Oftalmología");
        specialty.setDescription("Se dedican a la salud ocular, desde exámenes de la vista hasta cirugía de cataratas y corrección de la visión.");
        return specialty;
    }

    @Bean
    public Specialty otorrinolaringologia() {
        Specialty specialty = new Specialty();
        specialty.setId(9);
        specialty.setName("Otorrinolaringología");
        specialty.setDescription("Especializados en trastornos del oído, nariz y garganta, como otitis, sinusitis y amigdalitis.");
        return specialty;
    }

    @Bean
    public Specialty medicinaInterna() {
        Specialty specialty = new Specialty();
        specialty.setId(10);
        specialty.setName("Medicina Interna");
        specialty.setDescription("Tratan una amplia gama de enfermedades en adultos, desde diabetes hasta enfermedades cardíacas y pulmonares.");
        return specialty;
    }
	
}
