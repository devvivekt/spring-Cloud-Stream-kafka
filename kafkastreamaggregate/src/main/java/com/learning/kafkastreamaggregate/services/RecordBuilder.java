package com.learning.kafkastreamaggregate.services;

import com.learning.kafkaproduceravro.model.DepartmentAggregate;
import com.learning.kafkaproduceravro.model.Employee;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RecordBuilder {

    public DepartmentAggregate init(){
        DepartmentAggregate aggregate = new DepartmentAggregate();
        aggregate.setEmployeeCount(0);
        aggregate.setTotalSalary(0);
        aggregate.setAvgSalary(0D);
        return aggregate;
    }

    public DepartmentAggregate aggregate(Employee emp, DepartmentAggregate agg){
        DepartmentAggregate aggregate = new DepartmentAggregate();
        aggregate.setEmployeeCount(agg.getEmployeeCount()+1);
        aggregate.setTotalSalary(agg.getTotalSalary()+emp.getSalary());
        aggregate.setAvgSalary((double) (agg.getTotalSalary()+emp.getSalary()/(agg.getEmployeeCount()+1)));
        return aggregate;
    }
    public DepartmentAggregate substract(Employee emp, DepartmentAggregate agg){
        DepartmentAggregate aggregate = new DepartmentAggregate();
        aggregate.setEmployeeCount(agg.getEmployeeCount()-1);
        aggregate.setTotalSalary(agg.getTotalSalary()-emp.getSalary());
        aggregate.setAvgSalary((double) (agg.getTotalSalary()-emp.getSalary()/(agg.getEmployeeCount()-1)));
        return aggregate;
    }

}
