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

public class CorrectionServiceImpl implements CorrectionService {

    private static final Logger logger = LogManager.getLogger(CorrectionServiceImpl.class);
    private static final CorrectionService instance = new CorrectionServiceImpl();
    private CorrectionDao dao = CorrectionDaoImpl.getInstance();

    public static CorrectionService getInstance() {
        return instance;
    }

    private CorrectionServiceImpl() {}

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

    @Override
    public boolean approveCorrection(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.approveCorrection(id);
        } catch (DaoException e) {
            logger.error("Error occurred while approving correction",e);
            throw new ServiceException("Error occurred while approving correction",e);
        }
        return flag;
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.delete(id);
        } catch (DaoException e) {
            logger.error("Error occurred while deleting correction",e);
            throw new ServiceException("Error occurred while deleting correction",e);
        }
        return flag;
    }

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

    @Override
    public boolean editCorrection(long accountId, Correction correction) throws ServiceException {
        boolean flag;
        String name = correction.getName();
        String text = correction.getText();
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
