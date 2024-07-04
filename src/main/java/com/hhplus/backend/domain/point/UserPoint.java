package com.hhplus.backend.domain.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class UserPoint {

    @Getter
    private Long id;

    @Getter
    private Long userId;

    @Getter
    private int point;
}
