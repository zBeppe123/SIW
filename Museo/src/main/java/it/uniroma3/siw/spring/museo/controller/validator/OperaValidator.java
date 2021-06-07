package it.uniroma3.siw.spring.museo.controller.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.museo.model.Opera;
@Component
public class OperaValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Opera.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Opera opera=(Opera) target;
		if(opera.getAnno()>LocalDate.now().getYear()) {
			errors.reject("registra_opera_errors_annoErrato");
		}
	}

}
