package com.musala.exercise.gatewayperipherical;

import com.musala.exercise.gatewayperipherical.controller.GatewayResource;
import com.musala.exercise.gatewayperipherical.domain.Gateway;
import com.musala.exercise.gatewayperipherical.service.GatewayService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(GatewayResource.class)
class GatewayPeriphericalApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GatewayService gatewayService;

    @Before
    public void setup() {

    }


    @Test
    public void givenGatewayById_ReturnValidTest() throws Exception {

        // given
        Gateway gateway=new Gateway( )
                .address("123.34.12.11")
                .name("local1");
        given(gatewayService.findById("1"))
                .willReturn(Optional.ofNullable(gateway));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/gateway/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    public void givenGatewayByIdValidTest() throws Exception {

        // given
        Gateway gateway=new Gateway( )
                .address("123.34.12.11")
                .name("local1");
        given(gatewayService.findById("1"))
                .willReturn(Optional.ofNullable(gateway));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/gateway/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        mvc.perform(get("/api/gateway/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.name", is("local1")))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.address", is("123.34.12.11")));

    }

}
