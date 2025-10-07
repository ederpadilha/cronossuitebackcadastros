package com.cronossuite.cadastros.repository.model.cfg;

import java.util.Map;

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
public class Cgf {

    @SerializedName("db_oracle")
    @Expose
    private DbOracle dbOracle;
     
    // Para múltiplos schemas
    @SerializedName("schemas")
    @Expose
    private Map<String, DbOracle> schemas;
}
