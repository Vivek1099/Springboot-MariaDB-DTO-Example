package com.example.Springboot_MariaDB_DTO_Demo;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController 
{
	@Autowired
	StudentRepository srepo;
	
	@GetMapping("/test")
	public String test()
	{
		return "DTO test demo";
	}
	
	@PostMapping("/save")
	public String savedata(@RequestBody StudentDTO sdto) //Rough Coding
	{
		Student s=new Student();
		s.setName(sdto.getName());
		s.setCity(sdto.getCity());
		srepo.save(s);
		return "Data Saved";
	}
	
	@GetMapping("/show")
	public List<StudentDTO> showdata()
	{
		return srepo.findAll().stream().map(student ->{
			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setId(student.getId());
			studentDTO.setName(student.getName());
			studentDTO.setCity(student.getCity());
			
			return studentDTO;
		}).collect(Collectors.toList()); //use stream
	}
	
	@GetMapping("/byid/{id}")
	public StudentDTO ById(@PathVariable int id)
	{
		Student student = srepo.findById(id).get();
		StudentDTO studentDTO=new StudentDTO();
		BeanUtils.copyProperties(student, studentDTO); // there must be all the fields in entity as well as dto 
		return studentDTO;
	}
	
	@DeleteMapping("delbyid/{id}")
	public String DelById(@PathVariable int id)
	{
		srepo.deleteById(id);
		return "Data Deleted";
	}
	
	@PutMapping("/update/{sid}")
	public StudentDTO Update(@RequestBody StudentDTO studentDTO, @PathVariable int id)
	{
		Student student = srepo.findById(id).get();
		student.setName(studentDTO.getName());
		student.setCity(studentDTO.getCity());
		srepo.save(student);
		return studentDTO;
	}
	
	@GetMapping("bycity/{scity}")
	public List<StudentDTO> ByCity(@PathVariable String city)
	{
		return srepo.findByCity(city).stream().map(student->
		{
			StudentDTO studentDTO= new StudentDTO();
			studentDTO.setName(student.getName());
			studentDTO.setCity(student.getCity());
			studentDTO.setId(student.getId());
			return studentDTO;
		}).collect(Collectors.toList());
	}
}
