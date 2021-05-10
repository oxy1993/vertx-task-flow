package com.oxy.vertx.demo.msg;

import com.oxy.vertx.base.entities.BaseResponse;
import com.oxy.vertx.demo.dto.AuthorDTO;

import java.util.List;

public class GetAllAuthorsResponseMsg extends BaseResponse {
    List<AuthorDTO> listAuthorDTOS;

    public List<AuthorDTO> getListAuthors() {
        return listAuthorDTOS;
    }

    public void setListAuthors(List<AuthorDTO> listAuthorDTOS) {
        this.listAuthorDTOS = listAuthorDTOS;
    }
}
