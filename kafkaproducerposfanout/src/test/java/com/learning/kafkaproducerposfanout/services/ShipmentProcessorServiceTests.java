package com.learning.kafkaproducerposfanout.services;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@Log4j2
@SpringBootTest
@EmbeddedKafka(topics = {
        ShipmentProcessorServiceTests.INPUT_TOPIC
        , ShipmentProcessorServiceTests.OUTPUT_TOPIC_SHIP
        , ShipmentProcessorServiceTests.OUTPUT_TOPIC_LOY
        , ShipmentProcessorServiceTests.OUTPUT_TOPIC_HAD
        },
        partitions = 1,
        bootstrapServersProperty = "spring.kafka.bootstrap-servers")
public class ShipmentProcessorServiceTests {

    public static final String INPUT_TOPIC = "pos-topic";
    public static final String OUTPUT_TOPIC_SHIP = "shipment-topic";
    public static final String OUTPUT_TOPIC_LOY = "loyalty-topic";
    public static final String OUTPUT_TOPIC_HAD = "hadoop-sink-topic";

    private static final String GROUP_NAME = "embeddedKafkaApplicationTest";

    /*
    @ClassRule
    public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(1, true,1
            ,"pos-topic","shipment-topic","loyalty-topic","hadoop-sink-topic");

    private static EmbeddedKafkaBroker embeddedKafka = embeddedKafkaRule.getEmbeddedKafka();

     */
    @org.junit.jupiter.api.Test
    void testSendReceive(@Autowired EmbeddedKafkaBroker embeddedKafka) {
        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        senderProps.put("key.serializer", StringSerializer.class);
        senderProps.put("value.serializer", JsonSerializer.class);
        DefaultKafkaProducerFactory<String, String> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<String, String> template = new KafkaTemplate<>(pf, true);
        template.setDefaultTopic(INPUT_TOPIC);
        template.sendDefault("{\"InvoiceNumber\":\"19307944\",\"CreatedTime\":1679834250001,\"StoreID\":\"STR7449\",\"PosID\":\"POS423\",\"CashierID\":\"OAS733\",\"CustomerType\":\"PRIME\",\"CustomerCardNo\":\"7691699125\",\"TotalAmount\":2040.0,\"NumberOfItems\":2,\"PaymentMethod\":\"CASH\",\"TaxableAmount\":2040.0,\"CGST\":51.0,\"SGST\":51.0,\"CESS\":2.5500000000000003,\"DeliveryType\":\"HOME-DELIVERY\",\"DeliveryAddress\":{\"AddressLine\":\"706-3622 Pharetra Av.\",\"City\":\"Serampore\",\"State\":\"West Bengal\",\"PinCode\":\"266237\",\"ContactNumber\":\"8034395531\"},\"InvoiceLineItems\":[{\"ItemCode\":\"338\",\"ItemDescription\":\"Kneeling chairs\",\"ItemPrice\":442.0,\"ItemQty\":1,\"TotalValue\":442.0},{\"ItemCode\":\"518\",\"ItemDescription\":\"Hourglass\",\"ItemPrice\":1598.0,\"ItemQty\":1,\"TotalValue\":1598.0}]}");

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(GROUP_NAME, "false", embeddedKafka);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put("key.deserializer", StringDeserializer.class);
        consumerProps.put("value.deserializer", JsonDeserializer.class);
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);

        Consumer<String, String> consumer = cf.createConsumer();
        consumer.assign(Collections.singleton(new TopicPartition(OUTPUT_TOPIC_SHIP, 0)));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
        consumer.commitSync();

        assertThat(records.count()).isEqualTo(1);
        assertThat(new String(records.iterator().next().value())).isEqualTo("FOO");
    }

    /*
    @Autowired
    StreamsBuilderFactoryBean builderFactoryBean;
    private static Consumer<String, String> consumer;

    @Before
    public void before() {
        builderFactoryBean.setCloseTimeout(0);
    }

    @BeforeClass
    public static void setUp(){
        Map<String, Object> consumerProps = consumerProps("group", "false", embeddedKafka);
        System.setProperty("broker", embeddedKafkaRule.getEmbeddedKafka().getBrokersAsString());
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        consumer = cf.createConsumer();
        embeddedKafka.consumeFromEmbeddedTopics(consumer, "shipment-topic","loyalty-topic","hadoop-sink-topic");
    }

    @Test
    public void testShipmentProcessorService() throws InterruptedException {

        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        DefaultKafkaProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(senderProps);

        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf, true);
        template.setDefaultTopic("pos-topic");
        template.sendDefault("{\"InvoiceNumber\":\"590164\",\"CreatedTime\":1679834251018,\"StoreID\":\"STR7188\",\"PosID\":\"POS682\",\"CashierID\":\"OAS394\",\"CustomerType\":\"NONPRIME\",\"CustomerCardNo\":\"1489226224\",\"TotalAmount\":2103.0,\"NumberOfItems\":2,\"PaymentMethod\":\"CASH\",\"TaxableAmount\":2103.0,\"CGST\":52.575,\"SGST\":52.575,\"CESS\":2.62875,\"DeliveryType\":\"TAKEAWAY\",\"InvoiceLineItems\":[{\"ItemCode\":\"468\",\"ItemDescription\":\"Lunch box\",\"ItemPrice\":1467.0,\"ItemQty\":1,\"TotalValue\":1467.0},{\"ItemCode\":\"543\",\"ItemDescription\":\"Lighthouse clock\",\"ItemPrice\":636.0,\"ItemQty\":1,\"TotalValue\":636.0}]}");

        Thread.sleep(2000);

        ConsumerRecord<String, String> cr = KafkaTestUtils.getSingleRecord(consumer, "shipment-topic", 5000);
        assertThat(cr.value().contains("STR7188")).isTrue();

        cr = KafkaTestUtils.getSingleRecord(consumer, "loyalty-topic", 5000);
        assertThat(cr.value().contains("STR7188")).isTrue();
        cr = KafkaTestUtils.getSingleRecord(consumer, "hadoop-sink-topic", 5000);
        assertThat(cr.value().contains("STR7188")).isTrue();

    }

    @AfterClass
    public static void tearDown(){
        consumer.close();
        System.clearProperty("spring.cloud.stream.kafka.streams.binder.brokers");
    }

    */
}
