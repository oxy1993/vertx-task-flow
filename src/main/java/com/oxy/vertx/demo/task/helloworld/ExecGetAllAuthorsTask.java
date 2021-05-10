package com.oxy.vertx.demo.task.helloworld;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.jdbc.BaseJDBCClientImpl;
import com.oxy.vertx.demo.domain.Author;
import com.oxy.vertx.demo.dto.AuthorDTO;
import com.oxy.vertx.demo.msg.ExecGetAllAuthorsMsg;
import com.oxy.vertx.demo.msg.GetAllAuthorsResponseMsg;
import io.vertx.core.Handler;

import java.util.ArrayList;
import java.util.List;

public class ExecGetAllAuthorsTask extends OxyTask<ExecGetAllAuthorsMsg> {
    @Override
    protected void exec(ExecGetAllAuthorsMsg input, Handler<ExecGetAllAuthorsMsg> nextTask) {
        GetAllAuthorsResponseMsg responseMsg = input.createResponse(GetAllAuthorsResponseMsg.class);
        String sql = "select * from authors";
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        BaseJDBCClientImpl.getClient().doQuery(sql, Author.class, done -> {
            log.info("Get authors from authors success with size {}", done.size());
            done.forEach(author -> authorDTOList.add(AuthorDTO.AuthorFluentBuilder()
                    .setId(author.getId())
                    .setFirstName(author.getFirst_name())
                    .setLastName(author.getLast_name())
                    .setEmail(author.getEmail())
                    .setBirthdate(author.getBirthdate())
                    .setAdded(author.getAdded())
                    .build()));
            responseMsg.setListAuthors(authorDTOList);
            nextTask.handle(input);
        });
    }
}
