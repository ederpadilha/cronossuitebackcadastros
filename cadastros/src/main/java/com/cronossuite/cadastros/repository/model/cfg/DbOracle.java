package com.cronossuite.cadastros.repository.model.cfg;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DbOracle {

@SerializedName("user")
@Expose
private String user;
@SerializedName("password")
@Expose
private String password;
@SerializedName("connectString")
@Expose 
private String connectString;

 

}