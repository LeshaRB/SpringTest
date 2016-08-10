package by.lesharb.springtest.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@Component
@EnableJpaAuditing
public class AuditorAwareBean implements AuditorAware<String> {

	public String getCurrentAuditor() {
		return "LeshaRB";
	}

}
