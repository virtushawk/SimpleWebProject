package edu.epam.swp.model.service.impl;

import edu.epam.swp.model.dao.CorrectionDao;
import edu.epam.swp.model.dao.impl.CorrectionDaoImpl;
import edu.epam.swp.model.entity.Correction;
import edu.epam.swp.exception.DaoException;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.CorrectionService;
import edu.epam.swp.model.validation.CorrectionValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * The implementation of {@link CorrectionService}. Contains methods to work with Correction object.
 * @author romab
 * @see Correction
 */
public class CorrectionServiceImpl implements CorrectionService {

    private static final Logger logger = LogManager.getLogger(CorrectionServiceImpl.class);
    private static final CorrectionService instance = new CorrectionServiceImpl();
    private CorrectionDao dao = CorrectionDaoImpl.getInstance();

    private CorrectionServiceImpl() {}

    /**
     * Gets instance.
     * @return Instance.
     */
    public static CorrectionService getInstance() {
        return instance;
    }

    /**
     * Creates correction.
     * @param correction Correction object.
     * @return true if correction was created, false otherwise.
     * @throws ServiceException If DaoException was  thrown.
     */
    @Override
    public boolean create(Correction correction) throws ServiceException {
        boolean flag;
        String name = correction.getName();
        String text = correction.getText();
        if ((!CorrectionValidator.isName(name)) || (!CorrectionValidator.isText(text))) {
            logger.info("Invalid credentials for correction");
            return false;
        }
        try {
            flag = dao.create(correction);
        } catch (DaoException e) {
            logger.error("Error occurred while creating correction",e);
            throw new ServiceException("Error occurred while creating correction",e);
        }
        return flag;
    }

    /**
     * Finds all corrections.
     * @return List containing corrections.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public List<Correction> findAll() throws ServiceException {
        List<Correction> corrections;
        try {
            corrections = dao.findAll();
        } catch (DaoException e) {
            logger.error("Error occurred while finding all corrections",e);
            throw new ServiceException("Error occurred while finding all corrections",e);
        }
        return corrections;
    }

    /**
     * Approves correction.
     * @param correctionId Correction's id.
     * @return True if correction was approved, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean approveCorrection(long correctionId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.approveCorrection(correctionId);
        } catch (DaoException e) {
            logger.error("Error occurred while approving correction",e);
            throw new ServiceException("Error occurred while approving correction",e);
        }
        return flag;
    }

    /**
     * Deletes correction.
     * @param correctionId Correction's id.
     * @return True if correction was deleted, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean delete(long correctionId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.delete(correctionId);
        } catch (DaoException e) {
            logger.error("Error occurred while deleting correction",e);
            throw new ServiceException("Error occurred while deleting correction",e);
        }
        return flag;
    }

    /**
     * Deletes user's correction.
     * @param accountId User's id.
     * @param correctionId Correction's id.
     * @return True if correction was deleted, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean delete(long accountId, long correctionId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.delete(accountId,correctionId);
        } catch (DaoException e) {
            logger.error("Error occurred while deleting correction",e);
            throw new ServiceException("Error occurred while deleting correction",e);
        }
        return flag;
    }

    /**
     * Finds User's corrections.
     * @param accountId User's id.
     * @return List containing user's corrections.
     * @throws ServiceException if DaoException was thrown.
     */
    @Override
    public List<Correction> findUserCorrections(long accountId) throws ServiceException {
        List<Correction> corrections;
        try {
            corrections = dao.findCorrectionsByAccountId(accountId);
        } catch (DaoException e) {
            logger.error("Error occurred while finding user's corrections",e);
            throw new ServiceException("Error occurred while finding user's corrections",e);
        }
        return corrections;
    }

    /**
     * Edits user's correction.
     * @param accountId  User's id.
     * @param correction Correction object.
     * @return true if correction was edited, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean editCorrection(long accountId, Correction correction) throws ServiceException {
        boolean flag;
        String name = correction.getName();
        String text = correction.getText();
        logger.info(name);
        logger.info(text);
        if ((!CorrectionValidator.isName(name)) || (!CorrectionValidator.isText(text))) {
            logger.info("Invalid credentials for correction");
            return false;
        }
        try {
            flag = dao.update(accountId,correction);
        } catch (DaoException e) {
            logger.error("Error occurred while updating correction",e);
            throw new ServiceException("Error occurred while updating correction",e);
        }
        return flag;
    }
}
