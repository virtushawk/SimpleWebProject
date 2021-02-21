package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.Correction;
import edu.epam.swp.exception.ServiceException;

import java.util.List;

public interface CorrectionService {

    boolean create(Correction correction) throws ServiceException;
    List<Correction> findAll() throws ServiceException;
    List<Correction> findUserCorrections(long accountId) throws ServiceException;
    boolean approveCorrection(long id) throws ServiceException;
    boolean delete(long id) throws ServiceException;
    boolean delete(long accountId,long correctionId) throws ServiceException;
    boolean editCorrection(long accountId,Correction correction) throws ServiceException;
}
