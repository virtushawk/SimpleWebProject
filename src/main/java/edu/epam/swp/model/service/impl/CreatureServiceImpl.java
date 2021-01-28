package edu.epam.swp.model.service.impl;

import edu.epam.swp.model.dao.CreatureDao;
import edu.epam.swp.model.dao.impl.CreatureDaoImpl;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.exception.DaoException;
import edu.epam.swp.model.exception.ServiceException;
import edu.epam.swp.model.service.CreatureService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CreatureServiceImpl implements CreatureService {
    private static final Logger logger = LogManager.getLogger(CreatureServiceImpl.class);
    private static final CreatureServiceImpl instance = new CreatureServiceImpl();
    private static final CreatureDao dao = CreatureDaoImpl.getInstance();

    private CreatureServiceImpl() {}

    public static CreatureServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Creature> findNewCreatures() throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findNewCreatures();
            return creatures;
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
    }
}
