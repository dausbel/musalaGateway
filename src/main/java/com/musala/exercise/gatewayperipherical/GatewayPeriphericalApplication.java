package com.musala.exercise.gatewayperipherical;

import com.musala.exercise.gatewayperipherical.domain.Device;
import com.musala.exercise.gatewayperipherical.domain.Gateway;
import com.musala.exercise.gatewayperipherical.domain.enumerations.Status;
import com.musala.exercise.gatewayperipherical.repository.DeviceRepository;
import com.musala.exercise.gatewayperipherical.repository.GatewayRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.List.*;

@SpringBootApplication
public class GatewayPeriphericalApplication {


    public static void main(String[] args) {
        SpringApplication.run(GatewayPeriphericalApplication.class, args);
    }

    @Bean
    public CommandLineRunner mappingDemo(DeviceRepository deviceRepository,
                                         GatewayRepository gatewayRepository) {
        return args -> {

            // create a new gateway
            Gateway gateway = new Gateway()
                    .name("Gateway1")
                    .address("172.24.10.113");

            Device devic = new Device()
                    .status(Status.ONLINE)
                    .vendor("Biutix")
                    .date(new Date())
                    .gateway(gateway);

            Device device = new Device()
                    .status(Status.ONLINE)
                    .vendor("Biutix ---General")
                    .date(new Date())
                    .gateway(gateway);

            List<Device> deviceList= new ArrayList<>();
            deviceList.add(devic);
            deviceList.add(devic);

            gateway.setDeviceList(deviceList);

            // save the gateway
            gatewayRepository.save(gateway);

            // create and save new devices



            deviceRepository.save(devic);
            deviceRepository.save(device);

        };
    }

}
