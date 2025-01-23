package kabdulinovmedet.pet.kz.resume.models;

import lombok.Data;

import java.util.List;

@Data
public class Weather {

        private Main main;
        private Wind wind;
        private List<WeatherObject> weather;


        @Data
        public static class Main {
            private double temp;
            private int pressure;

            // Getters and setters for temp and pressure
        }

        @Data
        public static class Wind {
            private double speed;

        }

    @Data
    public static class WeatherObject {
        private String main;

    }

}
