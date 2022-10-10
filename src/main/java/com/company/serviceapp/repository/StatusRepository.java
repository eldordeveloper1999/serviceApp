package com.company.serviceapp.repository;

import com.company.serviceapp.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {


    @Query(nativeQuery = true, value = "select * from status where id = : id")
    Status getByTaskId(UUID id);

    @Query(nativeQuery = true, value = "select s.id, s.name from status s join orders o on s.id = o.status_id\n" +
            "where o.id = :orderId ")
    Status getStatusByOrderId(UUID orderId);
}
