package com.shinkle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo {
    private int albumId;
    private int id;
    private String title;

    @Override
    public String toString() {
        return String.format("[%d] %s", getId(), getTitle());
    }
}
