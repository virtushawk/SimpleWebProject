package edu.epam.swp.model.service.impl;

import edu.epam.swp.exception.DaoException;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.dao.ReviewDao;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.model.service.ReviewService;
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

public class ReviewServiceImplTest {

    @Mock
    private ReviewDao dao;

    @InjectMocks
    private ReviewService service;

    @BeforeClass
    public void beforeClass() {
        service = ReviewServiceImpl.getInstance();
        MockitoAnnotations.openMocks(this);
    }

    @DataProvider
    public Object[][] reviewData() {
        return new Object[][]{
                {new Review.ReviewBuilder().withText("Test review").withRating(1).build(), true},
                {new Review.ReviewBuilder().withText("").withRating(1).build(), false},
                {new Review.ReviewBuilder().withText("плохой отзыв").withRating(1).build(), false}
        };
    }

    @DataProvider
    public Object[][] userReviewData() {
        return new Object[][]{
                {1, new Review.ReviewBuilder().withText("Test user review").withRating(1).build(), true},
                {2, new Review.ReviewBuilder().withText("").withRating(1).build(), false},
                {3, new Review.ReviewBuilder().withText("плохой отзыв").withRating(1).build(), false}
        };
    }

    @Test(dataProvider = "reviewData")
    public void createReviewTest(Review review, boolean expected) throws DaoException, ServiceException {
        Mockito.when(dao.create(review)).thenReturn(true);
        boolean actual = service.createReview(review);
        Assert.assertEquals(actual, expected);
    }

    @Test(dependsOnMethods = "createReviewTest", expectedExceptions = ServiceException.class)
    public void createReviewExceptionTest() throws DaoException, ServiceException {
        Review review = new Review.ReviewBuilder().withText("Test review").withRating(1).build();
        Mockito.when(dao.create(review)).thenThrow(new DaoException());
        service.createReview(review);
    }

    @Test
    public void findCreatureReviewsTest() throws DaoException, ServiceException {
        long id = 1;
        List<Review> expected = new ArrayList<>();
        Mockito.when(dao.findReviewsByCreatureId(Mockito.anyLong())).thenReturn(expected);
        List<Review> actual = service.findCreatureReviews(id);
        Assert.assertEquals(actual, expected);
    }

    @Test(dependsOnMethods = "findCreatureReviewsTest", expectedExceptions = ServiceException.class)
    public void findCreatureReviewsExceptionTest() throws DaoException, ServiceException {
        long id = 1;
        Mockito.when(dao.findReviewsByCreatureId(Mockito.anyLong())).thenThrow(new DaoException());
        service.findCreatureReviews(id);
    }

    @Test
    public void findUserReviewsTest() throws DaoException, ServiceException {
        long id = 1;
        List<Review> expected = new ArrayList<>();
        Mockito.when(dao.findReviewsByAccountId(Mockito.anyLong())).thenReturn(expected);
        List<Review> actual = service.findUserReviews(id);
        Assert.assertEquals(actual, expected);
    }

    @Test(dependsOnMethods = "findUserReviewsTest", expectedExceptions = ServiceException.class)
    public void findUserReviewsExceptionTest() throws DaoException, ServiceException {
        long id = 1;
        Mockito.when(dao.findReviewsByAccountId(Mockito.anyLong())).thenThrow(new DaoException());
        service.findUserReviews(id);
    }

    @Test
    public void findAllTest() throws DaoException, ServiceException {
        List<Review> expected = new ArrayList<>();
        Mockito.when(dao.findAll()).thenReturn(expected);
        List<Review> actual = service.findAll();
        Assert.assertEquals(actual, expected);
    }

    @Test(dependsOnMethods = "findAllTest", expectedExceptions = ServiceException.class)
    public void findAllExceptionTest() throws DaoException, ServiceException {
        Mockito.when(dao.findAll()).thenThrow(new DaoException());
        service.findAll();
    }

