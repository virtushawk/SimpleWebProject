package edu.epam.swp.model.service.impl;

import edu.epam.swp.exception.DaoException;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.dao.CreatureDao;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.service.CreatureService;
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
import java.util.Optional;

public class CreatureServiceImplTest {

    @Mock
    private CreatureDao dao;

    @InjectMocks
    private CreatureService service;

    @BeforeClass
    public void beforeClass() {
        service = CreatureServiceImpl.getInstance();
        MockitoAnnotations.openMocks(this);
    }

    @DataProvider
    public Object[][] creatureData() {
        return new Object[][] {
                {new Creature.CreatureBuilder().withName("test Name").withDescription("Test description").build(),true},
                {new Creature.CreatureBuilder().withName("плохое имя").withDescription("Test description").build(),false},
                {new Creature.CreatureBuilder().withName("test Name").withDescription("плохое описание").build(),false},
                {new Creature.CreatureBuilder().withName("").withDescription("Test description").build(),false},
                {new Creature.CreatureBuilder().withName("test Name").withDescription("").build(),false},
        };
    }

    @DataProvider
    public Object[][] uncheckedCreatureData() {
        return new Object[][] {
                {1,new Creature.CreatureBuilder().withName("test unchecked name").withDescription("Test unchecked description").build(),true},
                {2,new Creature.CreatureBuilder().withName("плохое имя").withDescription("Test unchecked description").build(),false},
                {3,new Creature.CreatureBuilder().withName("test unchecked Name").withDescription("плохое описание").build(),false},
                {4,new Creature.CreatureBuilder().withName("").withDescription("Test unchecked description").build(),false},
                {5,new Creature.CreatureBuilder().withName("test unchecked Name").withDescription("").build(),false},
        };
    }


    @Test
    public void findNewCreaturesTest() throws DaoException, ServiceException {
        int limit = 3;
        List<Creature> expected = new ArrayList<>();
        Mockito.when(dao.findNewCreatures(Mockito.anyInt())).thenReturn(expected);
        List<Creature> actual = service.findNewCreatures(limit);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "findNewCreaturesTest",expectedExceptions = ServiceException.class)
    public void findNewCreaturesExceptionTest() throws DaoException,ServiceException {
        int limit = 3;
        Mockito.when(dao.findNewCreatures(Mockito.anyInt())).thenThrow(new DaoException());
        service.findNewCreatures(limit);
    }

    @Test
    public void findPopularCreaturesTest() throws DaoException,ServiceException {
        int limit = 3;
        List<Creature> expected = new ArrayList<>();
        Mockito.when(dao.findPopularCreatures(limit)).thenReturn(expected);
        List<Creature> actual = service.findPopularCreatures(limit);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "findPopularCreaturesTest",expectedExceptions = ServiceException.class)
    public void findPopularCreaturesExceptionTest() throws DaoException, ServiceException {
        int limit = 3;
        Mockito.when(dao.findPopularCreatures(Mockito.anyInt())).thenThrow(new DaoException());
        service.findPopularCreatures(limit);
    }

    @Test(dataProvider = "creatureData")
    public void createCreatureTest(Creature creature,boolean expected) throws DaoException, ServiceException {
        Mockito.when(dao.create(creature)).thenReturn(true);
        boolean actual = service.createCreature(creature);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "createCreatureTest",expectedExceptions = ServiceException.class)
    public void createCreatureExceptionTest() throws DaoException, ServiceException {
        Creature creature = new Creature.CreatureBuilder().withName("test Name").withDescription("Test description").build();
        Mockito.when(dao.create(creature)).thenThrow(new DaoException());
        service.createCreature(creature);
    }

