package cn.zealon.notes.controller.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class EmailEnableParam implements Serializable {
    private String userId;
    private String userName;
    private String password;
    private String avatarUrl;
    private String clientName;
    private String name;
    private String email;
    private String phone;
    private String userType;
}
