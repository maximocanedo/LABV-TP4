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
    public void signupMedico(Medico medico) {
        repository.add(medico);
    }

    @Override
    public Optional<Medico> findById(int id) {
        return repository.getById(id);
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
	public List<Object[]> listMedicosLegajoAscP2() {
		return repository.listMedicosLegajoAscP2();
	}
	
	public List<Integer> TodosMedicosXLegajoP4(){
		return repository.TodosMedicosXLegajoP4();
	}
	
	public Medico getDoctorWithHighestFile() {
		return repository.medicoMayorLegajoP5();
	}
	
	public List<Medico> listOrderByFileDescending(int page, int size) {
		return repository.listOrderByFileDescending(page, size);
	}
	
	public List<Medico> listOrderByFileDescending() {
		return this.listOrderByFileDescending(1, 10);
	}
	
	public List<Object[]> getTurnosMedicoEnFecha(int legajo, LocalDate fecha) {
        return repository.getTurnosMedicoEnFecha(legajo, fecha);
    }
	
	@Override
    public List<Object[]> getTurnosMedicoEnRangoDeFechas(int legajo, LocalDate fechaInicio, LocalDate fechaFin) {
        return repository.getTurnosMedicoEnRangoDeFechas(legajo, fechaInicio, fechaFin);
    }
    
}
