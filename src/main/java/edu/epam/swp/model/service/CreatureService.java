package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CreatureService {

    public List<Creature> findNewCreatures() throws ServiceException;
    public boolean createCreature(Creature creature) throws ServiceException;
    public Optional<Creature> get(long id) throws ServiceException;
}
