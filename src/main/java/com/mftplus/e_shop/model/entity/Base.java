package com.mftplus.e_shop.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@MappedSuperclass
public class Base {
    @JsonIgnore
    private boolean isDeleted;
    @JsonIgnore
    private boolean edited;
    @Version
    @JsonIgnore
    private int versionId;

}
