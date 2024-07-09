package com.example.Springboot_MariaDB_DTO_Demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>
{
	List<Student> findByCity(String city);
}
