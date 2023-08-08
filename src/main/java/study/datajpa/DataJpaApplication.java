package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//원래대로라면 base package 위치를 정해줘야한다.
//@EnableJpaRepositories(basePackages = "study.datajpa.repository")
//근데 spring boot는 알아서 다 정해줌 package study.datajpa; 이하의 package 를 전부다 component에 등록함
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

}
