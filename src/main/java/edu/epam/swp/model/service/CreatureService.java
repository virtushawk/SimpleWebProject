package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.exception.ServiceException;

import java.util.List;

public interface CreatureService {

    public List<Creature> findNewCreatures() throws ServiceException;

}
