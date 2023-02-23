package jsonconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import events.BetSlipPlacedEvent;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.util.serialization.DeserializationSchema;

import java.io.IOException;

public class BetSlipPlacedEventDeserializationSchema implements DeserializationSchema<BetSlipPlacedEvent> {

    private final static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Override
    public BetSlipPlacedEvent deserialize(byte[] bytes) throws IOException {
        return objectMapper.readValue(bytes, BetSlipPlacedEvent.class);
    }

    @Override
    public boolean isEndOfStream(BetSlipPlacedEvent betSlipPlacedEvent) {
        return false;
    }

    @Override
    public TypeInformation<BetSlipPlacedEvent> getProducedType() {
        return TypeInformation.of(BetSlipPlacedEvent.class);
    }
}
