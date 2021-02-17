package com.musala.exercise.gatewayperipherical;


import com.musala.exercise.gatewayperipherical.domain.Gateway;
import com.musala.exercise.gatewayperipherical.exceptions.NotFoundException;
import com.musala.exercise.gatewayperipherical.repository.DeviceRepository;
import com.musala.exercise.gatewayperipherical.repository.GatewayRepository;
import com.musala.exercise.gatewayperipherical.service.GatewayService;
import com.musala.exercise.gatewayperipherical.service.impl.GatewayServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayServiceTest {
    @MockBean
    private GatewayRepository gatewayRepository;

    @MockBean
    private DeviceRepository devicesRepository;
    @TestConfiguration
    static class GatewayServiceTestContextConfiguration {
        @MockBean
        private GatewayRepository gatewayRepository;

        @MockBean
        private DeviceRepository devicesRepository;

        @Bean
        public GatewayService employeeService() {
            return new GatewayServiceImpl(gatewayRepository, devicesRepository);
        }

    }

        @Before
        public void setUp() throws Exception {
            Gateway gateway = new Gateway()
                    .name("Local")
                    .address("123.34.12.11");
            gateway.setId("1");
            Mockito.when(gatewayRepository.findById(gateway.getId())).thenReturn(java.util.Optional.of(gateway));

        }

        @After
        public void tearDown() {
        }

        @Test
        public void findGatewayByIdTest() {
            String id = "1";
            Optional<Gateway> validGateway = gatewayRepository.findById(id);
            assertThat(validGateway.get().getName()).isEqualTo("local1");
            assertThat(validGateway.get().getAddress()).isEqualTo("123.34.12.11");

            given(gatewayRepository.findById("2")).willThrow(new NotFoundException(""));

        }
    }

