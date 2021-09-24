package com.zkp.jwt.testcopy;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Data
public class USer2 {
    int x;
    String name;
    List<Long> ids;

    @Override
    public String toString() {
        return "USer2{" +
                "x=" + x +
                ", name='" + name + '\'' +
                ", names=" + ids +
                '}';
    }
}
