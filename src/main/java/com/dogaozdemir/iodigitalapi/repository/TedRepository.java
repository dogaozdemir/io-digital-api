package com.dogaozdemir.iodigitalapi.repository;

import com.dogaozdemir.iodigitalapi.model.Ted;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface TedRepository extends MongoRepository<Ted,String> {
   Optional<Ted> findById(String id);
}
