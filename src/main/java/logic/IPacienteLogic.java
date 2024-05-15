package logic;

import java.util.List;

import entity.Paciente;

public interface IPacienteLogic {

	void signupPaciente(Paciente paciente);

	Paciente findById(int id);

	List<Paciente> list(int page, int size);

	List<Paciente> list();

}