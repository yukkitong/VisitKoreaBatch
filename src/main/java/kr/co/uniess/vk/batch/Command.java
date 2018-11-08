package kr.co.uniess.vk.batch;

public interface Command<T> {
    void execute(T... arg);
}
