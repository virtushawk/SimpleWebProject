package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.Correction;
import edu.epam.swp.model.exception.DaoException;

public interface CorrectionDao extends BaseDao<Correction> {

    boolean approveCorrection(long id) throws DaoException;
}
