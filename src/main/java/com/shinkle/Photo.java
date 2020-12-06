package com.shinkle;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Photo {
    int albumId;
    int id;
    String title;
    String url;
    String thumbnailUrl;
}
