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
    public Specialty add(Specialty record) {
    	dataManager.transact(session -> {
            session.save(record);
        });
    	return record;
    }

    @Override
    public Optional<Specialty> findById(int id) {
        return findById(id, false);
    }
    
    @Override
    public Optional<Specialty> findById(int id, boolean includeInactives) {
        final Optional<Specialty> optional = new Optional<>();
        dataManager.run(session -> {
            String hql = "FROM Specialty s WHERE s.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            optional.set((Specialty) query.uniqueResult());
        });
        return optional;
    }

    @Override
    public Specialty update(Specialty record) {
    	dataManager.transact(session -> {
            session.update(record);
        });
    	return record;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Specialty> search(SpecialtyQuery query) {
		final Optional<List<Specialty>> optional = new Optional<>();
		dataManager.run(session -> {
			Query q = query.toQuery(session);
			optional.set(q.list());
		});
		return optional.get();
	}
}
