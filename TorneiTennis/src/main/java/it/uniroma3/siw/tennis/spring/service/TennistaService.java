package it.uniroma3.siw.tennis.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.tennis.spring.model.Tennista;
import it.uniroma3.siw.tennis.spring.repository.TennistaRepository;

@Service
public class TennistaService {

    @Autowired
    protected TennistaRepository tennistaRepository;

    /**
     * This method retrieves a User from the DB based on its ID.
     * @param id the id of the User to retrieve from the DB
     * @return the retrieved User, or null if no User with the passed ID could be found in the DB
     */
    @Transactional
    public Tennista getTennista(Long id) {
        Optional<Tennista> result = this.tennistaRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * This method saves a User in the DB.
     * @param tennista the User to save into the DB
     * @return the saved User
     * @throws DataIntegrityViolationException if a User with the same email
     *                              as the passed User already exists in the DB
     */
    @Transactional
    public Tennista saveUser(Tennista tennista) {
        return this.tennistaRepository.save(tennista);
    }

    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     */
    @Transactional
    public List<Tennista> getAllTennisti() {
        List<Tennista> result = new ArrayList<>();
        Iterable<Tennista> iterable = this.tennistaRepository.findAll();
        for(Tennista tennista : iterable)
            result.add(tennista);
        return result;
    }
}
