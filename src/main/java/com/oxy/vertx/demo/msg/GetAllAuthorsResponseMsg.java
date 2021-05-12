package com.oxy.vertx.demo.msg;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oxy.vertx.base.entities.BaseResponse;
import com.oxy.vertx.demo.dto.AuthorDTO;

import java.util.List;

public class GetAllAuthorsResponseMsg extends BaseResponse {
    @JsonProperty("data")
    List<AuthorDTO> listAuthorDTOS;

    public void setListAuthors(List<AuthorDTO> listAuthorDTOS) {
        this.listAuthorDTOS = listAuthorDTOS;
    }

    public List<AuthorDTO> getListAuthorDTOS() {
        return listAuthorDTOS;
    }
}
