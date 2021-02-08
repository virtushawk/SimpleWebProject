package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CreatureService {

    List<Creature> findNewCreatures() throws ServiceException;
    List<Creature> findPopularCreatures() throws ServiceException;
    boolean createCreature(Creature creature) throws ServiceException;
    Optional<Creature> get(long id) throws ServiceException;
}
