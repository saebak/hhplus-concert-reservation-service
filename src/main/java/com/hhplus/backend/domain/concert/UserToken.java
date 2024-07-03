package com.hhplus.backend.domain.concert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class UserToken {

    @Getter
    private Long id;

    @Getter
    private Long userId;

    @Getter
    private String accessToken;

    @Getter
    private String status;
}
