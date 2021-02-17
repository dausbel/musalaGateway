package com.musala.exercise.gatewayperipherical.repository;

import com.musala.exercise.gatewayperipherical.domain.Gateway;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatewayRepository extends CrudRepository<Gateway,String> {



}
