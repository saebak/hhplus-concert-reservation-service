package com.hhplus.backend.domain.point;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class PointHistory {

    @Getter
    private Long id;

    @Getter
    private Long userId;

    @Getter
    private int amount;

    @Getter
    private String type;

    @Getter
    private LocalDateTime createAt;
}
