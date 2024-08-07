package com.ideas2it.sports.service;

import java.util.Set;

import com.ideas2it.exceptions.EmployeeException;
import com.ideas2it.model.Sport;

/**
 * <p>
 * Interface for SportService to  handle sport related operation.
 * </p>
 * @author  Kishore 
 * @version 1.0 
 */
public interface SportService {
    
    /**
     * Adds a new sport to the repository.
     *
     * @param id - The unique identifier for the sport.
     * @param name - The name of the sport
     */
    public void addSport(int id, String name) throws EmployeeException;

    /**
     * Remove a sport from the repository by its Id.
     *
     * @param id - The unique identifier of the sport to be removed.
     * @throws IllegalArgumentException if the sport with the given ID is not found.
     */
    public void removeSport(int id) throws IllegalArgumentException, EmployeeException;

    /**
     * Retrieves all sport from the repository.
     *
     * @return A List of all sport.
     */     
    public Set<Sport> getAllSports() throws EmployeeException;

    /**
     * Retrieves a sport by its unique identifier.
     *
     *@param id - The unique identifier of the sport.
     *@return The sport with the given ID, or null if not found.
     */
    public Sport getSportById(int id) throws EmployeeException;

    /**
     * Updates the details of an existing sport.
     *
     * @param id - the unique identifier of the sport to the updated.
     * @param name - The new name for the sport.
     * @thows IllegalArgumentException if the sport with the given ID is not found.
     */
    public void updateSport(int id, String name) throws IllegalArgumentException, EmployeeException;

}


