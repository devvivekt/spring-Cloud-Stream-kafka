# Read Me First
The following was discovered as part of building this project:

* The JVM level was changed from '11' to '17', review the [JDK Version Range](https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-Versions#jdk-version-range) on the wiki for more details.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.3/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.0.3/reference/htmlsingle/#using.devtools)

### Guides
The following guides illustrate how to use some features concretely:
* Avro Schema files .avsc files
  * include the avro package in pom dependency
    * Add maven avro plugin to produce AVRO friendly classes from schema files

            confluent local services stop
            confluent local services start
            confluent local destroy
            netsh interface portproxy add v4tov4 listenport=9092 listenaddress=0.0.0.0 connectport=9092 connectaddress=172.25.157.6 ip of ubuntu
            netsh interface portproxy add v4tov4 listenport=8081 listenaddress=0.0.0.0 connectport=8081 connectaddress=172.25.157.6 ip of ubuntu
            kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic avro-pos-topic
            kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic avro-pos-topic --from-beginning --property print.key=true --property key.seperator=":"
            kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic loyalty-topic --from-beginning --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer  --property print.key=true --property key.seperator=":"
            kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic hadoop-sink-topic --from-beginning --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer  --property print.key=true --property key.seperator=":"
* [Samples for using Apache Kafka Streams with Spring Cloud stream](https://github.com/spring-cloud/spring-cloud-stream-samples/tree/master/kafka-streams-samples)
* Requirements
  1. Getting avro messages and creating json messages for notification and loyalty services
* KStream methods

          filter and filternot
          map and flatMap
          mapValues and flatMapValues
          forEach and peek
          print
          branch and merge
          to
          toTable
          repartition
          selectKey
          groupBy and groupByKey
          join
          transform and flatTransform
          transformValues and flatTransformValues
          process

*  Kafka Stream join
    batch or stream system , need to join 2 datasets
    data is abstracted into 2 type of systems
    1. KStream
    2. KTable and GlobalKTable

Supports following join operation 
    join operation               |   Result    | Join Type                  |   Feature
    KStream - KStream                KStream     Inner, Left, Right , Outer     Windowed
    KTable - KTable                  KTable      Inner, Left, Right, Outer      Non-Windowed
    KStream - KTable                 KStream     Inner, Left, Outer             Non-Window
    KStream - GlobalKTable           KStream     Inner, Left                    Non-Windowed

1. KStream/KTable must have valid key
2. All topics of join must have same number of partitions
3. Data in topics must be co-partitioned
4. Co-partitioning is not mandatory for KStream-GlobalKTable join
5. Non-key based joins are allowed with KStream-GlobalKTable joins
6. KStream must be on the left side of the join

paybill for internet provider
otp is 5 minutes, mwallet will send a transation event to kafka and otp to mobile number registered
payment request and payment confirmation topics , joined by 5 mins windows


