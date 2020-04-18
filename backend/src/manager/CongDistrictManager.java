package manager;
import java.util.HashSet;
import java.util.HashMap;

//import self created package
import feature.*;

public class CongDistrictManager {
    private HashMap<Integer, CongressionalDistrict> congressionalDistrictMap;
    public CongDistrictManager(HashSet<CongressionalDistrict> congDistrictSet){
        this.congressionalDistrictMap = new HashMap<Integer, CongressionalDistrict>();
        for (CongressionalDistrict cd : congDistrictSet){
            this.congressionalDistrictMap.put(cd.getId(),cd);
        }
    }

    public CongressionalDistrict getCongDistrict(int cdId){
        if(this.congressionalDistrictMap.containsKey(cdId)){
            return this.congressionalDistrictMap.get(cdId);
        }
        else{
            return null;
        }
    }

    public HashSet<Integer> getAllChildrenPrecinct(int cdId){
        if(this.congressionalDistrictMap.containsKey(cdId)){
            return this.congressionalDistrictMap.get(cdId).getChildrenId();
        }
        else{
            return null;
        }
    }

    public String toString(){
        String str = "";
        for(int id : this.congressionalDistrictMap.keySet()){
            str = str + this.congressionalDistrictMap.get(id).toString()+"\n";
        }
        return str;
    }
}