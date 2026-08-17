package com.soni.ai_voice_agent.notification.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.soni.ai_voice_agent.entity.Task;
import com.soni.ai_voice_agent.notification.client.TwilioCallClient;
import com.soni.ai_voice_agent.notification.config.TwilioConfig;
import com.soni.ai_voice_agent.reminder.entity.Reminder;

public class TwilioNotificationProviderTest {

    @Mock
    private TwilioCallClient twilioCallClient;

    private TwilioConfig twilioConfig;
    private TwilioNotificationProvider twilioNotificationProvider;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        twilioConfig = new TwilioConfig();

        twilioConfig.setPhoneNumber("+10000000000");
        twilioConfig.setToNumber("+910000000000");

        twilioNotificationProvider =
                new TwilioNotificationProvider(
                        twilioConfig,
                        twilioCallClient
                );
    }

    @Test
    void shouldCreateVoiceCallForReminder() {

        Task task = new Task();
        task.setTitle("Attend Java Class");

        Reminder reminder = new Reminder();
        reminder.setTask(task);

        when(
                twilioCallClient.createCall(
                        org.mockito.ArgumentMatchers.anyString(),
                        org.mockito.ArgumentMatchers.anyString(),
                        org.mockito.ArgumentMatchers.anyString()
                )
        ).thenReturn("TEST_CALL_SID");

        twilioNotificationProvider.sendReminderNotification(reminder);

        ArgumentCaptor<String> toCaptor =
                ArgumentCaptor.forClass(String.class);

        ArgumentCaptor<String> fromCaptor =
                ArgumentCaptor.forClass(String.class);

        ArgumentCaptor<String> twimlCaptor =
                ArgumentCaptor.forClass(String.class);

        verify(twilioCallClient).createCall(
                toCaptor.capture(),
                fromCaptor.capture(),
                twimlCaptor.capture()
        );

        assertEquals(
                "+910000000000",
                toCaptor.getValue()
        );

        assertEquals(
                "+10000000000",
                fromCaptor.getValue()
        );

        assertEquals(
                "<Response><Say>Reminder. You have a task: Attend Java Class</Say></Response>",
                twimlCaptor.getValue()
        );
    }
}