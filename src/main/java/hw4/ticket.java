package hw4;

import java.time.LocalDate;
import java.util.Objects;

abstract class ticket {
    private String number;
    private String passengerName;
    private double price;
    private LocalDate date;

    public ticket(String number, String passengerName, double price, LocalDate date) {
        this.number = number;
        this.passengerName = passengerName;
        this.price = price;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    public abstract String getDescription();

    public abstract double calculateFinalPrice();

    @Override
    public String toString() {
        return "Номер: " + number + ", пассажир/зритель: " + passengerName
            + ", цена: " + price + ", дата: " + date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ticket t = (ticket) obj;
        return Double.compare(price, t.price) == 0 && Objects.equals(number, t.number)
            && Objects.equals(passengerName, t.passengerName)  && Objects.equals(date, t.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, passengerName, price, date);
    }
}

class trainTicket extends ticket {
    private String departureStation;
    private String arrivalStation;
    private int percFee;

    public trainTicket(String number, String passengerName, double price, LocalDate date,
            String departureStation, String arrivalStation, int percFee) {
        super(number, passengerName, price, date);
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.percFee = percFee;
    }

    @Override
    public String getDescription() {
        return "Ж/д билет: " + departureStation + " -> " + arrivalStation;
    }

    @Override
    public double calculateFinalPrice() {
        return getPrice() < 100 ? getPrice() * (1 + (this.percFee/50)) : getPrice() * (1 + (this.percFee/100));
    }

    @Override
    public String toString() {
        return super.toString() + ", маршрут: " + departureStation + " -> " + arrivalStation;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        trainTicket t = (trainTicket) obj;
        return Objects.equals(departureStation, t.departureStation)
                && Objects.equals(arrivalStation, t.arrivalStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), departureStation, arrivalStation);
    }
}

class airTicket extends ticket {
    private String departureAirport;
    private String arrivalAirport;
    private String flightNumber;
    private int flatFee;

    public airTicket(String number, String passengerName, double price, LocalDate date,
            String departureAirport, String arrivalAirport, String flightNumber, int flatFee) {
        super(number, passengerName, price, date);
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.flightNumber = flightNumber;
        this.flatFee = flatFee;
    }

    @Override
    public String getDescription() {
        return "Авиабилет: " + departureAirport + " -> " + arrivalAirport + ", рейс: " + flightNumber;
    }

    @Override
    public double calculateFinalPrice() {
        return getPrice() + this.flatFee;
    }

    @Override
    public String toString() {
        return super.toString() + ", аэропорты: " + departureAirport
            + " -> " + arrivalAirport + ", рейс: " + flightNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        airTicket t = (airTicket) obj;
        return Objects.equals(departureAirport, t.departureAirport)
            && Objects.equals(arrivalAirport, t.arrivalAirport)
            && Objects.equals(flightNumber, t.flightNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), departureAirport,
            arrivalAirport, flightNumber);
    }
}

class theaterTicket extends ticket {
    private String performanceName;
    private String seat;
    private String sector;
    private int percFee;

    public theaterTicket(String number, String passengerName, double price, LocalDate date,
            String performanceName, String seat, String sector, int percFee) {
        super(number, passengerName, price, date);
        this.performanceName = performanceName;
        this.seat = seat;
        this.sector = sector;
        this.percFee = percFee;
    }

    @Override
    public String getDescription() {
        return "Билет на спектакль '" + performanceName + "', сектор: " + sector
            + ", место: " + seat;
    }

    @Override
    public double calculateFinalPrice() {
        return getPrice() * (1 + (this.percFee/100));
    }

    @Override
    public String toString() {
        return super.toString() + ", спектакль: " + performanceName
            + ", сектор: " + sector  + ", место: " + seat;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        theaterTicket t = (theaterTicket) obj;
        return Objects.equals(performanceName, t.performanceName) && Objects.equals(seat, t.seat)
            && Objects.equals(sector, t.sector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), performanceName, seat, sector);
    }
}