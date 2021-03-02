package edu.epam.swp.model.service.impl;

import edu.epam.swp.exception.DaoException;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.dao.CorrectionDao;
import edu.epam.swp.model.entity.Correction;
import edu.epam.swp.model.service.CorrectionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CorrectionServiceImplTest {

    @Mock
    private CorrectionDao dao;

    @InjectMocks
    private CorrectionService service;

    @BeforeClass
    public void beforeClass() {
        service = CorrectionServiceImpl.getInstance();
        MockitoAnnotations.openMocks(this);
    }

    @DataProvider
    public Object[][] createCorrectionData() {
        return new Object[][] {
                {new Correction.CorrectionBuilder().withName("test Name").withText("test text").build(),true},
                {new Correction.CorrectionBuilder().withName("плохое имя").withText("test text").build(),false},
                {new Correction.CorrectionBuilder().withName("text name").withText("плохой текст").build(),false}
        };
    }

    @DataProvider
    public Object[][] editCorrectionData() {
        return new Object[][] {
                {1,new Correction.CorrectionBuilder().withName("test Name for edit").withText("test Text for edit").build(),true},
                {2,new Correction.CorrectionBuilder().withName("плохое имя for edit").withText("test text for edit").build(),false},
                {3,new Correction.CorrectionBuilder().withName("text name for edit").withText("плохой текст for edit").build(),false},
                {4,new Correction.CorrectionBuilder().withName("").withText("test text for edit").build(),false},
                {5,new Correction.CorrectionBuilder().withName("text name for edit").withText("").build(),false}
        };
    }

    @Test(dataProvider = "createCorrectionData")
    public void createTest(Correction correction,boolean expected) throws DaoException, ServiceException {
        Mockito.when(dao.create(correction)).thenReturn(true);
        boolean actual = service.create(correction);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "createTest", expectedExceptions = ServiceException.class)
    public void createTestException() throws DaoException, ServiceException {
        Correction correction = new Correction.CorrectionBuilder().withName("Test name").withText("test Text").build();
        Mockito.when(dao.create(correction)).thenThrow(new DaoException());
        service.create(correction);
    }

    @Test
    public void findAllTest() throws DaoException, ServiceException {
        List<Correction> expected = new ArrayList<>();
        Mockito.when(dao.findAll()).thenReturn(expected);
        List<Correction> actual = service.findAll();
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "findAllTest",expectedExceptions = ServiceException.class)
    public void findAllTestException() throws DaoException, ServiceException {
        Mockito.when(dao.findAll()).thenThrow(new DaoException());
        service.findAll();
    }

    @Test
    public void approveCorrectionTrueTest() throws DaoException, ServiceException {
        long id = 9;
        Mockito.when(dao.approveCorrection(Mockito.anyLong())).thenReturn(true);
        boolean actual = service.approveCorrection(id);
        Assert.assertTrue(actual);
    }

    @Test
    public void approveCorrectionFalseTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.approveCorrection(Mockito.anyLong())).thenReturn(false);
        boolean actual = service.approveCorrection(id);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"approveCorrectionTrueTest","approveCorrectionFalseTest"},expectedExceptions = ServiceException.class)
    public void approveCorrectionTestException() throws DaoException, ServiceException {
        long id = 1;
        Mockito.when(dao.approveCorrection(Mockito.anyLong())).thenThrow(new DaoException());
        service.approveCorrection(id);
    }

    @Test
    public void deleteTrueTest() throws DaoException, ServiceException {
        long id = 1;
        Mockito.when(dao.delete(Mockito.anyLong())).thenReturn(true);
        boolean actual = service.delete(id);
        Assert.assertTrue(actual);
    }

    @Test
    public void deleteFalseTest() throws DaoException, ServiceException {
        long id = 1;
        Mockito.when(dao.delete(Mockito.anyLong())).thenReturn(false);
        boolean actual = service.delete(id);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"deleteTrueTest","deleteFalseTest"},expectedExceptions = ServiceException.class)
    public void deleteExceptionTest() throws DaoException, ServiceException {
        long id = 1;
        Mockito.when(dao.delete(Mockito.anyLong())).thenThrow(new DaoException());
        service.delete(id);
    }

    @Test
    public void deleteWithAccountIdTrueTest() throws DaoException, ServiceException {
        long accountId = 1;
        long correctionId = 1;
        Mockito.when(dao.delete(Mockito.anyLong(),Mockito.anyLong())).thenReturn(true);
        boolean actual = service.delete(accountId,correctionId);
        Assert.assertTrue(actual);
    }

    @Test
    public void deleteWithAccountIdFalseTest() throws DaoException,ServiceException {
        long accountId = 1;
        long correctionId = 1;
        Mockito.when(dao.delete(Mockito.anyLong(),Mockito.anyLong())).thenReturn(false);
        boolean actual = service.delete(accountId,correctionId);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"deleteWithAccountIdTrueTest","deleteWithAccountIdFalseTest"},expectedExceptions = ServiceException.class)
    public void deleteWithAccountIdExceptionTest() throws DaoException, ServiceException {
        long accountId = 1;
        long correctionId = 1;
        Mockito.when(dao.delete(Mockito.anyLong(),Mockito.anyLong())).thenThrow(new DaoException());
        service.delete(accountId,correctionId);
    }

    @Test
    public void findUserCorrectionsTest() throws DaoException, ServiceException {
        List<Correction> expected = new ArrayList<>();
        long id = 1;
        Mockito.when(dao.findCorrectionsByAccountId(Mockito.anyLong())).thenReturn(expected);
        List<Correction> actual = service.findUserCorrections(id);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "findUserCorrectionsTest",expectedExceptions = ServiceException.class)
    public void findUserCorrectionExceptionTest() throws DaoException, ServiceException {
        long id = 1;
        Mockito.when(dao.findCorrectionsByAccountId(Mockito.anyLong())).thenThrow(new DaoException());
        service.findUserCorrections(id);
    }

    @Test(dataProvider = "editCorrectionData")
    public void editCorrectionTest(long accountId,Correction correction,boolean expected) throws DaoException, ServiceException {
        Mockito.when(dao.update(accountId,correction)).thenReturn(true);
        boolean actual = service.editCorrection(accountId,correction);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "editCorrectionTest",expectedExceptions = ServiceException.class)
    public void editCorrectionExceptionTest() throws DaoException, ServiceException {
        long accountId = 1;
        Correction correction = new Correction.CorrectionBuilder().withName("Test name").withText("test Text").build();
        Mockito.when(dao.update(Mockito.anyLong(),Mockito.eq(correction))).thenThrow(new DaoException());
        service.editCorrection(accountId,correction);
    }

    @AfterClass
    public void afterClass() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }
}
