package com.hhplus.backend.domain.token;

import java.time.LocalDateTime;
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

    @Getter
    private LocalDateTime createAt;

    @Getter
    private LocalDateTime udpateAt;
}
