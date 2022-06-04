package com.tcc.project.services;

import com.tcc.project.model.Fine;
import com.tcc.project.model.User;

import java.util.ArrayList;
import java.util.UUID;

public class FineService {
    public static ArrayList<Fine> fines = new ArrayList<>();

    public Fine addFine(Fine fine) {
        if (fine == null) {
            return null;
        }

        Fine newFine = new Fine(fine);

        newFine.setUuid(UUID.randomUUID());
        newFine.setPaid(false);
        fines.add(newFine);

        return newFine;
    }

    public Fine find(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        if (fines.size() != 0) {
            for (Fine fine: fines) {
                if (fine.getUuid().equals(uuid)) {
                    return fine;
                }
            }
        }

        return null;
    }

    public ArrayList<Fine> findAllByUser(User user) {
        ArrayList<Fine> finesByUser = new ArrayList<>();

        if (user == null) {
            return null;
        }

        for (Fine fine: fines) {
            if (fine.getUser().getUuid().equals(user.getUuid())) {
                finesByUser.add(fine);
            }
        }

        if (finesByUser.size() == 0) {
            return null;
        }

        return finesByUser;
    }

    public void delete(UUID uuid) {
        Fine fine = null;

        if (uuid != null) {
            fine = this.find(uuid);
        }

        if (fine != null) {
            fines.remove(fine);
        }
    }

}
