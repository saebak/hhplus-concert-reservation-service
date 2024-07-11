package com.hhplus.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class User {

    @Getter
    private Long id;

    @Getter
    private String name;

}
