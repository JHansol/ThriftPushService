package com.naver.npns.server.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_idx")
    private Long idx;

    @Column(name = "user_uuid", unique = true)
    @NotNull
    private String uuid;

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "idx=" + idx +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
