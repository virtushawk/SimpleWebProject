package edu.epam.swp.model.service.impl;

import edu.epam.swp.exception.DaoException;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.dao.UserDao;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.entity.UserStatus;
import edu.epam.swp.model.service.UserService;
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

public class UserServiceImplTest {

    @Mock
    private UserDao dao;

    @InjectMocks
    private UserService service;

    @BeforeClass
    public void beforeClass() {
        service = UserServiceImpl.getInstance();
        MockitoAnnotations.openMocks(this);
    }

    @DataProvider
    public Object[][] userData() {
        return new Object[][] {
                {"user","qwerty123"},
                {"a","qwerty123adasdadasda"},
                {"nicknameer","qwerty1"},
                {"ник","кверти"},
                {"longestNick","1234567"}
        };
    }

    @DataProvider
    public Object[][] nameData() {
        return new Object[][] {
                {1,"Roman Bruhanchik",true},
                {2,"a",true},
                {3,"",false},
                {4,"Роман",false},
                {5,"Roman @#@$",false}
        };
    }

    @DataProvider
    public Object[][] emailData() {
        return new Object[][] {
                {1,"example@gmail.com",true},
                {2,"firstname.lastname@example.com",true},
                {3,"email@123.123.123.123",false},
                {4,"plainaddress",false},
                {5,"#@%^%#$@#$@#.com",false},
                {6,"@example.com",false},
                {7,"email.@example.com",false}
        };
    }

    @DataProvider
    public Object[][] passwordData() {
        return new Object[][] {
                {1,"qwerty123",true},
                {2,"qwerty123adasdadasda",true},
                {3,"qwerty1",false},
                {4,"кверти",false},
                {5,"1234567",false}
        };
    }

    @Test
    public void findAllTest() throws DaoException, ServiceException {
        List<User> expected = new ArrayList<>();
        Mockito.when(dao.findAll()).thenReturn(expected);
        List<User> actual = service.findAll();
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "findAllTest",expectedExceptions = ServiceException.class)
    public void findAllExceptionTest() throws DaoException, ServiceException {
        Mockito.when(dao.findAll()).thenThrow(new DaoException());
        service.findAll();
    }

    @Test()
    public void findUserTest() throws DaoException,ServiceException {
        String name = "user";
        String password = "qwerty123adasdadasda";
        Optional<User> expected = Optional.empty();
        Mockito.when(dao.findUserByUsernamePassword(Mockito.anyString(),Mockito.anyString())).thenReturn(expected);
        Optional<User> actual = service.findUser(name,password);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "findUserTest",expectedExceptions = ServiceException.class)
    public void findUserExceptionTest() throws DaoException,ServiceException {
        String name = "user";
        String password = "qwerty123adasdadasda";
        Mockito.when(dao.findUserByUsernamePassword(Mockito.anyString(),Mockito.anyString())).thenThrow(new DaoException());
        service.findUser(name,password);
    }

    @Test
    public void changeAvatarTrueTest() throws DaoException,ServiceException {
        String avatar = "Path to avatar";
        long id = 1;
        Mockito.when(dao.updateAvatarById(Mockito.anyString(),Mockito.anyLong())).thenReturn(true);
        boolean actual = service.changeAvatar(avatar,id);
        Assert.assertTrue(actual);
    }

