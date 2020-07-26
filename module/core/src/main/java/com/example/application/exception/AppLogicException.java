package com.example.application.exception;


import com.sun.jndi.toolkit.url.Uri;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class AppLogicException extends AbstractThrowableProblem {
    private String title;
    private Status status;
    private String detail;

    public AppLogicException(String Title, Status Status, String Detail) {
//        super(Uri, Title, Status, Detail);
        super();
        this.title = Title;
        this.status = Status;
        this.detail = Detail;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
