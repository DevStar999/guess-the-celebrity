package com.example.guessthecelebrity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CelebInfo implements Serializable {
    private String celebName;
    private String celebImageSrc;
}
