package com.mcp.mycareerplan.ent;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    public String IdUsuario;
    public String Usuarios;
    public String Clave;
    public String IdTipoUsuario;
    public String TiposUsuario;
    public String token;
}