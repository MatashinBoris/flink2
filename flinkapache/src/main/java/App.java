import events.BetSlipPlacedEvent;
import jsonconfig.BetSlipPlacedEventDeserializationSchema;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.connector.jdbc.catalog.JdbcCatalog;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

import java.util.Properties;

public class App {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        EnvironmentSettings settings = EnvironmentSettings.inStreamingMode();
        TableEnvironment tableEnv = TableEnvironment.create(settings);
        Stream
        String name            = "my_catalog";
        String defaultDatabase = "mydb";
        String username        = "...";
        String password        = "...";
        String baseUrl         = "...";

        JdbcCatalog catalog = new JdbcCatalog(name, defaultDatabase, username, password, baseUrl);

        tableEnv.registerCatalog("my_catalog", catalog);

// set the JdbcCatalog as the current catalog of the session
        tableEnv.useCatalog("my_catalog");




        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");

        //properties.setProperty("zookeeper.connect", "localhost:2181");
        properties.setProperty("group.id", "javaflink");

        DataStream<BetSlipPlacedEvent> messageStream = env.addSource(new FlinkKafkaConsumer010<BetSlipPlacedEvent>("Betslip_Placed_Event", new BetSlipPlacedEventDeserializationSchema(), properties));
        System.out.println("Step D");
        messageStream.map((MapFunction<BetSlipPlacedEvent, Object>) event -> {

            System.out.println("Player Id :" + event.getPlayerId());
            System.out.println("betslip Id :" + event.getBetslipId());
            System.out.println("wager :" + event.getWagerAmount());

            return event;
        }).print();



        env.execute();
    }
}