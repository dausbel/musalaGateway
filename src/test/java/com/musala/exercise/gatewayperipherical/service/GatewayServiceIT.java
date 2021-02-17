package com.musala.exercise.gatewayperipherical.service;

import com.musala.exercise.gatewayperipherical.domain.Device;
import com.musala.exercise.gatewayperipherical.domain.Gateway;
import com.musala.exercise.gatewayperipherical.domain.enumerations.Status;
import com.musala.exercise.gatewayperipherical.repository.DeviceRepository;
import com.musala.exercise.gatewayperipherical.repository.GatewayRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayServiceIT {

    private static final String DEFAULT_ADDRESS = "172.24.25.23";

    private static final String DEFAULT_ID = "sasasaslklasklks-sdsdsdsdsd";

    private static final String DEFAULT_NAME = "GATEWAY==1";

    private static final Date DEFAULT_DEVICE_DATE = new Date();

    private static final String DEFAULT_DEVICE_VENDOR = "vendor Soft";

    private static final Status DEFAULT_DEVICE_STATUS = Status.ONLINE;


    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private DeviceRepository devicesRepository;

    private Gateway gateway;

    private Device device;


    @BeforeEach
    public void init() {
        gatewayRepository.deleteAll();
        gateway = new Gateway();
        gateway.setAddress(DEFAULT_ADDRESS);
        gateway.setName(DEFAULT_NAME);
        gateway.setId(DEFAULT_ID);

        devicesRepository.deleteAll();
        device = new Device();
        device.setDate(DEFAULT_DEVICE_DATE);
        device.setStatus(DEFAULT_DEVICE_STATUS);
        device.setVendor(DEFAULT_DEVICE_VENDOR);
        device.setGateway(gateway);

    }


    @Test
    public void assertGatewayDevicesListNotEmpty() {

        device = new Device();
        device.setDate(DEFAULT_DEVICE_DATE);
        device.setStatus(DEFAULT_DEVICE_STATUS);
        device.setVendor(DEFAULT_DEVICE_VENDOR);
        device.setGateway(gateway);
        List<Device>listDevices= Collections.singletonList(device);
        gateway = new Gateway();
        gateway.setAddress(DEFAULT_ADDRESS);
        gateway.setName(DEFAULT_NAME);
        gateway.setId(DEFAULT_ID);
       gateway.setDeviceList(listDevices);


        devicesRepository.save(device);
      gatewayRepository.save(gateway);

        List<Gateway> gatewayList = gatewayService.findAll();
        Gateway testGateway = gatewayList.get(gatewayList.size() - 1);
        assertThat(testGateway.getDeviceList()).isNotEmpty();


    }

}