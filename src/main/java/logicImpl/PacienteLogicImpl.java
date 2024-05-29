package logicImpl;

import java.util.List;
import entity.Optional;

import dao.IPacienteDAO;
import daoImpl.PacienteDAOImpl;
import entity.Paciente;
import logic.IPacienteLogic;

public class PacienteLogicImpl implements IPacienteLogic {
	
	private final IPacienteDAO repository;
	
	public PacienteLogicImpl() {
		repository = new PacienteDAOImpl();
	}
	
	@Override
	public void signupPaciente(Paciente paciente) {
		repository.add(paciente);
	}
	
	@Override
	public Optional<Paciente> findById(int id) {
		return repository.getById(id);
	}
	
	public void update(Paciente paciente) {
		repository.update(paciente);
	}
	
	@Override
	public List<Paciente> list(int page, int size) {
		return repository.list(page, size);
	}
	
	/* (non-Javadoc)
	 * @see logicImpl.UserLogic#list()
	 */
	@Override
	public List<Paciente> list() {
		return list(1, 15);
	}	
}
