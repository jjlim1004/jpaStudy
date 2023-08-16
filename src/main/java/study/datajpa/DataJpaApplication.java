package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing // auditing 을 위해서는 필요함
//@EnableJpaAuditing(modifyOnCreate = false) // 수정자와 수정일에 대한 정보는 null 로 들어감
@SpringBootApplication
//원래대로라면 base package 위치를 정해줘야한다.
//@EnableJpaRepositories(basePackages = "study.datajpa.repository")
//근데 spring boot는 알아서 다 정해줌 package study.datajpa; 이하의 package 를 전부다 component에 등록함
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	//BaseEntity 에서 작성자 수정자를 위한 Bean
	@Bean
	public AuditorAware<String> auditorProvider(){
		/*return new AuditorAware<String>() {
			@Override
			public Optional<String> getCurrentAuditor() {
				return Optional.of(UUID.randomUUID().toString());
			}
		};*/
		//위 코드와 같다.
		//실제로는 user 의 id 가 들어가야한다.
		//수정자와 작성자에 대한 정보를 이 Bean 에서 끌어간다.
		return () -> Optional.of(UUID.randomUUID().toString());
	}
}
