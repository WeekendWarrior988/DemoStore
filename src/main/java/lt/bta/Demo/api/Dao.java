package lt.bta.Demo.api;

import com.mysql.cj.xdevapi.Collection;
import lt.bta.Demo.helpers.EntityManagerHelper;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dao<T> implements AutoCloseable{

    final private EntityManager em;
    final private Class<T> clazz;

    public Dao(Class<T> clazz) {
        this.em = EntityManagerHelper.getEntityManager();
        this.clazz = clazz;
    }

    public List<T> list(int size, int skip) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> from = criteriaQuery.from(clazz);
        CriteriaQuery<T> select = criteriaQuery.select(from);
        TypedQuery<T> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(size);
        return typedQuery.getResultList();
    }

    public List<T> listAll() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> from = criteriaQuery.from(clazz);
        CriteriaQuery<T> select = criteriaQuery.select(from);
        TypedQuery<T> typedQuery = em.createQuery(select);
        return typedQuery.getResultList();
    }

    public T create(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    public T read(Object pk) {
        T entity = em.find(clazz, pk);
        return entity;
    }

    public T read(Object pk, String graph) {
        EntityGraph entityGraph = em.getEntityGraph(graph);
//        Map<String, Object> properties = new HashMap<>();
//        properties.put(EntityManagerHelper.FETCH_GRAPH, entityGraph);
//
//        T entity = em.find(clazz, pk, properties);
//        return entity;
        return em.find(clazz, pk, Collections.singletonMap(EntityManagerHelper.FETCH_GRAPH, entityGraph));
    }

    public T update(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        return entity;
    }

    public void delete(T entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    public List<T> findBy(String field, Object value) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> from = criteriaQuery.from(clazz);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get(field), value));
        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public void close() {
        em.close();
    }
}