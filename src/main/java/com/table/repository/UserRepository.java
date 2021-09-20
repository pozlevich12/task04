package com.table.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.table.entity.AuthProvider;
import com.table.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
    List<User> findByProviderKeyAndAuthProvider(String providerKey, AuthProvider authProvider);
    List<User> findByAuthProvider(AuthProvider authProvider);
    List<User> findByBlocked(boolean blocked);
    List<User> findByProviderKey(String providerKey);
    List<User> findByName(String name);
}