    @Test
    public void getCreatureTest() throws DaoException, ServiceException {
        long id = 1;
        Creature creature = new Creature.CreatureBuilder().withName("Test name").build();
        Optional<Creature> expected = Optional.of(creature);
        Mockito.when(dao.find(Mockito.anyLong())).thenReturn(expected);
        Optional<Creature> actual = service.findCreature(id);
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void getEmptyTest() throws DaoException, ServiceException {
        long id = 1;
        Optional<Creature> expected = Optional.empty();
        Mockito.when(dao.find(Mockito.anyLong())).thenReturn(expected);
        Optional<Creature> actual = service.findCreature(id);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = {"getCreatureTest","getEmptyTest"},expectedExceptions = ServiceException.class)
    public void getExceptionTest() throws DaoException, ServiceException {
        long id = 1;
        Mockito.when(dao.find(Mockito.anyLong())).thenThrow(new DaoException());
        service.findCreature(id);
    }

    @Test
    public void findAllTest() throws DaoException, ServiceException {
        List<Creature> expected = new ArrayList<>();
        Mockito.when(dao.findAll()).thenReturn(expected);
        List<Creature> actual = service.findAll();
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "findAllTest",expectedExceptions = ServiceException.class)
    public void findAllExceptionTest() throws DaoException, ServiceException {
        Mockito.when(dao.findAll()).thenThrow(new DaoException());
        service.findAll();
    }

    @Test
    public void changeImageTrueTest() throws DaoException, ServiceException {
        String image = "path to picture";
        long id = 1;
        Mockito.when(dao.updateImageById(Mockito.anyLong(),Mockito.anyString())).thenReturn(true);
        boolean actual = service.changeImage(id,image);
        Assert.assertTrue(actual);
    }

    @Test
    public void changeImageFalseTest() throws DaoException, ServiceException {
        String image = "path to picture";
        long id = 1;
        Mockito.when(dao.updateImageById(Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
        boolean actual = service.changeImage(id,image);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"changeImageTrueTest","changeImageFalseTest"},expectedExceptions = ServiceException.class)
    public void changeImageExceptionTest() throws DaoException, ServiceException {
        String image = "path to picture";
        long id = 1;
        Mockito.when(dao.updateImageById(Mockito.anyLong(),Mockito.anyString())).thenThrow(new DaoException());
        service.changeImage(id,image);
    }

    @Test
    public void changeUncheckedImageTrueTest() throws DaoException, ServiceException {
        String image = "path to picture";
        long creatureId = 1;
        long accountId = 1;
        Mockito.when(dao.updateUncheckedImageByCreatureId(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyString())).thenReturn(true);
        boolean actual = service.changeUncheckedImage(creatureId,accountId,image);
        Assert.assertTrue(actual);
    }

    @Test
    public void changeUncheckedImageFalseTest() throws DaoException, ServiceException {
        String image = "path to picture";
        long creatureId = 1;
        long accountId = 1;
        Mockito.when(dao.updateUncheckedImageByCreatureId(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
        boolean actual = service.changeUncheckedImage(creatureId,accountId,image);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"changeUncheckedImageTrueTest","changeUncheckedImageFalseTest"},expectedExceptions = ServiceException.class)
    public void changeUncheckedImageExceptionTest() throws DaoException, ServiceException {
        String image = "path to picture";
        long creatureId = 1;
        long accountId = 1;
        Mockito.when(dao.updateUncheckedImageByCreatureId(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyString())).thenThrow(new DaoException());
        service.changeUncheckedImage(creatureId,accountId,image);
    }

    @Test(dataProvider = "creatureData")
    public void editCreatureTest(Creature creature,boolean expected) throws DaoException, ServiceException {
        Mockito.when(dao.update(creature)).thenReturn(true);
        boolean actual = service.editCreature(creature);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "editCreatureTest",expectedExceptions = ServiceException.class)
    public void editCreatureExceptionTest() throws DaoException, ServiceException {
        Creature creature = new Creature.CreatureBuilder().withName("test Name").withDescription("Test description").build();
        Mockito.when(dao.update(creature)).thenThrow(new DaoException());
        service.createCreature(creature);
    }

    @Test(dataProvider = "uncheckedCreatureData")
    public void editUncheckedCreatureTest(long accountId,Creature creature,boolean expected) throws DaoException, ServiceException {
        Mockito.when(dao.updateUncheckedCreature(Mockito.anyLong(),Mockito.eq(creature))).thenReturn(true);
        boolean actual = service.editUncheckedCreature(accountId,creature);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "editUncheckedCreatureTest",expectedExceptions = ServiceException.class)
    public void editUncheckedCreatureExceptionTest() throws DaoException, ServiceException {
        long accountId = 1;
        Creature creature = new Creature.CreatureBuilder().withName("test Name").withDescription("Test description").build();
        Mockito.when(dao.updateUncheckedCreature(Mockito.anyLong(),Mockito.eq(creature))).thenThrow(new DaoException());
        service.editUncheckedCreature(accountId,creature);
    }

    @Test
    public void deleteTrueTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.delete(Mockito.anyLong())).thenReturn(true);
        boolean actual = service.delete(id);
        Assert.assertTrue(actual);
    }

    @Test
    public void deleteFalseTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.delete(Mockito.anyLong())).thenReturn(false);
        boolean actual = service.delete(id);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"deleteTrueTest","deleteFalseTest"},expectedExceptions = ServiceException.class)
    public void deleteExceptionTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.delete(Mockito.anyLong())).thenThrow(new DaoException());
       service.delete(id);
    }

    @Test
    public void deleteWithAccountIdTrueTest() throws DaoException,ServiceException {
        long accountId = 1;
        long creatureId = 1;
        Mockito.when(dao.delete(Mockito.anyLong(),Mockito.anyLong())).thenReturn(true);
        boolean actual = service.delete(accountId,creatureId);
        Assert.assertTrue(actual);
    }

    @Test
    public void deleteWithAccountIdFalseTest() throws DaoException,ServiceException {
        long accountId = 1;
        long creatureId = 1;
        Mockito.when(dao.delete(Mockito.anyLong(),Mockito.anyLong())).thenReturn(false);
        boolean actual = service.delete(accountId,creatureId);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"deleteWithAccountIdTrueTest","deleteWithAccountIdFalseTest"},expectedExceptions = ServiceException.class)
    public void deleteWithAccountIdExceptionTest() throws DaoException,ServiceException {
        long accountId = 1;
        long creatureId = 1;
        Mockito.when(dao.delete(Mockito.anyLong(),Mockito.anyLong())).thenThrow(new DaoException());
        service.delete(accountId,creatureId);
    }

    @Test
    public void findUserCreaturesTest() throws DaoException, ServiceException {
        long accountId = 1;
        List<Creature> expected = new ArrayList<>();
        Mockito.when(dao.findCreaturesByAccountId(Mockito.anyLong())).thenReturn(expected);
        List<Creature> actual = service.findUserCreatures(accountId);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "findUserCreaturesTest",expectedExceptions = ServiceException.class)
    public void findUserCreaturesExceptionTest() throws DaoException, ServiceException {
        long accountId = 1;
        Mockito.when(dao.findCreaturesByAccountId(Mockito.anyLong())).thenThrow(new DaoException());
        service.findUserCreatures(accountId);
    }

    @Test
    public void findUncheckedCreaturesTest() throws DaoException, ServiceException {
        List<Creature> expected = new ArrayList<>();
        Mockito.when(dao.findUncheckedCreatures()).thenReturn(expected);
        List<Creature> actual = service.findUncheckedCreatures();
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "findUncheckedCreaturesTest",expectedExceptions = ServiceException.class)
    public void findUncheckedCreaturesExceptionTest() throws DaoException, ServiceException {
        Mockito.when(dao.findUncheckedCreatures()).thenThrow(new DaoException());
        service.findUncheckedCreatures();
    }

    @Test
    public void approveCreatureTrueTest() throws DaoException, ServiceException {
        long creatureId = 1;
        Mockito.when(dao.approveCreature(Mockito.anyLong())).thenReturn(true);
        boolean actual = service.approveCreature(creatureId);
        Assert.assertTrue(actual);
    }

    @Test
    public void approveCreatureFalseTest() throws DaoException, ServiceException {
        long creatureId = 1;
        Mockito.when(dao.approveCreature(Mockito.anyLong())).thenReturn(false);
        boolean actual = service.approveCreature(creatureId);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"approveCreatureTrueTest","approveCreatureFalseTest"},expectedExceptions = ServiceException.class)
    public void approveCreatureExceptionTest() throws DaoException, ServiceException {
        long creatureId = 1;
        Mockito.when(dao.approveCreature(Mockito.anyLong())).thenThrow(new DaoException());
        service.approveCreature(creatureId);
    }

    @Test
    public void searchTest() throws DaoException, ServiceException {
        String text = "search text";
        List<Creature> expected = new ArrayList<>();
        Mockito.when(dao.search(Mockito.anyString())).thenReturn(expected);
        List<Creature> actual = service.search(text);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "searchTest",expectedExceptions = ServiceException.class)
    public void searchExceptionTest() throws DaoException, ServiceException {
        String text = "search text";
        Mockito.when(dao.search(Mockito.anyString())).thenThrow(new DaoException());
        service.search(text);
    }

    @Test
    public void findUserSuggestedCreaturesTest() throws DaoException, ServiceException {
        long accountId = 1;
        List<Creature> expected = new ArrayList<>();
        Mockito.when(dao.findUncheckedCreaturesByAccountId(Mockito.anyLong())).thenReturn(expected);
        List<Creature> actual = service.findUserSuggestedCreatures(accountId);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "findUserSuggestedCreaturesTest",expectedExceptions = ServiceException.class)
    public void findUserSuggestedCreaturesExceptionTest() throws DaoException, ServiceException {
        long accountId = 1;
        Mockito.when(dao.findUncheckedCreaturesByAccountId(Mockito.anyLong())).thenThrow(new DaoException());
        service.findUserSuggestedCreatures(accountId);
    }

    @AfterClass
    public void afterClass() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }
}
