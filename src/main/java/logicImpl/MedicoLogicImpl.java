package logicImpl;

import java.time.LocalDate;
import java.util.List;
import entity.Optional;
import exceptions.NotFoundException;
import dao.IMedicoDAO;
import daoImpl.MedicoDAOImpl;
import entity.Medico;
import logic.IMedicoLogic;

public class MedicoLogicImpl implements IMedicoLogic {

    private final IMedicoDAO repository;

    public MedicoLogicImpl() {
        this.repository = new MedicoDAOImpl();
    }

    @Override
    public void add(Medico medico) {
        repository.add(medico);
    }

    @Override
    public Optional<Medico> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Medico> list(int page, int size) {
        return repository.list(page, size);
    }

    @Override
    public List<Medico> list() {
        return list(1, 15);
    }

	@Override
	public List<Object[]> listOnlyFileNumbersAndNames() {
		return repository.listOnlyFileNumbersAndNames();
	}
	
	@Override
    public List<Integer> listOnlyFileNumbers(){
		return repository.listOnlyFileNumbers();
	}
	
	@Override
    public Medico findDoctorWithHighestFileNumber() {
		return repository.findDoctorWithHighestFileNumber();
	}
	
	@Override
    public List<Medico> listOrderByFileDescending(int page, int size) {
		return repository.listOrderByFileDescending(page, size);
	}
	
	@Override
    public List<Medico> listOrderByFileDescending() {
		return this.listOrderByFileDescending(1, 10);
	}
	
	@Override
    public List<Object[]> getAppointmentsByDoctorAndDate(int legajo, LocalDate fecha) {
        return repository.getAppointmentsByDoctorAndDate(legajo, fecha);
    }
	
	@Override
    public List<Object[]> getAppointmentsByDoctorAndDateRange(int legajo, LocalDate fechaInicio, LocalDate fechaFin) {
        return repository.getAppointmentsByDoctorAndDateRange(legajo, fechaInicio, fechaFin);
    }

	@Override
	public Optional<Medico> findByFile(int file) {
		return repository.findByFile(file);
	}

	@Override
	public Optional<Medico> findById(int id, boolean includeInactive) {
		return repository.findById(id, includeInactive);
	}

	@Override
	public List<Medico> list(int page, int size, boolean includeInactive) {
		return repository.list(page, size, includeInactive);
	}

	@Override
	public void update(Medico medico) throws NotFoundException {
    	Optional<Medico> search = findById(medico.getId());
    	if(search.isEmpty()) throw new NotFoundException();
    	Medico original = search.get();
    	if (medico.getNombre() != null) original.setNombre(medico.getNombre());
        if (medico.getApellido() != null) original.setApellido(medico.getApellido());
        if (medico.getSexo() != null) original.setSexo(medico.getSexo());
        if (medico.getFechaNacimiento() != null) original.setFechaNacimiento(medico.getFechaNacimiento());
        if (medico.getDireccion() != null) original.setDireccion(medico.getDireccion());
        if (medico.getLocalidad() != null) original.setLocalidad(medico.getLocalidad());
        if (medico.getCorreo() != null) original.setCorreo(medico.getCorreo());
        if (medico.getTelefono() != null) original.setTelefono(medico.getTelefono());
        if (medico.getEspecialidad() != null) original.setEspecialidad(medico.getEspecialidad());
        if (medico.getUser() != null) original.setUser(medico.getUser());
		repository.update(original);
	}

	@Override
	@Deprecated
	public void erase(Medico medico) {
		repository.erase(medico);
	}

	@Override
	public void disable(int id) throws NotFoundException {
		repository.disable(id);
		
	}

	@Override
	public void enable(int id) throws NotFoundException {
		repository.disable(id);
	}
    
}
