package com.example.domain.primary.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table
public class DtbUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", unique = true, length = 20)
    private String userName;

    @Column(name = "fullname", length = 50, nullable = false)
    private String fullName;

    @Column(name = "password", nullable = false)
    private String passWord;

    @Column(name = "isapproved", columnDefinition = "boolean default false")
    private boolean isApproved;

    @Column(name = "isdeleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    //Validate
    @Column(unique = true)
    @Pattern(regexp = "^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$", message = "Email not invalid")
    private String email;

    private String address;

    @Column(name = "verificationcode")
    private String verificationCode;

    @JsonFormat(pattern = "MM/DD/YYYY HH:SS")
    @Column(name = "verificationtime")
    private Timestamp verificationTime;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<DtbRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "shipper")
    private List<DtbTransportRequest> postedTransportRequests = new ArrayList<>();

    @OneToMany(mappedBy = "winner")
    private List<DtbTransportRequest> receivedTransportRequests = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<DtbBookDetail> detailBooks = new ArrayList<>();


}
