package logic;

import java.util.List;
import entity.Optional;

import entity.Paciente;

public interface IPacienteLogic {

	void signupPaciente(Paciente paciente);

	Optional<Paciente> findById(int id);

	List<Paciente> list(int page, int size);

	List<Paciente> list();

}