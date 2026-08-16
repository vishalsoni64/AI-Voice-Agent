package com.soni.ai_voice_agent.notification.provider;

import com.soni.ai_voice_agent.notification.client.TwilioCallClient;
import com.soni.ai_voice_agent.notification.config.TwilioConfig;
import com.soni.ai_voice_agent.reminder.entity.Reminder;

public class TwilioNotificationProvider implements NotificationProvider {

    private final TwilioConfig twilioConfig;
    private final TwilioCallClient twilioCallClient;

    public TwilioNotificationProvider(
            TwilioConfig twilioConfig,
            TwilioCallClient twilioCallClient) {

        this.twilioConfig = twilioConfig;
        this.twilioCallClient = twilioCallClient;
    }

    @Override
    public void sendReminderNotification(Reminder reminder) {

        String taskTitle = reminder.getTask().getTitle();

        String message =
                "Reminder. You have a task: " + taskTitle;

        String twiml =
                "<Response><Say>" + message + "</Say></Response>";

        String callSid = twilioCallClient.createCall(
                twilioConfig.getToNumber(),
                twilioConfig.getPhoneNumber(),
                twiml
        );

        System.out.println(
                "Twilio call created successfully. Call SID: "
                        + callSid
        );
    }
}