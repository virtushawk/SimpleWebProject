package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.Correction;
import edu.epam.swp.exception.DaoException;

import java.util.List;

public interface CorrectionDao extends BaseDao<Correction> {

    boolean approveCorrection(long id) throws DaoException;
    List<Correction> findCorrectionsByAccountId(long accountId) throws DaoException;
    boolean update(long accountId,Correction correction) throws DaoException;
    boolean delete(long accountId,long correctionId) throws DaoException;
}
