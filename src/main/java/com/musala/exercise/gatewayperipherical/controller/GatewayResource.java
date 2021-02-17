package com.musala.exercise.gatewayperipherical.controller;

import com.musala.exercise.gatewayperipherical.domain.Device;
import com.musala.exercise.gatewayperipherical.domain.Gateway;
import com.musala.exercise.gatewayperipherical.exceptions.DeviceNotAddedException;
import com.musala.exercise.gatewayperipherical.exceptions.NotFoundException;
import com.musala.exercise.gatewayperipherical.service.GatewayService;
import com.musala.exercise.gatewayperipherical.util.PaginationUtil;
import com.musala.exercise.gatewayperipherical.util.ResponseUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RestController
@RequestMapping("/api/gateway")
public class GatewayResource {
    private final Logger log = LoggerFactory.getLogger(GatewayResource.class);
    private final GatewayService gatewayService;

    @Autowired
    public GatewayResource(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    /**
     * @param gateway
     * @return
     */
    @PostMapping
    public ResponseEntity<Gateway> addGateway(@Valid @RequestBody Gateway gateway) {
        log.debug("REST request to save gateway : {}", gateway);
        final Gateway gatewaySaved = gatewayService.save(gateway);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gatewaySaved));
    }

    /**
     * @param id
     * @param deviceDto
     * @return
     * @throws DeviceNotAddedException
     */
    @PutMapping("/{id}")
    public ResponseEntity<Device> addDeviceToGateway(@PathVariable(value = "id") String id,
                                                     @Valid @RequestBody Device deviceDto) throws DeviceNotAddedException {
        log.debug("REST request to save device in a gateway : {}", deviceDto);
        final Device deviceAdded = gatewayService.addDevice(id, deviceDto);
        return ResponseUtil.wrapOrNotFound(Optional.of(deviceAdded));
    }

    /**
     * @param gateway
     * @return
     */
    @PutMapping
    public ResponseEntity<Gateway> updateGateway(@Valid @RequestBody Gateway gateway) {
        log.debug("REST request to update  a gateway : {}", gateway);
        Gateway gatewayUpdatedOrCreated = gatewayService.save(gateway);
        return ResponseUtil.wrapOrNotFound(Optional.of(gatewayUpdatedOrCreated));
    }

    /**
     * @param id
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gateway> getGatewayById(@PathVariable(value = "id") String id) throws NotFoundException {
        log.debug("REST request to get a gateway by id : {}", id);
        Gateway gatewayFounded = gatewayService.findById(id)
                .orElseThrow(() -> new NotFoundException("Gateway requested could not be found"));
        return ResponseUtil.wrapOrNotFound(Optional.of(gatewayFounded));
    }

    /**
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Gateway>> getAllGateway(Pageable pageable) {
        log.debug("REST request to get all gateway paginated");
        Page<Gateway> page = gatewayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gateway");
        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent().parallelStream().collect(Collectors.toList()));
    }

    /**
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<Gateway>> getAllGateway() {
        log.debug("REST request to get all gateway");
        final List<Gateway> gatewayList = gatewayService.findAll();
        return ResponseUtil.wrapOrNotFound(Optional.of(gatewayList));

    }

    /**
     * @param id
     * @param deviceId
     * @return
     */
    @DeleteMapping(value = "/{id}/device/{deviceId}")
    public ResponseEntity<Void> deleteDeviceToGateway(@PathVariable(value = "id") String id,
                                                      @PathVariable(value = "deviceId") long deviceId) {
        gatewayService.deleteDeviceById(id, deviceId);
        return ResponseEntity.noContent().build();
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGateway(@PathVariable String id) {
        gatewayService.deleteGateway(id);
        return ResponseEntity.noContent().build();
    }
}
