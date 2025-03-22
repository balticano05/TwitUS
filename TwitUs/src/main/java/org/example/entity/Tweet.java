package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tweet extends Mood{

    private double latitude;
    private double longitude;
    private Date timestamp;
    private String text;

}