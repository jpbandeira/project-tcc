package com.tcc.project;

import com.tcc.project.enums.TypeUser;
import com.tcc.project.model.Fine;
import com.tcc.project.model.User;
import com.tcc.project.services.FineService;
import com.tcc.project.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

public class FineServiceTest {

    private FineService fineService;

    private User user;

    private void clearLists() {
        FineService.fines.clear();
        UserService.users.clear();
    }

    @Before
    public void before() {
        this.fineService = new FineService();
        this.user = new User();
    }

    public Fine setup() {
        this.user = new User(UUID.randomUUID(), "Student", TypeUser.STUDENT, "student@email.com", "1710027");
        return new Fine(this.user);
    }

    @Test
    public void shouldNotAddWithNullFine() {
        Fine fine = this.fineService.addFine(null);
        Assert.assertNull(fine);

        this.clearLists();
    }

    @Test
    public void shouldAddAFine() {
        Fine fine = this.fineService.addFine(setup());
        Assert.assertNotNull(fine);
        Assert.assertNotNull(fine.getUuid());
        Assert.assertFalse(fine.isPaid());

        this.clearLists();
    }

    @Test
    public void shoulNotFindAFineWithANullUUID() {
        Fine fine = this.fineService.find(null);
        Assert.assertNull(fine);
    }

    @Test
    public void shoulNotFindAInvalidFine() {
        Fine fineSetup = setup();
        this.fineService.addFine(fineSetup);

        Fine fine = this.fineService.find(UUID.randomUUID());
        Assert.assertNull(fine);

        this.clearLists();
    }

    @Test
    public void shoulFindAFine() {
        Fine fine = this.fineService.addFine(setup());
        this.fineService.addFine(setup());

        Fine findedFine = this.fineService.find(fine.getUuid());
        Assert.assertNotNull(findedFine);

        this.clearLists();
    }

    @Test
    public void shoulNotFindAllByUserWithANullUser() {
        Fine fine = new Fine();

        ArrayList<Fine> fines = this.fineService.findAllByUser(fine.getUser());
        Assert.assertNull(fines);

        this.clearLists();
    }

    @Test
    public void shoulNotFindAllByUserWithAInvalidUserUUID() {
        Fine fine = this.fineService.addFine(setup());
        Fine invalidFine = setup();

        ArrayList<Fine> fines = this.fineService.findAllByUser(invalidFine.getUser());
        Assert.assertNull(fines);

        this.clearLists();
    }

    @Test
    public void shoulFindAllByUser() {
        Fine fine = this.fineService.addFine(setup());
        this.fineService.addFine(setup());

        ArrayList<Fine> fines = this.fineService.findAllByUser(fine.getUser());
        Assert.assertNotNull(fines);

        this.clearLists();
    }

    @Test
    public void shoulNotDeleteAFineWithANullUUID() {
        Fine fine1 = this.fineService.addFine(setup());
        Fine fine2 = this.fineService.addFine(setup());

        this.fineService.delete(null);
        Assert.assertNotNull(this.fineService.find(fine1.getUuid()));
        Assert.assertNotNull(this.fineService.find(fine2.getUuid()));

        this.clearLists();
    }

    @Test
    public void shoulDeleteAFine() {
        Fine fine1 = this.fineService.addFine(setup());
        Fine fine2 = this.fineService.addFine(setup());

        this.fineService.delete(fine1.getUuid());
        Assert.assertNull(this.fineService.find(fine1.getUuid()));
        Assert.assertNotNull(this.fineService.find(fine2.getUuid()));

        this.clearLists();
    }

}
