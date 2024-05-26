package logicImpl;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

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
    public Medico findById(int id) {
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

	@Override
    public List<Object[]> getTurnosMedicoEnFecha(int legajo, LocalDate fecha) {
        return repository.getTurnosMedicoEnFecha(legajo, fecha);
    }
}
