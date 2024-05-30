package logicImpl;

import java.time.LocalDate;
import java.util.List;
import entity.Optional;
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
    
}
