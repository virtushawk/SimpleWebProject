package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.Correction;
import edu.epam.swp.exception.DaoException;

import java.util.List;

/**
 * CorrectionDao interface contains methods for working with Correction object.
 * Extends {@link BaseDao}.
 * @see Correction
 * @author romab
 */
public interface CorrectionDao extends BaseDao<Correction> {

    /**
     * Approves correction.
     * @param correctionId Correction id.
     * @return true if approves successfully,otherwise false.
     * @throws DaoException if SQLException was thrown.
     */
    boolean approveCorrection(long correctionId) throws DaoException;

    /**
     * Finds corrections by account's id.
     * @param accountId account's id.
     * @return List of corrections.
     * @throws DaoException if SQLException was thrown.
     */
    List<Correction> findCorrectionsByAccountId(long accountId) throws DaoException;

    /**
     * Updates user's correction.
     * @param accountId User's id.
     * @param correction new Correction object.
     * @return true - if the correction was updated successfully,
     * false - if the correction has not been updated.
     * @throws DaoException if SQLException was thrown.
     */
    boolean update(long accountId,Correction correction) throws DaoException;

    /**
     * Deletes user's correction.
     * @param accountId User's id.
     * @param correctionId Correction's id.
     * @return true - if the correction was deleted successfully,
     * false - if the correction has not been deleted.
     * @throws DaoException if SQLException was thrown.
     */
    boolean delete(long accountId,long correctionId) throws DaoException;
}
