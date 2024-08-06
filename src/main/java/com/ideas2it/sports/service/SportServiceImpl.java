package com.ideas2it.sports.service;

import java.util.ArrayList;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ideas2it.exceptions.EmployeeException;
import com.ideas2it.model.Sport;
import com.ideas2it.sports.dao.SportRepository;
import com.ideas2it.sports.dao.SportRepositoryImpl;

/**
 * <p>
 * Implement if SportService interface to handle Sport-related operations.
 * </p>
 * @author  Kishore 
 * @version 1.0 
 */
public class SportServiceImpl implements SportService {
    private static final Logger logger = LogManager.getLogger(SportServiceImpl.class);
    private SportRepository sportRepository;

    public SportServiceImpl() {
        sportRepository = new SportRepositoryImpl();
    }

    /**
     * Adds a new sport to the repository.
     *
     * @param id - The unique identifier for the sport.
     * @param name - The name of the sport
     */
    @Override
    public void addSport(int id, String name) throws EmployeeException {
        Sport sport = new Sport(id, name);
        sportRepository.addSport(sport);
    }

    /**
     * Remove a sport from the repository by its Id.
     *
     * @param id - The unique identifier of the sport to be removed.
     * @thows IllegalArgumentException if the sport with the given ID is not found.
     */
    @Override
    public void removeSport(int id) throws IllegalArgumentException, EmployeeException {
        Sport sport = sportRepository.findSportById(id);
        if (sport != null) {
            sportRepository.deleteSport(id);
        } else {
            logger.info("Sport not found" +id);
            throw new IllegalArgumentException("Sport not found" +id);
        }
    }

    /**
     * Retrieves all sport from the repository.
     *
     * @return A List of all sport.
     */
    @Override     
    public Set<Sport> getAllSports() throws EmployeeException {
        return sportRepository.getAllSports();
    }
    
    /**
     * Retrieves a sport by its unique identifier.
     *
     *@param id - The unique identifier of the sport.
     *@return The sport with the given ID, or null if not found.
     */
    @Override
    public Sport getSportById(int id) throws EmployeeException {
        return sportRepository.findSportById(id);
    }

    /**
     * Updates the details of an existing sport.
     *
     * @param id - the unique identifier of the sport to the updated.
     * @param name - The new name for the sport.
     * @thows IllegalArgumentException if the sport with the given ID is not found.
     */
    @Override
    public void updateSport(int id, String name) throws IllegalArgumentException, EmployeeException {
        Sport sport = sportRepository.findSportById(id);
        if (sport != null) {
            sport.setName(name);
            sportRepository.updateSport(sport);
        } else {
            logger.info("Sport not found" +id);
            throw new IllegalArgumentException("Sport not found" +id);
        }
    }
}
