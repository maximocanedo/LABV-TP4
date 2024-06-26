package web.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.ISpecialtyDAO;
import web.entity.Optional;
import web.entity.Specialty;
import web.entity.input.SpecialtyQuery;
import web.exceptions.NotFoundException;

@Component("specialtiesrepository")
public class SpecialtyDAOImpl implements ISpecialtyDAO {

	@Autowired
	private DataManager dataManager;
	
	public SpecialtyDAOImpl() {}
	
    @Override
    public void add(Specialty record) {
    	dataManager.transact(session -> {
            session.save(record);
        });
    }

    @Override
    public Optional<Specialty> findById(int id) {
        return findById(id, false);
    }
    
    @Override
    public Optional<Specialty> findById(int id, boolean includeInactives) {
        final Optional<Specialty> optional = new Optional<>();
        dataManager.run(session -> {
            String hql = "FROM Especialidad WHERE id = :id" + (includeInactives ? "" : " AND active");
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            optional.set((Specialty) query.uniqueResult());
        });
        return optional;
    }

    @Override
    public List<Specialty> list() {
        return list(1, 15);
    }

    @Override
    public List<Specialty> list(int page, int size) {
        return this.list(page, size, false);
    }

    @Override
    public void update(Specialty record) {
    	dataManager.transact(session -> {
            session.update(record);
        });
    }

    @Override
    public void disable(int id) throws NotFoundException {
    	Optional<Specialty> file = findById(id);
    	if(file.isEmpty()) throw new NotFoundException();
    	dataManager.transact(session -> {
            Specialty original = file.get();
            original.setActive(false);
            session.update(original);
        });
    }
    
    @Override
    public void enable(int id) throws NotFoundException {
    	Optional<Specialty> file = findById(id, true);
    	if(file.isEmpty()) throw new NotFoundException();
    	dataManager.transact(session -> {
            Specialty original = file.get();
            original.setActive(true);
            session.update(original);
        });
    }

	@Override
	public List<Specialty> list(boolean showInactiveRecords) {
		return this.list(1, 10, showInactiveRecords);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Specialty> list(int page, int size, boolean showInactiveRecords) {
		final Optional<List<Specialty>> optional = new Optional<>();
		dataManager.run(session -> {
            String hql = "SELECT e FROM Especialidad e" + (showInactiveRecords ? "" : " WHERE e.active");
            Query query = session.createQuery(hql);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            optional.set(((List<Specialty>) query.list()));
        });
        return optional.get();
	}

	@Override
	public List<Specialty> search(SpecialtyQuery query) {
		// TODO Auto-generated method stub
		return null;
	}
}
