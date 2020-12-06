package com.shinkle;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PhotoList {
    @Singular
    List<Photo> photos;
}
