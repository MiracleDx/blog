package com.dongx.blog.entity;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Demo
 *
 * @author: dongx
 * Description: demo实体
 * Created in: 2018-05-13 11:19
 * Modified by:
 */
@Data
@Entity
@Table(name = "Gu_demo")
public class Demo {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Integer age;


	public Demo() {
	}
}
