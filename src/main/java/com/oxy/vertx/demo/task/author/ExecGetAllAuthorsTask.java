package com.oxy.vertx.demo.task.author;

import com.oxy.vertx.base.OxyTask;
import com.oxy.vertx.base.jdbc.BaseJDBCClientImpl;
import com.oxy.vertx.base.msg.GetSetRedisValueMsg;
import com.oxy.vertx.base.redis.GetRedisHelperTask;
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
        GetSetRedisValueMsg getSetRedisValueMsg = new GetSetRedisValueMsg();
        getSetRedisValueMsg.setKey("limit");
        new GetRedisHelperTask().run(getSetRedisValueMsg, done -> {
            if (done.getResponse().getResult() != 0) {
                input.fail(done.getResponse().getResult());
                nextTask.handle(input);
            }

            String limit = done.getValue();
            long startQueryTime = System.currentTimeMillis();
            BaseJDBCClientImpl.getClient().doQuery("select * from authors limit " + limit, Author.class, doneQuery -> {
                log.info("Query latency ====================================> {}ms", System.currentTimeMillis() - startQueryTime);
                long startMapperTime = System.currentTimeMillis();
                responseMsg.setListAuthors(doneQuery.stream()
                        .map(author -> AuthorDTO.AuthorFluentBuilder()
                                .setId(author.getId())
                                .setFirstName(author.getFirst_name())
                                .setLastName(author.getLast_name())
                                .setEmail(author.getEmail())
                                .setBirthdate(author.getBirthdate())
                                .setAdded(author.getAdded())
                                .build()).collect(Collectors.toList()));
                responseMsg.setResult(doneQuery.size());
                responseMsg.setDescription("Success");
                log.info("Map obj to DTO ===================================> {}ms", System.currentTimeMillis() - startMapperTime);
                nextTask.handle(input);
            });
        });
    }
}
