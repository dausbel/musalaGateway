package com.musala.exercise.gatewayperipherical.repository;

import com.musala.exercise.gatewayperipherical.domain.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device,Long> {
}
