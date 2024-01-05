//package com.xirui.new21;
//
//import java.util.concurrent.StructuredTaskScope;
//
//public class ThreadTest {
//    public static void main(String[] args) {
//        try(var scope = new StructuredTaskScope.ShutdownOnFailure()) {
//
//            // 想象一下LongRunningTask实现Supplier
//            var dataTask = new LongRunningTask("dataTask", ...);
//            var restTask = new LongRunningTask("restTask", ...);
//
//            // 并行运行任务
//            Subtask<TaskResponse> dataSubTask = scope.fork(dataTask);
//            Subtask<TaskResponse> restSubTask = scope.fork(restTask);
//
//            // 等待所有任务成功完成或第一个子任务失败。
//            // 如果一个失败，向所有其他子任务发送取消请求
//            // 在范围上调用join方法，等待两个任务都完成或如果一个任务失败
//            scope.join();
//            scope.throwIfFailed();
//
//            // 处理成功的子任务结果
//            System.out.println(dataSubTask.get());
//            System.out.println(restSubTask.get());
//        }
//    }
//
//}
