package com.rmn.toolkit.bankinfoapplication.repository;

import com.rmn.toolkit.bankinfoapplication.model.Department;
import com.rmn.toolkit.bankinfoapplication.model.Service;
import com.rmn.toolkit.bankinfoapplication.model.type.DepartmentType;
import com.rmn.toolkit.bankinfoapplication.model.type.ScheduleType;
import com.rmn.toolkit.bankinfoapplication.model.type.ServiceName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class DepartmentRepositoryImpl {

    private final EntityManager em;

    public List<Department> findAllDepartmentsByPredicates(List<ServiceName> serviceNames , DepartmentType departmentType , ScheduleType scheduleType) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> cq = cb.createQuery(Department.class);
        Root<Department> departments = cq.from(Department.class);
        List<Predicate> predicates = new ArrayList<>();

        if(serviceNames!=null){
            for(ServiceName serviceName : serviceNames) {
                Subquery<Long> subQuery = cq.subquery(Long.class);
                Root<Department> subQueryDepartment = subQuery.from(Department.class);
                Join<Service, Department> subQueryService = subQueryDepartment.join("services");
                subQuery.select(subQueryDepartment.get("id")).where(cb.equal(subQueryService.get("name"), serviceName));
                predicates.add(cb.in(departments.get("id")).value(subQuery));
            }
        }
        if(departmentType!=null){
            predicates.add( cb.equal(departments.get("type"), departmentType));
        }
        if(scheduleType!=null && !scheduleType.equals(ScheduleType.OPEN)){
            predicates.add(cb.equal(departments.get("scheduleType") , scheduleType));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Department> query = em.createQuery(cq);

        return query.getResultList();
    }
}
