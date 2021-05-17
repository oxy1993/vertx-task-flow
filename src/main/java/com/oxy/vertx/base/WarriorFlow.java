package com.oxy.vertx.base;

import com.oxy.vertx.base.entities.IRequest;
import io.vertx.core.Handler;

import java.util.ArrayList;
import java.util.List;

public class WarriorFlow<T extends IRequest> extends WarriorTask<T> {
    private final List<WarriorTask<? super T>> mWorkerList = new ArrayList<>();
    private final List<Integer> alwaysRunIndex = new ArrayList<>();

    @Override
    public void exec(T input, Handler<T> nextTask) {
        runOneTask(mWorkerList, input, nextTask, 0);
    }

    private void runOneTask(List<WarriorTask<? super T>> taskList, T input, Handler<T> lastTask, final int step) {
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

    public WarriorFlow<T> addTask(WarriorTask<? super T> task) {
        mWorkerList.add(task);
        return this;
    }

    public WarriorFlow<T> addAlwaysRunTask(WarriorTask<? super T> task) {
        alwaysRunIndex.add(mWorkerList.size());
        return addTask(task);
    }
}
