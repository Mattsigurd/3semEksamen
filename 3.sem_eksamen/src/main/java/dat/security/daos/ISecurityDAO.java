package dat.security.daos;

import dat.security.entities.User;
import dat.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import dat.security.entities.User;
import dat.security.exceptions.ValidationException;

import java.util.List;

public interface ISecurityDAO {
    UserDTO getVerifiedUser(String username, String password) throws ValidationException, ValidationException;
    User createUser(String username, String password);
    //User createUserOG(String username, String password);
    User getUserByID(int id);
    List<User> getAll();
}
