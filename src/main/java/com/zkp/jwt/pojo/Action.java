package com.zkp.jwt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author admin
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Action {
    /**
     * 
     */
    DELETE(1, "删除"),
    COMMENTS(2, "评论"),
    REPLY(3, "回复"),
    ENSHRINE(4, "收藏"),
    Like(5, "点赞");

    //可以为enum编写构造方法、字段和方法
    private int id;
    private String name;

    public static void week() {
        Action[] weeks = Action.values();
        System.out.println("Work"+ Arrays.stream(weeks).map(a->a.getId()).filter(i->i>4).collect(Collectors.toList()));
    }
    public static Action get(Integer id) {
        if (id == null) {
            return null;
        }
        Action[] actions = Action.values();
        for (Action action : actions) {
            if (action.getId() == id) {
                return action;
            }
        }
        return null;
    }}
