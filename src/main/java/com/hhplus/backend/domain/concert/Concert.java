package com.hhplus.backend.domain.concert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Concert {

    @Getter
    private Long id;

    @Getter
    private String title;

    @Getter
    private String content;

    @Getter
    private int price;
}
