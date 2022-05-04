package com.ml.record.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ml.record.model.Record;
import com.ml.record.util.RecordBuildTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@DirtiesContext
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class MessageProducerTest {

    @Autowired
    private MessageProducer messageProducer;

    @Test
    public void sendMessage() {
        Record record = RecordBuildTest.buildRecord(true, true, true);
        boolean sended = messageProducer.sendMessage(record);
        assertEquals(true, sended);
    }
}
