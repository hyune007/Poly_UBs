package com.poly.ubs.service;

import java.util.List;

/**
 * Generic interface for common CRUD operations
 * @param <T> Entity type
 * @param <ID> Id type
 */
public interface IGenericService<T, ID> {
    
    /**
     * Save an entity
     * @param entity the entity to save
     * @return the saved entity
     */
    T save(T entity);
    
    /**
     * Update an entity
     * @param entity the entity to update
     * @return the updated entity
     */
    T update(T entity);
    
    /**
     * Find an entity by id
     * @param id the id of the entity to find
     * @return the entity if found, null otherwise
     */
    T findById(ID id);
    
    /**
     * Find all entities
     * @return list of all entities
     */
    List<T> findAll();
    
    /**
     * Delete an entity by id
     * @param id the id of the entity to delete
     */
    void deleteById(ID id);
    
    /**
     * Check if an entity exists by id
     * @param id the id to check
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(ID id);
    
    /**
     * Count all entities
     * @return the number of entities
     */
    long count();
}