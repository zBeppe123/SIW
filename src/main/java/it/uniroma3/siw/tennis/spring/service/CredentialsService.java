package it.uniroma3.siw.tennis.spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.tennis.spring.model.Credentials;
import it.uniroma3.siw.tennis.spring.repository.CredentialsRepository;

@Service
public class CredentialsService {
	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CredentialsRepository credentialsRepository;

	/** Restituisce Credentials tramite l'id.
	 * @param id Id del Credentials.
	 * @return Credetials cercato ,altrimenti null se non esiste.
	 */
	@Transactional
	public Credentials getCredentials(Long id) {
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}

	/** Restituisce Credentials tramite l'username.
	 * @param username Username dell'utene.
	 * @return Credetials cercato ,altrimenti null se non esiste.
	 */
	@Transactional
	public Credentials getCredentials(String username) {
		Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
		return result.orElse(null);
	}

	/** Salva le credenziali nel DB.
	 * @param credentials Credenziali da salvare.
	 * @return Credenziali salvato.
	 */
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		credentials.setRole(Credentials.ADMIN_ROLE);
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return this.credentialsRepository.save(credentials);
	}
}
