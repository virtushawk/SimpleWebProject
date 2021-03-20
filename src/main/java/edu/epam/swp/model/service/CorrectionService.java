package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.Correction;
import edu.epam.swp.exception.ServiceException;

import java.util.List;

/**
 * CorrectionService interface contains method to work with Correction object.
 * @author romab
 * @see Correction
 */
public interface CorrectionService {

    /**
     * Creates correction.
     * @param correction Correction object.
     * @return true if correction was created, false otherwise.
     * @throws ServiceException If DaoException was  thrown.
     */
    boolean create(Correction correction) throws ServiceException;

    /**
     * Finds all corrections.
     * @return List containing corrections.
     * @throws ServiceException If DaoException was thrown.
     */
    List<Correction> findAll() throws ServiceException;

    /**
     * Finds User's corrections.
     * @param accountId User's id.
     * @return List containing user's corrections.
     * @throws ServiceException if DaoException was thrown.
     */
    List<Correction> findUserCorrections(long accountId) throws ServiceException;

    /**
     * Approves correction.
     * @param correctionId Correction's id.
     * @return True if correction was approved, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean approveCorrection(long correctionId) throws ServiceException;

    /**
     * Deletes correction.
     * @param correctionId Correction's id.
     * @return True if correction was deleted, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean delete(long correctionId) throws ServiceException;

    /**
     * Deletes user's correction.
     * @param accountId User's id.
     * @param correctionId Correction's id.
     * @return True if correction was deleted, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean delete(long accountId,long correctionId) throws ServiceException;

    /**
     * Edits user's correction.
     * @param accountId  User's id.
     * @param correction Correction object.
     * @return true if correction was edited, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean editCorrection(long accountId,Correction correction) throws ServiceException;
}
