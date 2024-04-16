package id.ac.ui.cs.advprog.bookubeauth.repositories;

import id.ac.ui.cs.advprog.bookubeauth.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}