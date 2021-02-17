package com.musala.exercise.gatewayperipherical.service.impl;

import com.musala.exercise.gatewayperipherical.domain.Device;
import com.musala.exercise.gatewayperipherical.domain.Gateway;
import com.musala.exercise.gatewayperipherical.domain.enumerations.Status;
import com.musala.exercise.gatewayperipherical.exceptions.DeviceNotAddedException;
import com.musala.exercise.gatewayperipherical.repository.DeviceRepository;
import com.musala.exercise.gatewayperipherical.repository.GatewayRepository;
import com.musala.exercise.gatewayperipherical.service.GatewayService;
import com.musala.exercise.gatewayperipherical.util.PaginationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GatewayServiceImpl implements GatewayService {

    private final GatewayRepository gatewayRepository;

    private final DeviceRepository deviceRepository;

    private Consumer<Gateway> deleteAssociatedDevices = gateway -> gateway.getDeviceList().forEach(device -> deleteDeviceById(device.getIdDevice()));

    @Autowired
    public GatewayServiceImpl(GatewayRepository gatewayRepository, DeviceRepository deviceRepository) {
        this.gatewayRepository = gatewayRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Gateway save(Gateway gateway) {

        return gatewayRepository.save(gateway);
    }

    @Override
    public List<Gateway> findAll() {
        return StreamSupport
                .stream(gatewayRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Gateway> findAll(Pageable pageable) {
        List<Gateway> gatewayList = this.findAll();
        return PaginationUtil.generatePaginationImpl(gatewayList, pageable);
    }


    @Override
    public Optional<Gateway> findById(String id) {
        return gatewayRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        gatewayRepository.deleteById(id);
    }

    @Override
    public Device addDevice(String id, Device device) throws DeviceNotAddedException {

        return this.findById(id)
                .filter(NOT_NULL.and(ALLOW_MORE_DEVICES))
                .map(gateway -> {
                    if (Objects.nonNull(device.getIdDevice())) {
                        return gateway.getDeviceList()
                                .stream()
                                .filter(device1 -> Objects.deepEquals(device1.getIdDevice(), device.getIdDevice()))
                                .map(device1 -> {
                                    device1.setVendor(device.getVendor());
                                    device1.setStatus(device.getStatus());
                                    if(Objects.deepEquals(device.getStatus().name(), Status.ONLINE))
                                        device1.setStatus(Status.ONLINE);
                                    else
                                        device1.setStatus(Status.OFFLINE);
                                    return deviceRepository.save(device1);
                                })
                                .findFirst().get();
                    } else {

                        Device deviceObj = new Device()
                                .vendor(device.getVendor())
                                .status(device.getStatus())
                                .date(new Date())
                                .gateway(gateway);
                        if(Objects.deepEquals(device.getStatus().name(), Status.ONLINE))
                            deviceObj.setStatus(Status.ONLINE);
                        else
                            deviceObj.setStatus(Status.OFFLINE);
                        return deviceRepository.save(deviceObj);
                    }

                })
                .orElseThrow(() -> new DeviceNotAddedException("Device could not be added"));

    }

    @Override
    public void deleteDeviceById(long deviceId) {
        deviceRepository.findById(deviceId)
                .ifPresent(device -> deviceRepository.deleteById(device.getIdDevice()));
    }

    @Override
    public void deleteDeviceById(String gatewayId, long deviceId) {
        this.findById(gatewayId)
                .filter(NOT_NULL)
                .map(Gateway::getDeviceList)
                .ifPresent(devices -> devices.forEach(device -> {
                    if (Objects.deepEquals(device.getIdDevice(), deviceId))
                        deleteDeviceById(deviceId);
                }));
    }

    @Override
    public void deleteGateway(String gatewayId) {
        this.findById(gatewayId)
                .filter(NOT_NULL)
                .ifPresent(gateway -> {
                    if (HAS_DEVICES.test(gateway)) {
                        deleteAssociatedDevices.accept(gateway);
                    }
                    gatewayRepository.deleteById(gatewayId);
                });
    }


}
