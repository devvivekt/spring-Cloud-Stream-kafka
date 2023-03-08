# Spring Cloud Stream Kafka Application

This application uses Spring Cloud Stream with kafka stream for publishing and subscribing message streams

## Instructions to install
    1. Need jdk11
    2. Need idea intellij
    3. Spring intitalizer with Spring Cloud Stream, Spring kafka Stream
    4. Install Install Windows Subsystem for Linux WSL2 in windows
        WSL2 dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
        dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
        wsl --set-default-version 2
        wsl --list --verbose
        microsoft app store install Ubuntu 20.04.5 LTS
        Setup Ubuntu locally with new root user password
            sudo apt-get update && sudo apt-get upgrade -y
            install jdk: sudo apt install default-jre
        install windows terminal from app store
    5. Install confluent platform
        wget https://packages.confluent.io/archive/6.1/confluent-6.1.0.tar.gz
        untar tar -xvf confluent-6.1.0.tar.gz
    6. Change the kafka server.properties file following attributes
        vi confluent-6.1.0/etc/kafka/server.properties
        listeners=PLAINTEXT://0.0.0.0:9092
        advertised.listeners=PLAINTEXT://localhost:9092
        listener.security.protocol.map=PLAINTEXT:PLAINTEXT,SSL:SSL,SASL_PLAINTEXT:SASL_PLAINTEXT,SASL_SSL:SASL_SSL
    7. Start confluent using
        confluent local services start [only for local mode]
            output should be like below

```
                The local commands are intended for a single-node development environment only,
                NOT for production usage. https://docs.confluent.io/current/cli/index.html
                Using CONFLUENT_CURRENT: /tmp/confluent.778569
                Starting ZooKeeper
                ZooKeeper is [UP]
                Starting Kafka
                Kafka is [UP]
                Starting Schema Registry
                Schema Registry is [UP]
                Starting Kafka REST
                Kafka REST is [UP]
                Starting Connect
                Connect is [UP]
                Starting ksqlDB Server
                ksqlDB Server is [UP]
                Starting Control Center
                Control Center is [UP]
            
```
    8. Hit the browser from local
        http://localhost:9021
        it will show the running cluster
    9. Create kafka topic
        kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic pos-topic
    10. Send Messages to kafka Topic
        kafka-console-producer --broker-list localhost:9092 --topic pos-topic
        kafka-console-consumer --bootstrap-server localhost:9092 --topic pos-topic --from-beginning --property print.key=true --property key.seperator=":"
    11. kafka is running on the ubuntu machine, everytime windows start ubuntu machine ip address is dynamic
        So, need below proxy command to get it connected via localhost
        netsh interface portproxy add v4tov4 listenport=9092 listenaddress=0.0.0.0 connectport=9092 connectaddress=172.25.149.11
    12. kafka has 2 kind of apis, kafka client api and kafkaa stream api
        kafka stream is used only in case of aggregating, summarizing, windowing, and send summarized results to another topic
        kafka stream offers standard data transformation using 2 major abstraction KStream and KTable
        Kstream is key value table where data is inserted
        KTable is key value table with inserted/updated based on key as primary
        Aggregate , jion ,timedbased window and compute windowing
    13. send message curl -X POST http://localhost:8080/post -H "Content-Type: application/json" -d '{"topic": "users", "key": "42", "value": "Sabali"}'  



        

## Instructions to build
```

```

## Contents
```
```

## Instructions to run
```

```

