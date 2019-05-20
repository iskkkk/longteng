package com.alon.amqp.model;

import java.io.Serializable;

/**
 * @ClassName Employee
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/16 14:22
 * @Version 1.0
 **/
public class Employee implements Serializable {

    private String empno;

    private String name;

    private Integer age;

    public Employee(String empno, String name, Integer age) {
        this.empno = empno;
        this.name = name;
        this.age = age;
    }

    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
