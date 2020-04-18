package manager;
import java.util.HashMap;
import java.util.HashSet;

//import self created package
import feature.*;

public class StateManager {
    private HashMap<Integer,State> stateMap;
    public StateManager(HashSet<State> stateSet){
        this.stateMap = new HashMap<Integer,State>();
        for (State s : stateSet){
            this.stateMap.put(s.getId(),s);
        }
    }

    public State getState(int stateId){
        if(this.stateMap.containsKey(stateId)){
            return this.stateMap.get(stateId);
        }
        else{
            return null;
        }
    }

    public HashSet<Integer> getAllCounties(int stateId){
        if(this.stateMap.containsKey(stateId)){
            return this.stateMap.get(stateId).getCountiesId();
        }
        else{
            return null;
        }
    }

    public HashSet<Integer> getAllDistricts(int stateId){
        if(this.stateMap.containsKey(stateId)){
            return this.stateMap.get(stateId).getDistrictsId();
        }
        else{
            return null;
        }
    }

    public String toString(){
        String str = "";
        for(int id : this.stateMap.keySet()){
            str = str + this.stateMap.get(id).toString()+"\n";
        }
        return str;
    }
}  