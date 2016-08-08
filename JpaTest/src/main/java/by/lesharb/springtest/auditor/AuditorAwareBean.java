package by.lesharb.springtest.auditor;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareBean implements AuditorAware<String> {

	public String getCurrentAuditor() {
		return "LeshaRB";
	}

}
