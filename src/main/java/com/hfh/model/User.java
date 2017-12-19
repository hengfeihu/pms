package com.hfh.model;

import io.ebean.Model;

import javax.persistence.*;

@Entity
@Table(name = "pms_user")
public class User extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(length = 100)
    public String name;
}
