package it.uniroma3.siw.spring.museo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.museo.model.Credenziali;
import it.uniroma3.siw.spring.museo.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CredenzialiRepository credenzialiRepository;
	
	@Transactional
	public void saveCredenziali(Credenziali credenziali) {
		credenziali.setRole(Credenziali.DEFAULT_ROLE);
		credenziali.setPassword(this.passwordEncoder.encode(credenziali.getPassword()));
		
		this.credenzialiRepository.save(credenziali);
	}
	
	@Transactional
	public Credenziali getCredenziali(String username) {
		Optional<Credenziali> result = this.credenzialiRepository.findByUsername(username);
		return result.orElse(null);
	}
}
