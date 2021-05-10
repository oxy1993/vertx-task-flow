package com.oxy.vertx.demo.task.helloworld;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.jdbc.BaseJDBCClientImpl;
import com.oxy.vertx.demo.domain.Author;
import com.oxy.vertx.demo.dto.AuthorDTO;
import com.oxy.vertx.demo.msg.ExecGetAllAuthorsMsg;
import com.oxy.vertx.demo.msg.GetAllAuthorsResponseMsg;
import io.vertx.core.Handler;

import java.util.stream.Collectors;

public class ExecGetAllAuthorsTask extends OxyTask<ExecGetAllAuthorsMsg> {
    @Override
    protected void exec(ExecGetAllAuthorsMsg input, Handler<ExecGetAllAuthorsMsg> nextTask) {
        GetAllAuthorsResponseMsg responseMsg = input.createResponse(GetAllAuthorsResponseMsg.class);
        BaseJDBCClientImpl.getClient().doQuery("select * from authors", Author.class, done -> {
            responseMsg.setListAuthors(
                    done.stream()
                    .map(author -> AuthorDTO.AuthorFluentBuilder()
                    .setId(author.getId())
                    .setFirstName(author.getFirst_name())
                    .setLastName(author.getLast_name())
                    .setEmail(author.getEmail())
                    .setBirthdate(author.getBirthdate())
                    .setAdded(author.getAdded())
                    .build()).collect(Collectors.toList()));
            nextTask.handle(input);
        });
    }
}
