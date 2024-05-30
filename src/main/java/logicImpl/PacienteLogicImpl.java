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
	public void add(Paciente paciente) {
		repository.add(paciente);
	}
	
	@Override
	public Optional<Paciente> findById(int id) {
		return repository.findById(id);
	}
	
	@Override
    public void update(Paciente paciente) {
		repository.update(paciente);
	}
	
	@Override
	public List<Paciente> list(int page, int size) {
		return repository.list(page, size);
	}
	
	@Override
    public List<Paciente> list() {
		return list(1, 15);
	}

	@Override
	public void erase(Paciente paciente) {
		repository.erase(paciente);
		
	}	
}
