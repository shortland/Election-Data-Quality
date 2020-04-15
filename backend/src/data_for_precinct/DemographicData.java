package data_for_precinct;
import java.util.EnumMap;
//import data_for_precinct.*;

public class DemographicData {
    private EnumMap<RACE, Integer> demographicByRace;
    private int total;
    public DemographicData(int dA, int dB, int dH, int dO, int dW){
        this.demographicByRace = new EnumMap<RACE, Integer>(RACE.class);
        this.demographicByRace.put(RACE.ASIAN, dA);
        this.demographicByRace.put(RACE.BLACK, dB);
        this.demographicByRace.put(RACE.HISPANIC, dH);
        this.demographicByRace.put(RACE.OTHER, dO);
        this.demographicByRace.put(RACE.WHITE, dW);
        this.total = calculateTotal();
    }

    private int calculateTotal(){
        int total = 0;
        for (RACE r : this.demographicByRace.keySet()){
            total += this.demographicByRace.get(r);
        }
        return total;
    }

    public int getDemogaphic(RACE race){
        if (this.demographicByRace.containsKey(race)){
            return this.demographicByRace.get(race);
        }
        else{
            return 0;
        }
    }

    public double getDemogaphicPercentage(RACE race){
        if (this.demographicByRace.containsKey(race)){
            double percentage = Double.valueOf(this.getDemogaphic(race))/ Double.valueOf(this.total);
            return percentage;
        }
        else{
            System.out.println("hi: " + race.name()+ ":" + Integer.toString(this.getDemogaphic(race)));
            return 0.0;
        }
    }

    public void setDemographic(RACE race, int newData){
        if (this.demographicByRace.containsKey(race)){
            this.demographicByRace.remove(race);
        }
        this.demographicByRace.put(race,newData);
        this.total = this.calculateTotal();
    }

    public String toString(){
        String str = "Demographic : ";
        for (RACE r : this.demographicByRace.keySet()){
            str = str + "\t" + r.name() + " : " + Integer.toString(this.getDemogaphic(r)) + " [" + Double.toString(this.getDemogaphicPercentage(r))+ "]\n";
        }
        return str;
    }
}