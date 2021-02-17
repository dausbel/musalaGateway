package com.musala.exercise.gatewayperipherical.service;

import com.musala.exercise.gatewayperipherical.domain.Device;
import com.musala.exercise.gatewayperipherical.domain.Gateway;
import com.musala.exercise.gatewayperipherical.exceptions.DeviceNotAddedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public interface GatewayService {

    String ENTITY_NAME = "gateway";
    Predicate<Gateway> NOT_NULL= gateway -> Objects.nonNull(gateway.getId());
    Predicate<Gateway> ALLOW_MORE_DEVICES = gateway -> gateway.getDeviceList().size() < 10;
    Predicate<Gateway> HAS_DEVICES = gateway -> gateway.getDeviceList().size() > 0;



    /**
     * @param gateway
     * @return
     */
    Gateway save(Gateway gateway);


    /**
     * @return
     */
    List<Gateway> findAll();


    /**
     * @return
     */
    Page<Gateway> findAll(Pageable pageable);

    /**
     * @param id
     * @return
     */
    Optional<Gateway> findById(String id);

    /**
     * @param id
     */
    void delete(String id);

    /**
     * @param id
     * @param device
     */

    Device addDevice(String id, Device device) throws DeviceNotAddedException;

    /**
     *
     * @param deviceId
     */
    void deleteDeviceById(long deviceId);

    /**
     *
     * @param gatewayId
     * @param deviceId
     */
    void deleteDeviceById(String gatewayId, long deviceId);


    /**
     *
     * @param gateewayId
     */
    void deleteGateway(String gateewayId);

}
