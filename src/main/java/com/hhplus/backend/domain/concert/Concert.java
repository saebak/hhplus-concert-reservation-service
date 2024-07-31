package com.hhplus.backend.domain.concert;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Concert implements Serializable {

    private static final long serialVersionUID = 123124312394329421L;

    @Getter
    private Long id;

    @Getter
    private String title;

    @Getter
    private String content;

    @Getter
    private int price;
}
