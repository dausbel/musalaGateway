package com.musala.exercise.gatewayperipherical.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.musala.exercise.gatewayperipherical.domain.enumerations.Status;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "devices")
@EntityListeners(AuditingEntityListener.class)
public class Device implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long idDevice;

    @NotBlank
    private String vendor;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gateway_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Gateway gateway;



    public Device() {
    }

    public Long getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Long idDevice) {
        this.idDevice = idDevice;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    public Device vendor(String vendor) {

        this.vendor = vendor;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Device date(Date date) {
        this.date = date;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public Device status(Status status) {
        this.status = status;
        return this;
    }

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    public Device gateway(Gateway gateway) {
        this.gateway = gateway;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Device)) return false;

        Device device = (Device) o;

        return new EqualsBuilder()
                .append(getIdDevice(), device.getIdDevice())
                .append(getDate(), device.getDate())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getIdDevice())
                .append(getDate())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Device{" +
                "Id=" + idDevice +
                ", vendor='" + vendor + '\'' +
                ", date=" + date +
                ", status=" + status +
                ", gateway=" + gateway +
                '}';
    }
}
