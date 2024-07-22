package web.logic.validator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeDeserializer extends JsonDeserializer<Time> {

    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    @Override
    public Time deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String timeString = p.getText();
        try {
            return new Time(format.parse(timeString).getTime());
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}
