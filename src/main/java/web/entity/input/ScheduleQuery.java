package web.entity.input;
import org.hibernate.Query;
import org.hibernate.Session;
import web.entity.Day;

public class ScheduleQuery implements Searchable {
    private String q;
    private FilterStatus status = FilterStatus.ONLY_ACTIVE;
    private Day day = null;
    private int page;
    private int size;

    public ScheduleQuery(String q, FilterStatus status) {
        setQueryText(q);
        setStatus(status);
    }

    public ScheduleQuery(String q) {
        this(q, FilterStatus.ONLY_ACTIVE);
    }

    public ScheduleQuery filterByDay(String day) {
        if (day == null || day.trim().isEmpty()) {
            setDay(null);
            return this;
        }
        Day d = Day.valueOf(day);
        setDay(d);

        return this;
    }

    public ScheduleQuery paginate(int page, int size) {
        this.page = page;
        this.size = size;
        return this;
    }

    public ScheduleQuery paginate(String page, String size) {
        try {
            this.page = Integer.parseInt(page);
            this.size = Integer.parseInt(size);
        } catch(NumberFormatException expected) {
            System.out.println(expected.getMessage());
        }
        return this;
    }

    @Override
    public Query toQuery(Session session) {
        StringBuilder hql = new StringBuilder("SELECT s FROM Schedule s WHERE 1 = 1 "); 
        if (getStatus() != FilterStatus.BOTH) {
            hql.append("AND s.active = :status ");
        }
        if (getQueryText() != null && !getQueryText().isEmpty()) {
            hql.append("AND (s.beginDay LIKE :queryText OR s.finishDay LIKE :queryText) ");
        }

        if (getDay() != null) {
            hql.append("AND (s.beginDay = :day OR s.finishDay = :day) ");
        }

        Query query = session.createQuery(hql.toString());

        if (getQueryText() != null && !getQueryText().isEmpty()) {
            query.setParameter("queryText", "%" + getQueryText() + "%");
        }

        if (getDay() != null) {
            query.setParameter("day", getDay());
        }

        switch (getStatus()) {
            case ONLY_ACTIVE:
                query.setParameter("status", true);
                break;
            case ONLY_INACTIVE:
                query.setParameter("status", false);
                break;
            default:
                break;
        }

        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query;
    }

    @Override
    public String getQueryText() {
        return q;
    }

    private void setQueryText(String q) {
        this.q = q;
    }

    public FilterStatus getStatus() {
        return status;
    }

    private void setStatus(FilterStatus status) {
        if (status == null) return;
        this.status = status;
    }

    @Override
    public int getPage() {
        return this.page;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    
}
