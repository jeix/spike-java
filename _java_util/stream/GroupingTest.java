
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class GroupingTest {
    public static void main(String[] args) {
        List<Rdf> rdfList = Arrays.asList(
            new Rdf("1", "101", 11, 15),
            new Rdf("1", "101", 16, 20),
            new Rdf("1", "101", 21, 25),
            new Rdf("1", "102", 13, 17),
            new Rdf("1", "102", 18, 22),
            new Rdf("2", "201", 12, 16),
            new Rdf("2", "201", 17, 21),
            new Rdf("2", "201", 22, 26)
        );

        System.out.println("### get minimum begins");

        Map<String,Optional<Rdf>> begins = rdfList.stream()
            .collect(groupingBy(Rdf::carDriver, minBy(comparingInt(Rdf::getBegin))));
        
        for (String carDriver : begins.keySet()) {
            Optional<Rdf> maybeRdf = begins.get(carDriver);
            if (maybeRdf.isPresent()) {
                Rdf rdf = maybeRdf.get();
                System.out.println(rdf.carId+"::"+rdf.driverId+"::"+rdf.begin);
					//-> 2::201::12
					//-> 1::101::11
					//-> 1::102::13
            }
        }

        System.out.println("### get maximum ends");

        Map<String,Optional<Rdf>> ends = rdfList.stream()
            .collect(groupingBy(Rdf::carDriver, maxBy(comparingInt(Rdf::getEnd))));
        
        for (String carDriver : ends.keySet()) {
            Optional<Rdf> maybeRdf = ends.get(carDriver);
            if (maybeRdf.isPresent()) {
                Rdf rdf = maybeRdf.get();
                System.out.println(rdf.carId+"::"+rdf.driverId+"::"+rdf.end);
					//-> 2::201::26
					//-> 1::101::25
					//-> 1::102::22
            }
        }

        System.out.println("### begins + ends");

        {
            System.out.println("##### (1)");

            List<Rdf> rdfMinMaxList = new ArrayList<>();
            for (String carDriver : begins.keySet()) {
                Optional<Rdf> maybeBeginRdf = begins.get(carDriver);
                Optional<Rdf> maybeEndRdf = ends.get(carDriver);
                if (maybeBeginRdf.isPresent()) {
                    Rdf rdfBegin = maybeBeginRdf.get();
                    Rdf rdfEnd = maybeEndRdf.get();
                    rdfMinMaxList.add(new Rdf(rdfBegin.carId, rdfBegin.driverId, rdfBegin.begin, rdfEnd.end));
                }
            }

            rdfMinMaxList = rdfMinMaxList.stream()
                .sorted((rdf1, rdf2) -> rdf1.compareTo(rdf2))
                .collect(toList());

            for (Rdf rdf : rdfMinMaxList) {
                System.out.println(rdf.carId+"::"+rdf.driverId+"::"+rdf.begin+"::"+rdf.end);
					//-> 1::101::11::25
					//-> 1::102::13::22
					//-> 2::201::12::26
            }
        }

        {
            System.out.println("##### (2)");

            List<Rdf> beginList = begins.values().stream()
                .filter((maybeBegin) -> maybeBegin.isPresent())
                .map((maybeBegin) -> maybeBegin.get())
                .collect(toList());

            Map<String, Rdf> endMap = ends.values().stream()
                .filter((maybeEnd) -> maybeEnd.isPresent())
                .map((maybeEnd) -> maybeEnd.get())
                .collect(toMap(Rdf::carDriver, (rdf) -> rdf));
            
            List<Rdf> rdfMinMaxList = new ArrayList<>();
            for (Rdf rdfBegin : beginList) {
                Rdf rdfEnd = endMap.get(rdfBegin.carDriver());
                rdfMinMaxList.add(new Rdf(rdfBegin.carId, rdfBegin.driverId, rdfBegin.begin, rdfEnd.end));
            }

            rdfMinMaxList = rdfMinMaxList.stream()
                .sorted(comparing(Rdf::getCarId).thenComparing(Rdf::getDriverId))
                .collect(toList());

            for (Rdf rdf : rdfMinMaxList) {
                System.out.println(rdf.carId+"::"+rdf.driverId+"::"+rdf.begin+"::"+rdf.end);
					//-> 1::101::11::25
					//-> 1::102::13::22
					//-> 2::201::12::26
            }
        }
    }
}

class Rdf {
    /*private*/ String carId;
    /*private*/ String driverId;
    /*private*/ int begin;
    /*private*/ int end;
    public Rdf(String carId, String driverId, int begin, int end) {
        this.carId = carId;
        this.driverId = driverId;
        this.begin = begin;
        this.end = end;
    }
    public String getCarId() { return carId; }
    public String getDriverId() { return driverId; }
    public int getBegin() { return begin; }
    public int getEnd() { return end; }
    // public CarDriver carDriver() {
    //     return new CarDriver(carId, driverId);
    // }
    public String carDriver() {
        return carId + "_" + driverId;
    }
    public int compareTo(Rdf other) {
        int res = carId.compareTo(other.carId);
        if (res == 0) {
            res = driverId.compareTo(other.driverId);
        }
        return res; 
    }
}

// class CarDriver {
//     /*private*/ String carId;
//     /*private*/ String driverId;
//     public CarDriver(String carId, String driverId) {
//         this.carId = carId;
//         this.driverId = driverId;
//     }
//     @Override
//     public boolean equals(Object o) {
//         if (! (o instanceof CarDriver)) {
//             return false;
//         }
//         CarDriver cd = (CarDriver) o;
//         return carId.equals(cd.carId) && driverId.equals(cd.driverId);
//     }
//     @Override
//     public int hashCode() {
//         int res = 17;
//         res = 31 * res + carId.hashCode();
//         res = 31 * res + driverId.hashCode();
//         return res;
//     }
// }