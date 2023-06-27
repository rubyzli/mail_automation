package com.rubyzli.mail_automation.logic;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.quartz.Job;

@Component
public class MailboxChecker implements Job {
    private final EmailResponder emailResponder;

    @Autowired
    public MailboxChecker(EmailResponder emailResponder) {
        this.emailResponder = emailResponder;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        emailResponder.checkMailbox();
    }
}

