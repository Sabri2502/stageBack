package tn.esprit.usermanagement.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.usermanagement.entities.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address,Integer> {
}