    @Test(dataProvider = "reviewData")
    public void editReviewTest(Review review, boolean expected) throws DaoException, ServiceException {
        Mockito.when(dao.update(review)).thenReturn(true);
        boolean actual = service.editReview(review);
        Assert.assertEquals(actual, expected);
    }

    @Test(dependsOnMethods = "editReviewTest", expectedExceptions = ServiceException.class)
    public void editReviewExceptionTest() throws DaoException, ServiceException {
        Review review = new Review.ReviewBuilder().withText("Test review").withRating(1).build();
        Mockito.when(dao.update(review)).thenThrow(new DaoException());
        service.editReview(review);
    }

    @Test(dataProvider = "userReviewData")
    public void editReviewWithAccountIdTest(long accountId, Review review, boolean expected) throws DaoException, ServiceException {
        Mockito.when(dao.update(Mockito.anyLong(), Mockito.eq(review))).thenReturn(true);
        boolean actual = service.editReview(accountId, review);
        Assert.assertEquals(actual, expected);
    }

    @Test(dependsOnMethods = "editReviewWithAccountIdTest", expectedExceptions = ServiceException.class)
    public void editReviewWithAccountIdExceptionTest() throws DaoException, ServiceException {
        Review review = new Review.ReviewBuilder().withText("Test review").withRating(1).build();
        long accountId = 1;
        Mockito.when(dao.update(Mockito.anyLong(), Mockito.eq(review))).thenThrow(new DaoException());
        service.editReview(accountId, review);
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

    @Test(dependsOnMethods = {"deleteTrueTest", "deleteFalseTest"}, expectedExceptions = ServiceException.class)
    public void deleteExceptionTest() throws DaoException, ServiceException {
        long id = 1;
        Mockito.when(dao.delete(Mockito.anyLong())).thenThrow(new DaoException());
        service.delete(id);
    }

    @Test
    public void deleteWithAccountIdTrueTest() throws DaoException, ServiceException {
        long accountId = 1;
        long reviewId = 1;
        Mockito.when(dao.delete(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);
        boolean actual = service.delete(accountId, reviewId);
        Assert.assertTrue(actual);
    }

    @Test
    public void deleteWithAccountIdFalseTest() throws DaoException, ServiceException {
        long accountId = 1;
        long reviewId = 1;
        Mockito.when(dao.delete(Mockito.anyLong(), Mockito.anyLong())).thenReturn(false);
        boolean actual = service.delete(accountId, reviewId);
        Assert.assertFalse(actual);
    }

    @Test(dependsOnMethods = {"deleteWithAccountIdTrueTest", "deleteWithAccountIdFalseTest"}, expectedExceptions = ServiceException.class)
    public void deleteWithAccountIdExceptionTest() throws DaoException, ServiceException {
        long accountId = 1;
        long reviewId = 1;
        Mockito.when(dao.delete(Mockito.anyLong(), Mockito.anyLong())).thenThrow(new DaoException());
        service.delete(accountId, reviewId);
    }

    @Test
    public void findUserReviewTest() throws DaoException, ServiceException {
        long accountId = 1;
        long reviewId = 1;
        Optional<Review> expected = Optional.empty();
        Mockito.when(dao.findReviewByAccountIdCreatureId(Mockito.anyLong(),Mockito.anyLong())).thenReturn(expected);
        Optional<Review> actual = service.findUserReview(accountId,reviewId);
        Assert.assertEquals(actual,expected);
    }

    @Test(dependsOnMethods = "findUserReviewTest", expectedExceptions = ServiceException.class)
    public void findUserReviewExceptionTest() throws DaoException, ServiceException {
        long accountId = 1;
        long reviewId = 1;
        Mockito.when(dao.findReviewByAccountIdCreatureId(Mockito.anyLong(),Mockito.anyLong())).thenThrow(new DaoException());
        service.findUserReview(accountId,reviewId);
    }

    @AfterClass
    public void afterClass() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }
}