    @Test
    public void changeAvatarFalseTest() throws DaoException,ServiceException {
        String avatar = "Path to avatar";
        long id = 1;
        Mockito.when(dao.updateAvatarById(Mockito.anyString(),Mockito.anyLong())).thenReturn(false);
        boolean actual = service.changeAvatar(avatar,id);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"changeAvatarTrueTest","changeAvatarFalseTest"},expectedExceptions = ServiceException.class)
    public void changeAvatarExceptionTest() throws DaoException,ServiceException {
        String avatar = "Path to avatar";
        long id = 1;
        Mockito.when(dao.updateAvatarById(Mockito.anyString(),Mockito.anyLong())).thenThrow(new DaoException());
        service.changeAvatar(avatar,id);
    }

    @Test
    public void getTest() throws DaoException,ServiceException {
        long id = 1;
        Optional<User> expected = Optional.empty();
        Mockito.when(dao.find(Mockito.anyLong())).thenReturn(expected);
        Optional<User> actual = service.findUser(id);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "getTest",expectedExceptions = ServiceException.class)
    public void getExceptionTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.find(Mockito.anyLong())).thenThrow(new DaoException());
        service.findUser(id);
    }

    @Test
    public void confirmEmailTrueTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.confirmEmail(Mockito.anyLong())).thenReturn(true);
        boolean actual = service.confirmEmail(id);
        Assert.assertTrue(actual);
    }

    @Test
    public void confirmEmailFalseTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.confirmEmail(Mockito.anyLong())).thenReturn(false);
        boolean actual = service.confirmEmail(id);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"confirmEmailTrueTest","confirmEmailFalseTest"},expectedExceptions = ServiceException.class)
    public void confirmEmailExceptionTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.confirmEmail(Mockito.anyLong())).thenThrow(new DaoException());
        service.confirmEmail(id);
    }

    @Test
    public void blockUserTrueTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.blockUser(Mockito.anyLong())).thenReturn(true);
        boolean actual = service.blockUser(id);
        Assert.assertTrue(actual);
    }

    @Test
    public void blockUserFalseTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.blockUser(Mockito.anyLong())).thenReturn(false);
        boolean actual = service.blockUser(id);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"blockUserTrueTest","blockUserFalseTest"},expectedExceptions = ServiceException.class)
    public void blockUserExceptionTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.blockUser(Mockito.anyLong())).thenThrow(new DaoException());
        service.blockUser(id);
    }

    @Test
    public void unblockUserTrueTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.unblockUser(Mockito.anyLong())).thenReturn(true);
        boolean actual = service.unblockUser(id);
        Assert.assertTrue(actual);
    }

    @Test
    public void unblockUserFalseTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.unblockUser(Mockito.anyLong())).thenReturn(false);
        boolean actual = service.unblockUser(id);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"unblockUserTrueTest","unblockUserFalseTest"},expectedExceptions = ServiceException.class)
    public void unblockUserExceptionTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.unblockUser(Mockito.anyLong())).thenThrow(new DaoException());
        service.unblockUser(id);
    }

    @Test
    public void makeAdminTrueTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.makeAdmin(Mockito.anyLong())).thenReturn(true);
        boolean actual = service.makeAdmin(id);
        Assert.assertTrue(actual);
    }

    @Test
    public void makeAdminFalseTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.makeAdmin(Mockito.anyLong())).thenReturn(false);
        boolean actual = service.makeAdmin(id);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"makeAdminTrueTest","makeAdminFalseTest"},expectedExceptions = ServiceException.class)
    public void makeAdminExceptionTest() throws DaoException,ServiceException {
        long id = 1;
        Mockito.when(dao.makeAdmin(Mockito.anyLong())).thenThrow(new DaoException());
        service.makeAdmin(id);
    }

    @Test(dataProvider = "nameData")
    public void changeNameTest(long accountId,String name,boolean expected) throws DaoException,ServiceException {
        Mockito.when(dao.updateName(Mockito.anyString(),Mockito.anyLong())).thenReturn(true);
        boolean actual = service.changeName(name,accountId);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "changeNameTest",expectedExceptions = ServiceException.class)
    public void changeNameExceptionTest() throws DaoException,ServiceException {
        String name = "test name";
        long accountId = 1;
        Mockito.when(dao.updateName(Mockito.anyString(),Mockito.anyLong())).thenThrow(new DaoException());
        service.changeName(name,accountId);
    }

    @Test(dataProvider = "emailData")
    public void changeEmailTest(long accountId,String email,boolean expected) throws DaoException,ServiceException {
        Mockito.when(dao.updateEmail(email,accountId)).thenReturn(true);
        boolean actual = service.changeEmail(email,accountId);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "changeEmailTest",expectedExceptions = ServiceException.class)
    public void changeEmailExceptionTest() throws DaoException,ServiceException {
        Mockito.when(dao.updateEmail(Mockito.anyString(),Mockito.anyLong())).thenThrow(new DaoException());
        String email = "example@gmail.com";
        long accountId = 1;
        service.changeEmail(email,accountId);
    }

    @Test(dataProvider = "passwordData")
    public void changePasswordTest(long accountId,String password,boolean expected) throws DaoException,ServiceException {
        Mockito.when(dao.updatePassword(Mockito.anyString(),Mockito.anyLong())).thenReturn(true);
        boolean actual = service.changePassword(password,accountId);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "changePasswordTest",expectedExceptions = ServiceException.class)
    public void changePasswordExceptionTest() throws DaoException,ServiceException {
        Mockito.when(dao.updatePassword(Mockito.anyString(),Mockito.anyLong())).thenThrow(new DaoException());
        String password = "qwerty123";
        long accountId = 1;
        service.changePassword(password,accountId);
    }

    @Test
    public void changeUserStatusTest() throws DaoException,ServiceException {
        UserStatus status = UserStatus.REGULAR;
        long accountId = 1;
        Mockito.when(dao.updateUserStatus(accountId,status)).thenReturn(true);
        boolean actual = service.changeUserStatus(accountId,status);
        Assert.assertTrue(actual);
    }

    @Test(dependsOnMethods = "changeUserStatusTest",expectedExceptions = ServiceException.class)
    public void changeUserStatusExceptionTest() throws DaoException,ServiceException {
        UserStatus status = UserStatus.REGULAR;
        long accountId = 1;
        Mockito.when(dao.updateUserStatus(accountId,status)).thenThrow(new DaoException());
        service.changeUserStatus(accountId,status);
    }

    @AfterClass
    public void afterClass() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }
}
