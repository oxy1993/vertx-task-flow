package com.oxy.vertx.base;

import com.oxy.vertx.base.entities.IRequest;
import io.vertx.core.Handler;

import java.util.ArrayList;
import java.util.List;

public class OxyFlow<T extends IRequest> extends OxyTask<T> {
    private final List<OxyTask<? super T>> mWorkerList = new ArrayList<>();
    private final List<Integer> alwaysRunIndex = new ArrayList<>();

    @Override
    public void exec(T input, Handler<T> nextTask) {
        runOneTask(mWorkerList, input, nextTask, 0);
    }

    private void runOneTask(List<OxyTask<? super T>> taskList, T input, Handler<T> lastTask, final int step) {
        if (step == taskList.size()) {
            //this is the end => do return here
            lastTask.handle(input);
        } else if (!input.isBreakWorkFlow() || alwaysRunIndex.contains(step)) {
            taskList.get(step).run(input, entries -> runOneTask(taskList, (T) entries, lastTask, step + 1));
        } else {
            //skip and move to next task
            runOneTask(taskList, input, lastTask, step + 1);
        }
    }

    public OxyFlow<T> addTask(OxyTask<? super T> task) {
        mWorkerList.add(task);
        return this;
    }

    public OxyFlow<T> addAlwaysRunTask(OxyTask<? super T> task) {
        alwaysRunIndex.add(mWorkerList.size());
        return addTask(task);
    }
}
