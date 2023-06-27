package com.rubyzli.mail_automation.config;

import com.rubyzli.mail_automation.logic.MailboxChecker;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.text.ParseException;

@Configuration
public class QuartzConfig {
    private final MailboxChecker mailboxChecker;

    @Autowired
    public QuartzConfig(MailboxChecker mailboxChecker) {
        this.mailboxChecker = mailboxChecker;
    }

    @Bean
    public JobDetail mailboxCheckerJobDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(MailboxChecker.class);
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.afterPropertiesSet();
        return jobDetailFactoryBean.getObject(); // Return the JobDetail object
    }

    @Bean
    public Trigger mailboxCheckerTrigger(JobDetail mailboxCheckerJobDetail) throws ParseException {
        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(mailboxCheckerJobDetail);
        triggerFactoryBean.setCronExpression("0 * * ? * *");
        triggerFactoryBean.afterPropertiesSet();
        return triggerFactoryBean.getObject();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactory(Trigger mailboxCheckerTrigger) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(mailboxCheckerTrigger);
        return schedulerFactoryBean;
    }
}
