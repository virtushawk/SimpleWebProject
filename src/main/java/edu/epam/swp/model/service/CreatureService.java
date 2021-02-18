package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CreatureService {

    List<Creature> findNewCreatures(int limit) throws ServiceException;
    List<Creature> findPopularCreatures(int limit) throws ServiceException;
    boolean createCreature(Creature creature) throws ServiceException;
    Optional<Creature> get(long id) throws ServiceException;
    List<Creature> findAll() throws ServiceException;
    boolean changeImage(long id,String image) throws ServiceException;
    boolean changeUncheckedImage(long id,long accountId,String image) throws ServiceException;
    boolean editCreature(Creature creature) throws ServiceException;
    boolean editUncheckedCreature(long accountId,Creature creature) throws ServiceException;
    boolean delete(long id) throws ServiceException;
    boolean delete(long accountId,long creatureId) throws ServiceException;
    List<Creature> findUserCreatures(long id) throws ServiceException;
    List<Creature> findUncheckedCreatures() throws ServiceException;
    boolean approveCreature(long id) throws ServiceException;
    List<Creature> search(String text) throws ServiceException;
    List<Creature> findUserSuggestedCreatures(long id) throws ServiceException;
}
