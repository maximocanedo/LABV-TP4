package logicImpl;

import java.util.List;
import entity.Optional;

import dao.IPacienteDAO;
import daoImpl.PacienteDAOImpl;
import entity.Paciente;
import exceptions.NotFoundException;
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
    public void update(Paciente paciente) throws NotFoundException {
		Optional<Paciente> search = findById(paciente.getId());
		if(search.isEmpty()) throw new NotFoundException();
		Paciente original = search.get();
		if (paciente.getNombre() != null) original.setNombre(paciente.getNombre());
        if (paciente.getApellido() != null) original.setApellido(paciente.getApellido());
        if (paciente.getDni() != 0) original.setDni(paciente.getDni());
        if (paciente.getTelefono() != null) original.setTelefono(paciente.getTelefono());
        if (paciente.getDireccion() != null) original.setDireccion(paciente.getDireccion());
        if (paciente.getLocalidad() != null) original.setLocalidad(paciente.getLocalidad());
        if (paciente.getProvincia() != null) original.setProvincia(paciente.getProvincia());
        if (paciente.getFechaNacimiento() != null) original.setFechaNacimiento(paciente.getFechaNacimiento());
        if (paciente.getCorreo() != null) original.setCorreo(paciente.getCorreo());
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
	@Deprecated
	public void erase(Paciente paciente) {
		repository.erase(paciente);
		
	}

	@Override
	public Optional<Paciente> findById(int id, boolean includeInactives) {
		return repository.findById(id, includeInactives);
	}

	@Override
	public List<Paciente> list(int page, int size, boolean includeInactives) {
		return repository.list(page, size, includeInactives);
	}

	@Override
	public void disable(int id) throws NotFoundException {
		repository.disable(id);
	}

	@Override
	public void enable(int id) throws NotFoundException {
		repository.enable(id);		
	}	
}
